# MangaReader Web 化重构开发文档

> 版本：v1.0
> 目标：将 MangaReader 从 **JavaFX + Spring Boot** 桌面应用，重构为 **Web 前端 + Spring Boot** 的 B/S 架构应用。
> 原则：**最大化复用现有后端业务代码（Service / Mapper / 定时任务 / 配置 / 数据库），仅重构表现层与启动方式，并新增 REST API 与前端工程。**

---

## 1. 重构背景与目标

### 1.1 现状问题

| 问题 | 说明 |
| --- | --- |
| 桌面端限制 | JavaFX 只能在安装了桌面环境的机器上运行，无法远程访问、无法多端使用 |
| 打包困难 | JavaFX 模块化依赖（`javafx-controls` 等 6 个模块）需 jlink / 平台分类器，发布包体积大、跨平台维护成本高 |
| UI 迭代慢 | FXML + Controller 的 UI 开发效率低，样式能力弱（对比现代 Web 生态） |
| 异常处理耦合 | `GlobalExceptionHandler` 直接依赖 `javafx.scene.control.Alert` 弹窗，无法在服务端模式使用 |

### 1.2 重构目标

1. 后端改造为**纯 Spring Boot Web 服务**（内嵌 Tomcat，`java -jar` 一键启动，无 JavaFX 依赖）。
2. 新增 **REST API** 层，对前端暴露书架、阅读、下载中心全部能力。
3. 新建 **Vue 3 前端工程**，实现书架、下拉式阅读器、下载中心三大页面，功能与桌面版对齐。
4. 数据库表结构、下载流水线（定时任务 + 线程池 + 重试）**零改动复用**。
5. 提供下载进度的**实时推送能力（SSE）**，替代桌面版本地轮询。

### 1.3 功能对齐清单（桌面版 → Web 版）

| 桌面版功能 | 承载位置 | Web 版对应 |
| --- | --- | --- |
| 书架网格 + 封面 + 默认封面兜底 | `MangaShelfController` + `manga_shelf.fxml` | 书架页 `/` |
| 章节列表 + 上/下一章切换 | `ReaderController` + `manga_reader.fxml` | 阅读页 `/reader/:mangaId` |
| 下拉式虚拟滚动阅读 | `ComicVirtualFlow` | 前端虚拟滚动 + 懒加载 |
| 自动播放（加速/减速/自动连章） | `ReaderController` + `AutoplayConfig` | 前端 `setInterval` 滚动 + 后端配置接口 |
| 新增漫画下载任务 | `DownloadCenterController` | 下载中心页 `/download` |
| 下载进度 / 状态展示 | 定时任务 + 状态字段 | 下载中心页 + SSE 实时推送 |
| 顶部导航切换 | `TopNavigationController` + `top_navigation.fxml` | 前端顶部导航栏（Vue Router） |

---

## 2. 现有架构盘点

### 2.1 现有技术栈

- Java 17、Spring Boot（含 starter-web / aop / validation）
- MyBatis 3.5.13 + MyBatis Spring Boot Starter 3.0.3 + HikariCP + MySQL 8
- **JavaFX 21.0.2**（controls / fxml / web / graphics / media / swing）← 本次移除
- OkHttp 4.12.0（章节/图片下载）、jsoup 1.17.2（爬虫解析）、Guava、TwelveMonkeys ImageIO + webp-imageio（图片解码）
- Lombok、SLF4J + Logback

### 2.2 现有模块职责

```
com.mangareader
├── MainApp / MangaReaderApplication   # 入口：JavaFX launch() 内启动 Spring 容器
├── controller/                        # 5 个 FXML 控制器（JavaFX 事件驱动，全部重写）
├── component/ComicVirtualFlow         # JavaFX 虚拟滚动列表（删除，前端替代）
├── config/                            # 8 个配置类（大部分复用）
├── constant / enums                   # ImageConstant、ProcessStatus（复用）
├── handler/GlobalExceptionHandler     # 依赖 JavaFX Alert（重写）
├── mapper/ + resources/mapper/*.xml   # MyBatis 数据访问（复用）
├── model/entity/                      # Manga、Chapter、MangaImage 等（复用）
├── service/ + service/impl/           # 业务服务（复用）
├── task/                              # 漫画下载 + 图片下载两个定时任务（复用）
└── utils/ImageUtil                    # 图片工具（复用）
```

### 2.3 存量文件处置总表

| 文件 / 目录 | 处置 | 说明 |
| --- | --- | --- |
| `MainApp.java` | **删除** | 仅为 JavaFX 入口转发，不再需要 |
| `MangaReaderApplication.java` | **重写** | 去掉 `extends Application`，改为标准 `@SpringBootApplication` |
| `controller/MainController.java` | **删除** | 视图切换由前端路由承担 |
| `controller/MangaShelfController.java` | **重写** | → `MangaController`（REST） |
| `controller/ReaderController.java` | **重写** | → `ChapterController` + `ReaderConfigController`（REST） |
| `controller/DownloadCenterController.java` | **重写** | → `DownloadController`（REST + SSE） |
| `controller/TopNavigationController.java` | **删除** | 前端导航实现 |
| `component/ComicVirtualFlow.java` | **删除** | 前端虚拟滚动替代 |
| `handler/GlobalExceptionHandler.java` | **重写** | 改为 `@RestControllerAdvice` 返回 JSON |
| `service/**`、`mapper/**`、`task/**`、`enums/**`、`constant/**`、`utils/**` | **复用** | 无需改动 |
| `config/AutoplayConfig.java` 等 8 个配置类 | **复用** | 仅确认无 JavaFX 引用 |
| `resources/fxml/*`、`resources/css/*`、`resources/images/default_cover.png` | **删除** | 前端静态资源接管（默认封面迁移到前端工程） |
| `resources/mapper/*.xml`、`resources/sql/*` | **复用** | 数据库零改动 |
| `pom.xml` | **修改** | 移除全部 `org.openjfx` 依赖与 JavaFX 打包配置 |

---

## 3. 目标架构设计

### 3.1 总体架构

```
┌──────────────────────────── 浏览器 ────────────────────────────┐
│  Vue 3 SPA (Vite + TypeScript + Element Plus + Pinia)          │
│  书架页 / 阅读页(虚拟滚动+自动播放) / 下载中心(表单+进度)          │
└──────────────┬───────────────────────────────┬─────────────────┘
               │ REST /api/**  + SSE           │ 图片静态资源 /images/**
┌──────────────▼───────────────────────────────▼─────────────────┐
│                    Spring Boot Web (内嵌 Tomcat)                │
│  ┌────────────┐ ┌──────────────┐ ┌──────────────┐ ┌─────────┐ │
│  │ REST 控制器 │ │ 静态资源映射  │ │ SSE 推送端点  │ │ 全局异常 │ │
│  └─────┬──────┘ └──────┬───────┘ └──────┬───────┘ └─────────┘ │
│        ▼               ▼                ▼                      │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │ Service 层（复用）：Manga / Chapter / MangaImage /        │  │
│  │ MangaDownload / MangaImageDownload / Image               │  │
│  └──────────────┬───────────────────────────┬───────────────┘  │
│                 ▼                           ▼                   │
│  ┌──────────────────────┐   ┌───────────────────────────────┐  │
│  │ MyBatis Mapper (复用) │   │ 定时任务(复用)：漫画下载调度、   │  │
│  └──────────┬───────────┘   │ 图片下载扫描 + 僵死任务恢复      │  │
│             ▼               └───────────────┬───────────────┘  │
│        MySQL (manga_reader)                 ▼                  │
│                              OkHttp + jsoup 下载流水线 → 本地磁盘 │
└────────────────────────────────────────────────────────────────┘
```

### 3.2 技术选型

| 分层 | 技术 | 版本建议 | 选型理由 |
| --- | --- | --- | --- |
| 后端框架 | Spring Boot | 3.4.5（继承父工程） | 沿用现有版本 |
| 持久层 | MyBatis + HikariCP + MySQL 8 | 现状版本 | 零改动复用 |
| 接口规范 | RESTful JSON | — | 统一 `Result<T>` 包装 |
| 实时推送 | SSE（SseEmitter） | Spring 内置 | 下载进度单向推送，比 WebSocket 轻量 |
| 前端框架 | Vue 3 + TypeScript | 3.4+ | 组合式 API，生态成熟 |
| 构建工具 | Vite | 5+ | 快速冷启动，代理配置简单 |
| UI 组件库 | Element Plus | 2.7+ | 表格 / 表单 / 进度条开箱即用 |
| 状态管理 | Pinia | 2+ | 轻量，管理阅读器与下载状态 |
| 路由 | Vue Router | 4+ | 书架 / 阅读 / 下载中心三路由 |
| HTTP 客户端 | Axios | 1.7+ | 统一拦截、错误处理 |
| 图片懒加载 | IntersectionObserver（原生） | — | 替代 `ComicVirtualFlow`，无需重量级虚拟列表库 |

### 3.3 前后端协作方式

- **开发环境**：前端 Vite Dev Server（`http://localhost:5173`）通过 proxy 将 `/api`、`/images` 转发到后端 `http://localhost:8080`，避免 CORS 问题。
- **生产环境（推荐）**：`vite build` 产物复制到 `MangaReader/src/main/resources/static/`，由 Spring Boot 直接托管，单 Jar 发布；`/api/**` 与前端路由共存（前端使用 hash 路由或配置 SPA fallback）。
- **生产环境（可选）**：Nginx 托管前端静态资源并反代 `/api`、`/images` 到 Spring Boot，适合图片并发量大的场景（可开启 `sendfile`、缓存）。

---

## 4. 后端重构方案

### 4.1 pom.xml 调整

**移除**：

```xml
<!-- 全部移除 -->
org.openjfx:javafx-controls / javafx-fxml / javafx-web / javafx-graphics / javafx-media / javafx-swing
<javafx.version> 属性
spring-boot-maven-plugin 中针对 org.openjfx 的 <excludes> 段
```

**保留**：`spring-boot-starter-web / aop / validation`、MyBatis、MySQL、HikariCP、OkHttp、jsoup、Guava、ImageIO、Lombok、Logback、test。

**mainClass 改为**：`com.mangareader.MangaReaderApplication`。

> 说明：`twelvemonkeys imageio` 与 `webp-imageio` 在 Web 版用于服务端读取图片宽高/转码（可选），暂保留；若确认前端直接展示原图，可在第二阶段移除以瘦身。

### 4.2 入口类重写

`MangaReaderApplication.java` 改为标准 Spring Boot 启动类：

```java
@Slf4j
@SpringBootApplication
@MapperScan("com.mangareader.mapper")
@EnableConfigurationProperties
@EnableScheduling
@EnableAsync
public class MangaReaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MangaReaderApplication.class, args);
    }
}
```

删除 `MainApp.java`。

### 4.3 统一响应与异常

新增 `model/common/Result.java`：

```java
@Data
public class Result<T> {
    private Integer code;      // 200 成功；其他为业务/系统错误码
    private String message;
    private T data;
    // static ok(data) / fail(code, message)
}
```

重写 `GlobalExceptionHandler` 为：

```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // MethodArgumentNotValidException → 400 参数校验失败
    // BusinessException(自定义) → 业务码
    // Exception → 500，仅返回日志不暴露堆栈
}
```

并新增自定义 `BusinessException`（如"漫画不存在""章节不存在"）。

### 4.4 静态图片资源服务

新增 `config/WebMvcConfig.java`，把数据库中的**本地磁盘绝对路径**映射为 HTTP URL：

```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 漫画图片： /images/**  -> file:${manga.storage.image-path}/
    // 封面/书库： /covers/**  -> file:${manga.storage.root}/
    // SPA fallback：非 /api、/images、/covers 的路径转发到 index.html（生产单 Jar 方案）
}
```

配套改造 `MangaImageService`：新增 `getImageUrl(MangaImage image)`，返回**浏览器可访问的相对 URL**（`/images/...`）而非磁盘路径；原 `getFullImagePath` 保留（下载服务仍在使用）。

> ⚠️ 安全要求：资源映射必须限定在配置的根目录内，防止 `../` 路径穿越（使用 `Path.normalize()` 校验）。

### 4.5 REST API 设计

统一前缀 `/api`，统一返回 `Result<T>`。

#### 4.5.1 漫画模块（`MangaController`）

| 方法 | 路径 | 说明 | 对应现有能力 |
| --- | --- | --- | --- |
| GET | `/api/manga/list` | 书架漫画列表（含封面 URL、状态、章节进度） | `MangaService.getAllManga()` |
| GET | `/api/manga/{mangaId}` | 漫画详情 | `MangaService.getMangaById()` |
| POST | `/api/manga` | 新增下载任务，body：`{mangaName, mangaUrl}`；落库为"待处理"，交由定时任务执行 | `MangaService.addManga(name,url)` + `updateMangaStatus(id,0)` |

请求/响应示例（新增任务）：

```json
// POST /api/manga
{ "mangaName": "某某漫画", "mangaUrl": "https://xxx.com/book/123" }

// Result<MangaVO>
{
  "code": 200, "message": "漫画已添加到下载队列，系统将自动处理",
  "data": { "mangaId": 12, "mangaName": "某某漫画", "mangaStatus": 0 }
}
```

参数校验：`mangaName` 非空、`mangaUrl` 必须为合法 URL（`@NotBlank` + `@URL`），对齐桌面版 `DownloadCenterController` 中的两条输入校验。

#### 4.5.2 章节与图片模块（`ChapterController`）

| 方法 | 路径 | 说明 | 对应现有能力 |
| --- | --- | --- | --- |
| GET | `/api/manga/{mangaId}/chapters` | 章节列表 | `ChapterService.getChaptersByMangaId()` |
| GET | `/api/chapter/{chapterId}` | 章节详情（含 `prevChapterId` / `nextChapterId`，便于前端禁用按钮） | `ChapterService.getChapterById()` |
| GET | `/api/chapter/{chapterId}/images` | 章节图片列表，返回 `[{imageId, url, sortOrder, width, height}]` | `MangaImageService.getImagesByChapterId()` + 新增 `getImageUrl()` |
| GET | `/api/chapter/{chapterId}/prev` | 上一章（无则 `data: null`） | `ChapterService.getPrevChapter()` |
| GET | `/api/chapter/{chapterId}/next` | 下一章 | `ChapterService.getNextChapter()` |

> 上一章/下一章按 `chapterNum` 判断，与桌面版 `updateButtonStates()` 逻辑一致。

#### 4.5.3 阅读器配置模块（`ReaderConfigController`）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/config/autoplay` | 返回 `AutoplayConfig` 五个参数（默认滚动距离、间隔、步长、最小/最大值），前端自动播放直接消费 |
| GET | `/api/config/reader` | 返回 `manga.reader.*`（preload-count、default-scale 等） |

#### 4.5.4 下载中心模块（`DownloadController` + SSE）

| 方法 | 路径 | 说明 | 对应现有能力 |
| --- | --- | --- | --- |
| GET | `/api/download/manga/list` | 漫画下载任务列表（状态、总/已处理章节数、心跳时间），支持按状态过滤 | `MangaService.getAllManga()` 扩展 |
| GET | `/api/download/image/stats` | 图片下载统计（总数、各状态数量、成功率） | `MangaImageDownloadService.getStatistics()` |
| GET | `/api/download/events` | **SSE 端点**，推送下载进度事件 | 新增 |

SSE 设计：

- 使用 `SseEmitter`（超时 0 或 30 分钟，配合前端自动重连）。
- 事件类型：`manga-progress`（漫画章节进度变化）、`image-stats`（图片统计，每 3~5 秒一次）、`manga-status`（状态跃迁：完成/失败）。
- 实现方式：新增 `DownloadEventPublisher`（基于 Spring `ApplicationEventPublisher` 或独立的 `SseEmitterRegistry`），在 `MangaDownloadScheduledTask` / `MangaImageDownloadServiceImpl` 状态变更处发布事件；注册表管理多个 Emitter，心跳包（`:ping`）每 15 秒一次防代理断开。
- 对现有定时任务**只做"发布事件"的一行式侵入**，不改变其调度与事务逻辑。

### 4.6 后端新增 / 修改文件清单

```
新增:
  config/WebMvcConfig.java                 # 静态资源映射 + SPA fallback
  config/CorsConfig.java                   # 开发环境允许 5173 跨域（可并入 WebMvcConfig）
  config/SseConfig.java                    # SseEmitter 注册表 / 线程池
  model/common/Result.java                 # 统一响应
  model/common/BusinessException.java      # 业务异常
  model/vo/MangaVO.java                    # 书架卡片视图对象
  model/vo/ChapterImageVO.java             # 图片 URL 视图对象
  model/vo/DownloadStatsVO.java            # 下载统计视图对象
  controller/MangaController.java
  controller/ChapterController.java
  controller/ReaderConfigController.java
  controller/DownloadController.java
  service/DownloadEventPublisher.java      # SSE 事件发布

修改:
  MangaReaderApplication.java              # 去 JavaFX
  handler/GlobalExceptionHandler.java      # @RestControllerAdvice
  service/MangaImageService(+Impl).java    # 新增 getImageUrl()
  task/MangaDownloadScheduledTask.java     # 状态变更处发布 SSE 事件（可选侵入）
  pom.xml                                  # 移除 JavaFX

删除:
  MainApp.java、controller/{Main,MangaShelf,Reader,DownloadCenter,TopNavigation}Controller.java
  component/ComicVirtualFlow.java、resources/fxml/**、resources/css/**
```

### 4.7 application.yml 调整

- 保留 `spring.datasource`、`mybatis`、`manga.*`、`logging` 全部配置。
- 新增：

```yaml
server:
  port: 8080
spring:
  web:
    resources:
      static-locations: classpath:/static/,file:${manga.storage.image-path}/
  mvc:
    async:
      request-timeout: 300000   # SSE 长连接超时
```

---

## 5. 前端开发方案

### 5.1 工程结构

前端工程建议放在 `MangaReader/manga-web/`（与后端同仓库，便于一起发布）：

```
manga-web/
├── index.html
├── vite.config.ts               # proxy: /api、/images → http://localhost:8080
├── package.json
├── src/
│   ├── main.ts                  # 挂载 Element Plus / Pinia / Router
│   ├── router/index.ts          # / 、/reader/:mangaId 、/download
│   ├── api/                     # axios 封装
│   │   ├── manga.ts             # 书架相关接口
│   │   ├── chapter.ts           # 章节/图片接口
│   │   ├── download.ts          # 下载中心接口 + SSE 封装
│   │   └── config.ts            # 阅读器配置接口
│   ├── stores/
│   │   ├── reader.ts            # 当前漫画/章节/页码/播放状态
│   │   └── download.ts          # 任务列表与统计
│   ├── components/
│   │   ├── MangaCard.vue        # 书架卡片（封面懒加载 + 默认封面兜底）
│   │   ├── ChapterList.vue      # 章节目录抽屉
│   │   ├── ComicScroller.vue    # 下拉式图片流（核心组件）
│   │   └── AutoPlayBar.vue      # 自动播放控制条（播放/加速/减速）
│   ├── views/
│   │   ├── ShelfView.vue        # 书架页
│   │   ├── ReaderView.vue       # 阅读页
│   │   └── DownloadView.vue     # 下载中心
│   └── assets/default_cover.png # 从 resources/images 迁移
```

### 5.2 书架页（ShelfView）

对齐 `MangaShelfController` 行为：

- `GET /api/manga/list` 渲染响应式网格卡片（封面 150×200、标题、状态角标：下载中/已完成）。
- 封面 `<img loading="lazy">`，`onerror` 兜底为 `default_cover.png`（对齐桌面版三层兜底逻辑）。
- 卡片点击 → `router.push(/reader/:mangaId)`。
- 顶部工具栏：刷新按钮、"添加漫画"入口（跳转下载中心）、顶部导航（书架 / 下载中心，替代 `TopNavigationController`）。

### 5.3 阅读页（ReaderView）

对齐 `ReaderController` 全部行为，是本次重构的核心页面：

**布局**：左侧章节目录（可折叠）+ 中间图片滚动区 + 底部状态栏（当前第 N 张 / 总张数 / 播放状态）。

**章节切换**：

- 进入页面：`GET /api/manga/{id}/chapters` 渲染目录，默认加载第一章（对齐 `loadManga()`）。
- 选中章节：`GET /api/chapter/{id}/images` 获取图片 URL 列表，滚动区重置到顶部。
- 上一章/下一章按钮：依据 `chapter/{id}` 返回的 `prevChapterId/nextChapterId` 禁用，对齐 `updateButtonStates()`。

**下拉式虚拟滚动（替代 `ComicVirtualFlow`）**：

- `ComicScroller.vue` 使用原生滚动容器 + `IntersectionObserver`：
  - 仅对视口上下各 2 屏内的 `<img>` 设置 `src`，其余渲染等比占位块（用接口返回的 `width/height` 计算，避免高度抖动）——等价于 JavaFX `VirtualFlow` 的单元回收。
  - 用 `observer` 回调计算"当前第一张可见图片索引"，更新"当前浏览：第 N 张"（对齐 `updateCurrentPageLabel()`）。

**自动播放（替代 `Timeline`）**：

- 启动时 `GET /api/config/autoplay` 获取参数（滚动距离/间隔/步长/上下限）。
- 播放：`setInterval(scrollInterval)` 内 `container.scrollBy(0, scrollDistance)`，对齐 `scrollToNextImage()`。
- 加速/减速：按 `scrollDistanceStep` 增减 `scrollDistance` 并限制在 `[min, max]`，重建定时器，对齐 `handleSpeedUp/handleSpeedDown()`。
- 到底检测：滚动到最后一张时自动请求下一章；无下一章则停止播放，对齐 `checkAutoPlayEnd()`。

**阅读进度（增量功能，可选）**：

- 利用闲置实体 `ReadingProgress` / `MangaChapterPageRecord` 落库：进入阅读页恢复上次章节与页码，滚动时防抖上报（30 秒间隔，对齐 `manga.reader.save-interval`）。

### 5.4 下载中心页（DownloadView）

对齐 `DownloadCenterController`：

- **新建任务表单**：漫画名称 + 漫画目录网址，前端校验非空（对齐原两条 `showAlert` 校验）+ 后端 `@Valid` 双重校验；成功后 `ElMessage` 提示"漫画已添加到下载队列，系统将自动处理"。
- **任务列表**：`GET /api/download/manga/list` 表格（名称、状态 Tag、已处理/总章节、进度条、最后心跳时间）；状态映射 `ProcessStatus`：0 待处理 / 1 处理中 / 2 已完成 / 3 失败。
- **图片下载统计**：`GET /api/download/image/stats` 展示统计卡片（总数、已完成、失败、成功率）。
- **SSE 实时刷新**：`new EventSource('/api/download/events')`，监听 `manga-progress` / `image-stats` / `manga-status` 事件增量更新表格与进度条；断线由浏览器自动重连，重连后先全量拉一次接口对齐数据。

---

## 6. 数据库

**零改动**。沿用 `resources/sql/table_0826.sql` 的四张表：

| 表 | 用途 |
| --- | --- |
| `manga` | 漫画主表（状态机 0/1/2/3、心跳、章节进度） |
| `chapter` | 章节（`uk_manga_chapter` 唯一约束） |
| `manga_image` | 图片（下载状态、重试、断点续传字段） |
| `manga_chapter_page_record` | 章节分页下载记录（多线程下载 + 重试） |

> 若实现 5.3 的阅读进度功能，需新增 `reading_progress` 表（字段：manga_id、chapter_id、page_num、updated_at，联合主键 manga_id），属于增量变更，不阻塞主流程。

---

## 7. 开发里程碑

| 阶段 | 内容 | 交付物 | 预估工期 |
| --- | --- | --- | --- |
| **M1 后端去 JavaFX** | pom 清理、入口类重写、删除 FXML 控制器与 ComicVirtualFlow、`mvn spring-boot:run` 空跑验证 | 可启动的纯 Web 后端 | 1 天 |
| **M2 REST API** | Result/异常体系、WebMvcConfig 静态映射、四个 Controller + VO、接口自测（Postman/单测） | 全部 `/api/**` 与 `/images/**` 可用 | 2~3 天 |
| **M3 SSE 推送** | SseEmitter 注册表、事件发布接入定时任务、心跳保活 | `/api/download/events` 稳定推送 | 1 天 |
| **M4 前端骨架 + 书架** | 工程初始化（Vite + Element Plus + Pinia + Router + Axios）、书架页、导航 | 书架可浏览、可跳转 | 1~2 天 |
| **M5 阅读页** | ComicScroller 虚拟滚动、章节目录/切换、自动播放三态、底部状态栏 | 阅读功能对齐桌面版 | 2~3 天 |
| **M6 下载中心** | 新建任务表单、任务表格、统计卡片、SSE 接入 | 下载中心对齐桌面版 | 1~2 天 |
| **M7 联调与发布** | 前后端联调、`vite build` 产物并入 `static/`、单 Jar 打包验证、阅读进度（可选） | 可发布版本 | 1~2 天 |

总预估：**9~13 人日**。

---

## 8. 测试策略

### 8.1 后端

- **单元测试**：沿用并扩展 `MangaDownloadServiceImplTest` 的写法，对新增 Controller 用 `@WebMvcTest` / `MockMvc` 覆盖：正常返回、参数校验失败（空名称/非法 URL）、漫画不存在 404、上一章/下一章边界（首章上一章为 null）。
- **Service 复用验证**：原有 `MangaService / ChapterService / MangaImageService` 逻辑不变，重点验证 `getImageUrl()` 生成的 URL 可被 `/images/**` 正确解析（含中文文件名、webp 类型）。
- **定时任务回归**：跑通一次完整下载流水线（待处理 → 处理中 → 完成），确认 SSE 事件同步发出、心跳超时回收逻辑不受影响。

### 8.2 前端

- 手动用例：书架封面缺失兜底；章节切换重置滚动位置；自动播放加/减速边界值；播放到底自动连章；SSE 断网重连后数据一致性；路径穿越尝试（`/images/../...` 必须 404）。

### 8.3 兼容回归

- 桌面版遗留数据（已有 `manga_reader` 库）直接启动 Web 版，验证存量漫画可正常展示与阅读。

---

## 9. 部署方案

**单 Jar 方案（推荐起步）**：

```powershell
# 1. 前端构建并拷贝产物
cd MangaReader/manga-web
npm run build
# 将 dist/* 复制到 MangaReader/src/main/resources/static/

# 2. 后端打包
cd ..
mvn clean package -DskipTests

# 3. 运行
java -jar target/MangaReader-1.0-SNAPSHOT.jar
# 浏览器访问 http://localhost:8080
```

**Nginx 方案（图片高并发时）**：Nginx 托管前端 `dist`，`location /api` 与 `/images` 反代到 `127.0.0.1:8080`，并配置 `expires` 缓存图片、`client_max_body_size` 等。

---

## 10. 风险与对策

| 风险 | 影响 | 对策 |
| --- | --- | --- |
| 静态资源路径穿越 | 泄露服务器文件 | 资源解析强制 `normalize()` 并校验前缀；单测覆盖 `../` 用例 |
| SSE 连接泄漏 | 线程/内存占用上涨 | Emitter 注册表统一超时与 `onCompletion/onTimeout` 清理；心跳探活 |
| 虚拟滚动高度抖动 | 阅读体验差 | 依赖 `manga_image` 的 `image_width/height` 预渲染占位；缺失宽高时以首图实际高度估算（对齐桌面版 `estimateCellHeight` 思路） |
| 大章节图片内存压力 | 浏览器卡顿 | 视口外图片回收 `src`（占位块保留）；控制同屏 `Image` 数量 ≤ `manga.image.max-memory-cache-size`(50) |
| 定时任务与 SSE 事件不同步 | 进度展示滞后 | 事件发布失败仅告警不阻断主流程；前端重连后全量拉取兜底 |
| JavaFX 代码残留引用导致编译失败 | 阻塞 M1 | M1 完成后全局搜索 `javafx.` 关键字确认清零 |
| 中文/特殊字符文件名 404 | 图片加载失败 | 后端返回 URL 统一 `URLEncoder` 处理，前端 `decode` 展示 |

---

## 附录 A：关键接口出入参速查

```
GET  /api/manga/list                     → Result<List<MangaVO>>
GET  /api/manga/{id}                     → Result<MangaVO>
POST /api/manga       {mangaName,mangaUrl}→ Result<MangaVO>
GET  /api/manga/{id}/chapters            → Result<List<Chapter>>
GET  /api/chapter/{id}                   → Result<ChapterVO>(含 prevChapterId/nextChapterId)
GET  /api/chapter/{id}/images            → Result<List<ChapterImageVO>>
GET  /api/chapter/{id}/prev | /next      → Result<Chapter|null>
GET  /api/config/autoplay                → Result<AutoplayVO>
GET  /api/download/manga/list            → Result<List<MangaTaskVO>>
GET  /api/download/image/stats           → Result<DownloadStatsVO>
GET  /api/download/events                → SSE: manga-progress / image-stats / manga-status
GET  /images/**                          → 静态图片流（映射 manga.storage.image-path）
GET  /covers/**                          → 静态封面流（映射 manga.storage.root）
```

## 附录 B：MangaVO / ChapterImageVO 字段约定

```
MangaVO:        mangaId, mangaName, coverUrl, mangaStatus(code+desc),
                totalChapters, processedChapters, createdAt, updatedAt
ChapterImageVO: imageId, sortOrder, url(/images/...), width, height
DownloadStatsVO: total, pending, downloading, completed, failed, successRate
```

