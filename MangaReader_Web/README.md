# MangaReader Web

> 本地下拉式漫画阅读器的 Web 版本，由原 **JavaFX 桌面版** 重构而来，采用 **Spring Boot（后端）+ Vue 3（前端）** 的 B/S 架构，支持书架浏览、下拉式阅读、自动播放、漫画下载与下载进度实时推送。

---

## 目录

- [一、项目简介](#一项目简介)
- [二、技术栈](#二技术栈)
- [三、项目结构](#三项目结构)
- [四、环境要求](#四环境要求)
- [五、数据库初始化](#五数据库初始化)
- [六、后端启动与打包](#六后端启动与打包)
- [七、前端安装详细教程](#七前端安装详细教程)
- [八、开发环境联调](#八开发环境联调)
- [九、生产环境部署](#九生产环境部署)
- [十、关键配置说明](#十关键配置说明)
- [十一、常见问题](#十一常见问题)

---

## 一、项目简介

| 功能 | 说明 |
| --- | --- |
| 书架页 `/` | 漫画网格展示、封面加载、默认封面兜底 |
| 阅读页 `/reader/:mangaId` | 下拉式虚拟滚动阅读、章节切换、阅读进度记忆、自动播放（可调速/自动连章） |
| 下载中心 `/download` | 新增漫画下载任务、下载进度与状态展示（SSE 实时推送） |

前端采用 **hash 路由**（`createWebHashHistory`），生产环境无需服务端配置 SPA fallback。

---

## 二、技术栈

### 后端

| 技术 | 版本 |
| --- | --- |
| Java | 17 |
| Spring Boot | 3.4.5 |
| MyBatis + MyBatis Starter | 3.5.13 / 3.0.3 |
| HikariCP | Spring Boot 内置 |
| MySQL | 8.x |
| OkHttp（下载） | 4.12.0 |
| jsoup（爬虫解析） | 1.17.2 |
| Lombok / SLF4J + Logback | 1.18.30 / 2.0.13 + 1.5.25 |

### 前端

| 技术 | 版本 |
| --- | --- |
| Vue | 3.4+ |
| TypeScript | 5.4 |
| Vite | 5.4 |
| Element Plus | 2.7+ |
| Pinia | 2.1+ |
| Vue Router | 4.3+ |
| Axios | 1.7+ |

---

## 三、项目结构

```
MangaReader_Web
├── src/main/java/com/mangareader/       # 后端源码
│   ├── controller/                      # REST 控制器（漫画/章节/下载/阅读器配置）
│   ├── service/ + service/impl/         # 业务层（含下载流水线）
│   ├── mapper/                          # MyBatis 数据访问
│   ├── task/                            # 漫画下载 + 图片下载定时任务
│   ├── model/                           # entity / dto / vo / common(Result)
│   ├── config/                          # 各类配置（线程池、SSE、CORS 等）
│   └── MangaReaderApplication.java      # Spring Boot 启动类
├── src/main/resources/
│   ├── application.yml                  # 后端配置文件
│   ├── mapper/                          # MyBatis XML
│   ├── sql/table.sql                    # 数据库建表脚本
│   └── images/                          # 默认封面等静态资源
├── manga-web/                           # 前端工程（Vue 3 + Vite）
│   ├── src/
│   │   ├── api/                         # Axios 接口封装（baseURL: /api）
│   │   ├── components/                  # 阅读器、章节列表、漫画卡片等组件
│   │   ├── views/                       # 书架 / 阅读 / 下载中心三个页面
│   │   ├── router/                      # 路由（hash 模式）
│   │   └── stores/                      # Pinia 状态
│   ├── package.json
│   └── vite.config.ts                   # Dev Server 端口与代理配置
└── pom.xml                              # Maven 构建配置
```

---

## 四、环境要求

| 环境 | 版本要求 | 说明 |
| --- | --- | --- |
| JDK | **17+** | 后端编译与运行必须 |
| Maven | 3.6+ | 后端构建打包 |
| Node.js | **18+**（推荐 20 LTS） | 前端运行与构建（Vite 5 要求 Node 18+） |
| npm | 9+（随 Node 安装） | 前端包管理器，也可换用 pnpm / yarn |
| MySQL | 8.x | 数据库，需提前创建并初始化 |

---

## 五、数据库初始化

1. 安装并启动 MySQL 8.x 服务。
2. 执行建表脚本（脚本内已包含 `CREATE DATABASE`）：

```bash
mysql -u root -p < src/main/resources/sql/table.sql
```

或使用 Navicat / DataGrip 等工具直接打开并执行 `src/main/resources/sql/table.sql`。

3. 按 `application.yml` 的默认配置，需要数据库用户：

```sql
CREATE USER IF NOT EXISTS 'edms'@'localhost' IDENTIFIED BY 'edms';
GRANT ALL PRIVILEGES ON manga_reader.* TO 'edms'@'localhost';
FLUSH PRIVILEGES;
```

> 若你使用其他账号密码，请直接修改 `src/main/resources/application.yml` 中 `spring.datasource` 的 `url / username / password`。

---

## 六、后端启动与打包

### 6.1 启动前配置

打开 `src/main/resources/application.yml`，按本机环境确认以下配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/manga_reader?...   # 数据库地址
    username: edms
    password: edms

server:
  port: 8080          # 后端端口

manga:
  storage:
    root: D:/MangaLibrary          # 漫画库根目录（需存在且有写权限）
    image-path: D:/images/TestManga # 图片存储目录
    cache-root: D:/MangaReader/cache
```

### 6.2 开发模式启动

在 `MangaReader_Web` 目录下执行：

```powershell
# 方式一：Maven 插件直接运行
mvn spring-boot:run
```

或在 IDEA 中直接运行 `com.mangareader.MangaReaderApplication` 的 `main` 方法。

启动成功后访问接口验证：`http://localhost:8080/api/config/reader`

### 6.3 打包

```powershell
# 跳过测试打包（推荐）
mvn clean package -DskipTests
```

打包产物位于：

```
target/MangaReader_Web-1.0-SNAPSHOT.jar
```

### 6.4 运行 Jar 包

```powershell
java -jar target/MangaReader_Web-1.0-SNAPSHOT.jar
```

支持通过命令行参数覆盖配置：

```powershell
# 指定端口、外部配置文件
java -jar target/MangaReader_Web-1.0-SNAPSHOT.jar --server.port=9090 --spring.config.location=D:/manga/application.yml

# 后台运行（Linux）
nohup java -jar MangaReader_Web-1.0-SNAPSHOT.jar > manga-reader.out 2>&1 &
```

日志输出到运行目录下的 `logs/manga-reader.log`（滚动保留 30 天）。

---

## 七、前端安装详细教程

> 适用系统：Windows（PowerShell），macOS / Linux 同理（命令去掉 `.cmd` 后缀即可）。

### 7.1 安装 Node.js

**推荐方式：使用 nvm-windows 管理 Node 版本（可选但推荐）**

1. 下载安装包：[nvm-windows releases](https://github.com/coreybutler/nvm-windows/releases) 下载 `nvm-setup.exe` 并安装。
2. 安装 Node 20 LTS：

```powershell
nvm install 20
nvm use 20
```

**普通方式：官网直接安装**

1. 打开 [https://nodejs.org](https://nodejs.org)，下载 **LTS 版本**（20.x 或以上）的 Windows Installer (.msi)。
2. 双击安装，一路 Next，保持默认勾选 "Add to PATH"。
3. 安装完成后**重新打开**一个 PowerShell 窗口验证：

```powershell
node -v    # 应输出 v20.x.x 或更高
npm -v     # 应输出 10.x.x 或更高
```

若提示"不是内部或外部命令"，说明 PATH 未生效，请重启终端或检查环境变量中是否包含 Node 安装目录。

### 7.2 配置 npm 镜像（国内网络强烈推荐）

官方源在国内下载依赖较慢，建议切换为淘宝镜像：

```powershell
# 设置镜像
npm config set registry https://registry.npmmirror.com

# 验证
npm config get registry   # 应输出 https://registry.npmmirror.com
```

> 如需恢复官方源：`npm config set registry https://registry.npmjs.org`

### 7.3 安装依赖

进入前端工程目录：

```powershell
cd D:\gitProject\LeetCode_QA\MangaReader_Web\manga-web
```

安装依赖：

```powershell
npm install
```

如果安装过程中报权限 / 缓存错误，可依次尝试：

```powershell
# 清理缓存后重装
npm cache clean --force
npm install

# 或删除 node_modules 与锁文件后重装（PowerShell）
Remove-Item -Recurse -Force node_modules
Remove-Item package-lock.json
npm install
```

安装完成后，目录下会生成 `node_modules/` 文件夹和 `package-lock.json` 文件。

### 7.4 启动开发服务器

```powershell
npm run dev
```

输出类似：

```
VITE v5.x  ready in xxx ms

➜  Local:   http://localhost:5173/
```

浏览器打开 `http://localhost:5173/` 即可。

> 开发服务器已内置代理：`/api`、`/images`、`/covers` 全部转发到 `http://localhost:8080`，所以**必须先启动后端**，否则页面接口会报错（详见[第八节](#八开发环境联调)）。

### 7.5 前端可用脚本一览

| 命令 | 作用 |
| --- | --- |
| `npm run dev` | 启动 Vite 开发服务器（热更新，端口 5173） |
| `npm run build` | 类型检查（vue-tsc）+ 生产构建，产物输出到 `dist/` |
| `npm run preview` | 本地预览 `dist/` 构建产物 |

### 7.6 生产构建

```powershell
npm run build
```

- 先执行 `vue-tsc` 进行 TypeScript 类型检查，类型错误会中断构建；
- 再由 `vite build` 打包，产物输出到 `manga-web/dist/` 目录。

> 若类型检查报错但你只想快速出包，可临时执行 `npx vite build` 跳过检查（不推荐长期使用）。

---

## 八、开发环境联调

开发模式下前后端分离运行：

```
浏览器
  │
  ▼
Vite Dev Server  http://localhost:5173      ← npm run dev
  │  /api/**、/images/**、/covers/** 代理
  ▼
Spring Boot      http://localhost:8080      ← mvn spring-boot:run
  │
  ▼
MySQL            localhost:3306/manga_reader
```

- 前端 Axios `baseURL = /api`，所有接口走代理，无 CORS 问题（后端同时配置了 `CorsConfig` 兜底）。
- 启动顺序建议：**MySQL → 后端 → 前端**。
- 后端接口统一返回 `Result<T>` 结构：`{ code, message, data }`，`code = 200` 表示成功。

---

## 九、生产环境部署

### 方案一：单 Jar 部署（推荐，最简单）

将前端构建产物交给 Spring Boot 托管，一个 Jar 包发布：

```powershell
# 1. 构建前端
cd manga-web
npm run build

# 2. 将 dist 内容复制到后端静态资源目录
Copy-Item -Recurse -Force dist\* ..\src\main\resources\static\

# 3. 重新打包后端
cd ..
mvn clean package -DskipTests

# 4. 运行
java -jar target/MangaReader_Web-1.0-SNAPSHOT.jar
```

浏览器访问 `http://服务器IP:8080/` 即可。前端使用 **hash 路由**，刷新不会 404，无需额外的 fallback 配置。

### 方案二：Nginx 反向代理（适合图片访问量大的场景）

前端静态资源由 Nginx 托管，接口与图片反向代理到后端：

```nginx
server {
    listen       80;
    server_name  localhost;

    # 前端静态资源
    location / {
        root   /usr/share/nginx/html/manga-web;   # dist 产物部署目录
        index  index.html;
        try_files $uri $uri/ /index.html;
    }

    # 接口代理
    location /api/ {
        proxy_pass http://127.0.0.1:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }

    # 图片资源代理（可加缓存）
    location /images/ {
        proxy_pass http://127.0.0.1:8080/images/;
        expires 7d;
    }

    location /covers/ {
        proxy_pass http://127.0.0.1:8080/covers/;
        expires 7d;
    }

    # SSE 下载进度推送：关闭缓冲，保证实时性
    location /api/download/ {
        proxy_pass http://127.0.0.1:8080/api/download/;
        proxy_buffering off;
        proxy_cache off;
        proxy_read_timeout 300s;
    }
}
```

---

## 十、关键配置说明

`application.yml` 中的重要配置项：

| 配置 | 默认值 | 说明 |
| --- | --- | --- |
| `server.port` | 8080 | 后端服务端口 |
| `spring.datasource.*` | localhost:3306/manga_reader | 数据库连接 |
| `manga.storage.root` | D:/MangaLibrary | 漫画库根目录 |
| `manga.storage.image-path` | D:/images/TestManga | 图片存储目录（同时映射为静态资源） |
| `manga.reader.preload-count` | 20 | 阅读器预加载图片数 |
| `manga.image.supported-formats` | jpg,jpeg,png,webp,gif | 支持的图片格式 |
| `manga.download.*` | — | 下载源地址、请求头、CSS 选择器等爬虫配置 |
| `manga.download-config.scan-cron` | 0/2 * * * * ? | 下载任务扫描频率（每 2 秒） |
| `manga.download-config.max-retry` | 3 | 下载失败最大重试次数 |

---

## 十一、常见问题

**Q1：前端页面打开后接口全部报错 / 空白？**
A：确认后端（8080）已启动，且数据库可正常连接。开发模式下前端依赖 Vite 代理，直接打开 `dist` 文件是不行的。

**Q2：`npm install` 非常慢或超时？**
A：配置淘宝镜像（见 7.2），或使用 `npm install --registry=https://registry.npmmirror.com`。

**Q3：`npm run build` 报 TypeScript 类型错误？**
A：`build` 脚本包含 `vue-tsc` 类型检查。根据报错提示修复类型问题；临时跳过可执行 `npx vite build`。

**Q4：启动后端报数据库连接失败？**
A：检查 `application.yml` 中数据库地址 / 账号 / 密码，确认 `manga_reader` 库已通过 `table.sql` 初始化。

**Q5：下载任务一直不动？**
A：`manga.download.base-url` 为示例占位地址（`https://test.com`），请替换为实际漫画源站点，并同步调整 `css-selector`、`referer`、`user-agent` 等。

**Q6：端口被占用？**
A：`netstat -ano | findstr :8080` 找到占用进程；或修改 `server.port` 并同步修改 `manga-web/vite.config.ts` 中的代理目标。

---

## 相关文档

- 开发设计与重构方案：[docs/development_plan.md](docs/development_plan.md)
- 桌面版（JavaFX）原始工程：`../MangaReader`
