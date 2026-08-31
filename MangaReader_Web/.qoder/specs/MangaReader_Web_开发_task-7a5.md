# MangaReader Web 化重构实施计划

## 阶段 1: 后端基础搭建 (M1)

将原始 MangaReader 项目中可复用的业务代码迁移到 MangaReader_Web，同时移除所有 JavaFX 依赖。

### 1.1 pom.xml 完善

基于原始项目 `D:\gitProject\LeetCode_QA\MangaReader\pom.xml` 的 dependencies 段，在 `d:\gitProject\LeetCode_QA\MangaReader_Web\pom.xml` 中补全：

- 添加 `<parent>` 指向 `spring-boot-starter-parent:3.4.5`（原项目通过父工程继承，此处需独立声明）
- 添加全部 dependencies（spring-boot-starter-web/aop/validation, mybatis-spring-boot-starter, mysql-connector-j, HikariCP, mybatis, OkHttp, jsoup, Guava, TwelveMonkeys ImageIO, webp-imageio, Lombok, SLF4J, Logback, spring-boot-starter-test）
- **不添加**任何 `org.openjfx` 依赖
- 添加 `<build>` 段：spring-boot-maven-plugin（mainClass 改为 `com.mangareader.MangaReaderApplication`）、maven-compiler-plugin、maven-resources-plugin
- 移除 `<properties>` 中的 `<javafx.version>`

### 1.2 迁移复用代码（从 `D:\gitProject\LeetCode_QA\MangaReader\src` 复制到 `d:\gitProject\LeetCode_QA\MangaReader_Web\src`）

以下文件**原样复制**，无需修改（无 JavaFX 引用）：

| 目录 | 文件 | 说明 |
|------|------|------|
| `model/entity/` | Manga.java, Chapter.java, MangaImage.java, MangaChapterPageRecord.java, Bookmark.java, ReadingProgress.java | 6 个实体类 |
| `enums/` | ProcessStatus.java | 状态枚举 |
| `constant/` | ImageConstant.java | 常量 |
| `mapper/` | MangaMapper.java, ChapterMapper.java, MangaImageMapper.java, MangaChapterPageRecordMapper.java | 4 个 Mapper 接口 |
| `resources/mapper/` | MangaMapper.xml, ChapterMapper.xml, MangaImageMapper.xml, MangaChapterPageRecordMapper.xml | 4 个 Mapper XML |
| `service/` | MangaService.java, ChapterService.java, MangaImageService.java, MangaImageDownloadService.java, MangaDownloadService.java, ImageService.java | 6 个 Service 接口 |
| `service/impl/` | MangaServiceImpl.java, ChapterServiceImpl.java, MangaImageServiceImpl.java, MangaImageDownloadServiceImpl.java, MangaDownloadServiceImpl.java, ImageServiceImpl.java | 6 个 Service 实现 |
| `config/` | AutoplayConfig.java, MangaAsyncConfig.java, MangaDownloadConfig.java, MangaDownloadProperties.java, MangaOkHttpConfig.java, MangaProperties.java, ProcessStatusTypeHandler.java, ThreadPoolConfig.java | 8 个配置类 |
| `task/` | MangaDownloadScheduledTask.java, MangaImageDownloadScheduledTask.java | 2 个定时任务 |
| `utils/` | ImageUtil.java | 图片工具 |

### 1.3 新建入口类

`src/main/java/com/mangareader/MangaReaderApplication.java` — 标准 Spring Boot 启动类（不继承 JavaFX Application）：

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

### 1.4 迁移并修改 application.yml

从原始项目复制 `application.yml`，新增 Web 相关配置：

```yaml
server:
  port: 8080
spring:
  web:
    resources:
      static-locations: classpath:/static/,file:${manga.storage.image-path}/
  mvc:
    async:
      request-timeout: 300000
```

### 1.5 迁移 SQL 和静态资源

- 复制 `resources/sql/table_0826.sql` 到 MangaReader_Web
- 复制 `resources/images/default_cover.png` 到 MangaReader_Web（后续迁移到前端）

### 1.6 验证

- 执行 `mvn compile` 确认编译通过
- 确认无 `javafx` 关键字残留（全局搜索验证）

---

## 阶段 2: REST API 层 (M2)

### 2.1 统一响应与异常

新建文件：
- `model/common/Result.java` — 统一响应包装 `{code, message, data}`，含 `static ok(data)` / `fail(code, message)`
- `model/common/BusinessException.java` — 自定义业务异常 `{code, message}`

### 2.2 重写 GlobalExceptionHandler

`handler/GlobalExceptionHandler.java` — 改为 `@RestControllerAdvice`：
- `MethodArgumentNotValidException` -> 400
- `BusinessException` -> 业务码
- `Exception` -> 500（仅返回消息，不暴露堆栈）

### 2.3 视图对象 (VO)

新建文件：
- `model/vo/MangaVO.java` — 字段：mangaId, mangaName, coverUrl, mangaStatus(code+desc), totalChapters, processedChapters, createdAt, updatedAt
- `model/vo/ChapterVO.java` — 扩展 Chapter 实体，增加 prevChapterId, nextChapterId
- `model/vo/ChapterImageVO.java` — 字段：imageId, sortOrder, url(/images/...), width, height
- `model/vo/DownloadStatsVO.java` — 字段：total, pending, downloading, completed, failed, successRate
- `model/vo/AutoplayVO.java` — 从 AutoplayConfig 映射的五个参数
- `model/dto/MangaAddRequest.java` — 新增漫画请求体 `{mangaName(@NotBlank), mangaUrl(@NotBlank @URL)}`

### 2.4 WebMvcConfig + CorsConfig

- `config/WebMvcConfig.java` — 静态资源映射 `/images/**` -> `file:${manga.storage.image-path}/`，`/covers/**` -> `file:${manga.storage.root}/`，SPA fallback（非 /api、/images、/covers 转发 index.html），安全校验（Path.normalize() 防穿越）
- `config/CorsConfig.java` — 开发环境允许 `http://localhost:5173` 跨域

### 2.5 四个 REST Controller

**MangaController** (`/api/manga`):
- `GET /api/manga/list` — 书架列表，Manga -> MangaVO 转换（含 coverUrl 生成）
- `GET /api/manga/{mangaId}` — 漫画详情
- `POST /api/manga` — 新增下载任务（@Valid MangaAddRequest），调用 MangaService.addManga(name, url)

**ChapterController** (`/api/manga` + `/api/chapter`):
- `GET /api/manga/{mangaId}/chapters` — 章节列表
- `GET /api/chapter/{chapterId}` — 章节详情（含 prevChapterId/nextChapterId）
- `GET /api/chapter/{chapterId}/images` — 图片列表（url 转为 `/images/...` 格式）
- `GET /api/chapter/{chapterId}/prev` — 上一章
- `GET /api/chapter/{chapterId}/next` — 下一章

**ReaderConfigController** (`/api/config`):
- `GET /api/config/autoplay` — 返回 AutoplayConfig 参数
- `GET /api/config/reader` — 返回 manga.reader.* 配置

**DownloadController** (`/api/download`):
- `GET /api/download/manga/list` — 漫画下载任务列表（支持 ?status= 过滤）
- `GET /api/download/image/stats` — 图片下载统计
- `GET /api/download/events` — SSE 端点（阶段 3 实现）

### 2.6 修改 MangaImageService

在 `MangaImageService` 接口和实现中新增 `getImageUrl(MangaImage image)` 方法，返回浏览器可访问的相对 URL（`/images/...`），原 `getFullImagePath` 保留。

---

## 阶段 3: SSE 推送 (M3)

### 3.1 SSE 基础设施

- `config/SseConfig.java` — SseEmitter 注册表（`SseEmitterRegistry`），管理多个 Emitter 生命周期，含超时清理、心跳保活（15 秒 `:ping`）
- `service/DownloadEventPublisher.java` — 事件发布服务，在 manga-progress / image-stats / manga-status 状态变更时推送

### 3.2 接入定时任务

在 `MangaDownloadScheduledTask` 和 `MangaImageDownloadServiceImpl` 的状态变更处注入 `DownloadEventPublisher`，发布 SSE 事件。仅做"一行式侵入"，不改变原有调度与事务逻辑。

---

## 阶段 4: 前端骨架 + 书架页 (M4)

### 4.1 工程初始化

在 `d:\gitProject\LeetCode_QA\MangaReader_Web\manga-web\` 创建 Vue 3 + TypeScript 前端工程：

```
manga-web/
├── index.html
├── vite.config.ts          # proxy /api, /images -> http://localhost:8080
├── package.json
├── tsconfig.json
├── src/
│   ├── main.ts             # 挂载 Element Plus / Pinia / Router
│   ├── App.vue             # 根组件（含顶部导航）
│   ├── router/index.ts     # 三路由：/ 、/reader/:mangaId 、/download
│   ├── api/                # axios 封装 + 各模块接口
│   ├── stores/             # Pinia 状态管理
│   ├── components/         # 公共组件
│   ├── views/              # 页面视图
│   └── assets/             # 静态资源（default_cover.png）
```

技术栈：Vue 3 + TypeScript + Vite 5 + Element Plus 2.7+ + Pinia 2 + Vue Router 4 + Axios 1.7+

### 4.2 顶部导航栏

App.vue 中实现顶部导航（替代原 TopNavigationController）：
- 书架（`/`）| 下载中心（`/download`）
- Element Plus `el-menu` 水平模式

### 4.3 书架页 (ShelfView.vue)

- `GET /api/manga/list` 渲染响应式网格卡片（el-row + el-col 或 CSS Grid）
- `MangaCard.vue` 组件：封面 150x200（`loading="lazy"` + `onerror` 兜底 default_cover.png）、标题、状态角标
- 点击卡片 -> `router.push(/reader/:mangaId)`
- 顶部工具栏：刷新按钮、"添加漫画"按钮（跳转 /download）

### 4.4 API 层封装

- `api/request.ts` — Axios 实例，baseURL `/api`，统一拦截（错误处理、loading）
- `api/manga.ts` — getMangaList, getMangaDetail, addManga
- `api/chapter.ts` — getChapters, getChapterDetail, getChapterImages, getPrevChapter, getNextChapter
- `api/download.ts` — getDownloadList, getImageStats, connectSSE
- `api/config.ts` — getAutoplayConfig, getReaderConfig

---

## 阶段 5: 阅读页 (M5)

### 5.1 ReaderView.vue 布局

- 左侧：章节目录（可折叠 el-drawer 或 el-aside）
- 中间：`ComicScroller.vue` 图片滚动区
- 底部：状态栏（当前第 N 张 / 总张数 / 播放状态）
- 顶部：章节切换按钮（上一章/下一章）

### 5.2 ComicScroller.vue（核心组件）

替代原 `ComicVirtualFlow`：
- 原生滚动容器 + `IntersectionObserver`
- 视口上下各 2 屏内的 `<img>` 设置 `src`，其余渲染等比占位块（用接口返回的 width/height 计算）
- observer 回调更新"当前浏览：第 N 张"
- 视口外图片回收 `src`（占位块保留），控制同屏 Image 数量 <= 50

### 5.3 章节切换逻辑

- 进入页面：获取章节列表，默认加载第一章
- 选中章节：获取图片 URL 列表，滚动区重置到顶部
- 上一章/下一章按钮：依据 prevChapterId/nextChapterId 控制禁用状态

### 5.4 自动播放 (AutoPlayBar.vue)

- 获取 `/api/config/autoplay` 参数
- 播放：`setInterval` + `container.scrollBy(0, scrollDistance)`
- 加速/减速：按 step 调整 scrollDistance，限制在 [min, max]
- 到底检测：自动请求下一章；无下一章则停止

### 5.5 Pinia Reader Store

`stores/reader.ts`：管理当前 mangaId、chapterId、图片列表、当前页码、播放状态

---

## 阶段 6: 下载中心 (M6)

### 6.1 DownloadView.vue

- **新建任务表单**：漫画名称 + 漫画目录网址，前端校验非空 + 后端 @Valid 双重校验
- **任务列表**：el-table 展示（名称、状态 Tag、已处理/总章节、进度条、心跳时间）
  - 状态映射：0 待处理 / 1 处理中 / 2 已完成 / 3 失败
- **图片下载统计**：统计卡片（总数、已完成、失败、成功率）
- **SSE 实时刷新**：`EventSource('/api/download/events')` 监听事件增量更新；断线浏览器自动重连

### 6.2 Pinia Download Store

`stores/download.ts`：管理任务列表、统计数据、SSE 连接状态

---

## 阶段 7: 联调与发布 (M7)

### 7.1 前后端联调

- 启动后端 `mvn spring-boot:run`（端口 8080）
- 启动前端 `npm run dev`（端口 5173，Vite proxy 转发）
- 验证全部功能链路

### 7.2 生产打包

- `npm run build` 生成前端产物
- 将 `dist/*` 复制到 `src/main/resources/static/`
- `mvn clean package -DskipTests` 打单 Jar
- `java -jar target/MangaReader_Web-1.0-SNAPSHOT.jar` 一键启动
- 访问 `http://localhost:8080` 验证

---

## 文件清单总览

**后端新增文件（MangaReader_Web/src/main/java/com/mangareader/）：**
- MangaReaderApplication.java（重写）
- model/common/Result.java, BusinessException.java
- model/vo/MangaVO.java, ChapterVO.java, ChapterImageVO.java, DownloadStatsVO.java, AutoplayVO.java
- model/dto/MangaAddRequest.java
- controller/MangaController.java, ChapterController.java, ReaderConfigController.java, DownloadController.java
- handler/GlobalExceptionHandler.java（重写）
- config/WebMvcConfig.java, CorsConfig.java, SseConfig.java
- service/DownloadEventPublisher.java

**后端迁移文件（从 MangaReader 原样复制）：** 37 个文件中的约 30 个（entity/enums/constant/mapper/service/config/task/utils）

**前端新建文件（manga-web/）：** 约 25-30 个 Vue/TS 文件

**数据库：** 零改动，沿用 4 张表
