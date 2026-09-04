# MangaReader_Web 功能扩展实施计划

---

## 一、数据库变更

新增 SQL 脚本 `src/main/resources/sql/table_v2.sql`，包含以下新表：

### 1.1 用户表 `user`
```sql
CREATE TABLE user (
    user_id       BIGINT       NOT NULL AUTO_INCREMENT,
    username      VARCHAR(50)  NOT NULL COMMENT '用户名',
    password_hash VARCHAR(255) NOT NULL COMMENT 'BCrypt加密密码',
    email         VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    nickname      VARCHAR(50)  DEFAULT '' COMMENT '昵称',
    avatar_url    VARCHAR(500) DEFAULT '' COMMENT '头像路径',
    status        TINYINT      NOT NULL DEFAULT 1 COMMENT '1-正常 0-禁用',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
```

### 1.2 书架文件夹表 `shelf_folder`
```sql
CREATE TABLE shelf_folder (
    folder_id   BIGINT       NOT NULL AUTO_INCREMENT,
    user_id     BIGINT       NOT NULL,
    folder_name VARCHAR(100) NOT NULL COMMENT '文件夹名称',
    sort_order  INT          NOT NULL DEFAULT 0 COMMENT '排序',
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (folder_id),
    INDEX idx_user_id (user_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='书架文件夹表';
```

### 1.3 书架漫画关联表 `shelf_manga`
```sql
CREATE TABLE shelf_manga (
    id        BIGINT   NOT NULL AUTO_INCREMENT,
    user_id   BIGINT   NOT NULL,
    manga_id  BIGINT   NOT NULL,
    folder_id BIGINT   DEFAULT NULL COMMENT '所属文件夹,NULL表示未分类',
    added_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_manga (user_id, manga_id),
    INDEX idx_user_folder (user_id, folder_id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='书架漫画关联表';
```

### 1.4 改造 `reading_progress` 表
- 增加 `user_id` 字段（现有实体已有该字段，DB 表需对齐）
- 增加 `page_index` 字段用于记录分页页码

---

## 二、账号体系模块（后端）

### 2.1 新增依赖 (`pom.xml`)
- `spring-boot-starter-security`
- `jjwt-api` / `jjwt-impl` / `jjwt-jackson`（0.12.x）

### 2.2 新增文件清单

| 层 | 文件路径 | 说明 |
|---|---|---|
| Entity | `model/entity/User.java` | 用户实体 |
| Mapper | `mapper/UserMapper.java` + `resources/mapper/UserMapper.xml` | MyBatis Mapper |
| Service | `service/UserService.java` + `service/impl/UserServiceImpl.java` | 用户业务逻辑 |
| Controller | `controller/AuthController.java` | 注册/登录/登出 |
| Controller | `controller/UserController.java` | 个人信息/密码修改 |
| DTO | `model/dto/LoginRequest.java`, `RegisterRequest.java`, `PasswordResetRequest.java` | 请求 DTO |
| VO | `model/vo/UserVO.java`, `LoginVO.java` | 响应 VO（LoginVO 含 token） |
| Security | `config/SecurityConfig.java` | Spring Security 配置（放行注册/登录接口，其余需认证） |
| Security | `security/JwtUtils.java` | JWT 生成/解析工具 |
| Security | `security/JwtAuthenticationFilter.java` | OncePerRequestFilter，从 Redis 取 token 验证 |
| Security | `security/UserDetailsServiceImpl.java` | 实现 UserDetailsService |
| Config | `application.yml` 新增 `jwt.secret`、`jwt.expiration`（分钟） | JWT 配置 |

### 2.3 API 设计

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/auth/register` | 注册（username + password + email） |
| POST | `/api/auth/login` | 登录，返回 JWT token |
| POST | `/api/auth/logout` | 登出，删除 Redis 中的 token |
| GET  | `/api/auth/me` | 获取当前用户信息 |
| PUT  | `/api/user/profile` | 修改昵称/头像/邮箱 |
| PUT  | `/api/user/password` | 修改密码（需验证旧密码） |
| POST | `/api/user/password/reset` | 忘记密码（通过邮箱验证码，验证码存 Redis） |

### 2.4 Token 机制
- 登录成功后生成 JWT，同时将 `token -> userId` 映射存入 Redis，TTL = 配置的过期时间
- JwtAuthenticationFilter 每次请求从 `Authorization: Bearer <token>` 提取 token，查 Redis 验证
- 登出时删除 Redis 中的 token
- 密码使用 BCrypt 加密

---

## 三、书架管理模块（后端）

### 3.1 新增文件清单

| 层 | 文件路径 | 说明 |
|---|---|---|
| Entity | `model/entity/ShelfFolder.java`, `model/entity/ShelfManga.java` | 文件夹 / 关联实体 |
| Mapper | `mapper/ShelfFolderMapper.java` + XML, `mapper/ShelfMangaMapper.java` + XML | MyBatis Mapper |
| Service | `service/ShelfService.java` + `impl/ShelfServiceImpl.java` | 书架业务逻辑 |
| Controller | `controller/ShelfController.java` | 书架 REST API |
| VO | `model/vo/ShelfFolderVO.java`, `model/vo/ShelfMangaVO.java` | 响应 VO |
| DTO | `model/dto/FolderCreateRequest.java`, `model/dto/MangaMoveRequest.java` | 请求 DTO |

### 3.2 API 设计

| 方法 | 路径 | 说明 |
|---|---|---|
| GET  | `/api/shelf/folders` | 获取当前用户所有文件夹 |
| POST | `/api/shelf/folder` | 新建文件夹 |
| PUT  | `/api/shelf/folder/{folderId}` | 重命名文件夹 |
| DELETE | `/api/shelf/folder/{folderId}` | 删除文件夹（内漫画移至未分类） |
| GET  | `/api/shelf/mangas` | 获取书架漫画列表（支持 ?folderId= 筛选） |
| POST | `/api/shelf/manga` | 添加漫画到书架（body: mangaId, folderId） |
| PUT  | `/api/shelf/manga/{mangaId}/folder` | 移动漫画到指定文件夹 |
| DELETE | `/api/shelf/manga/{mangaId}` | 从书架移除漫画 |

---

## 四、阅读器增强模块（后端）

### 4.1 章节图片分页 API
修改 `ChapterController`，新增分页图片接口：

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/api/chapter/{chapterId}/images/paged?page=0&size=100` | 分页获取章节图片 |

返回 VO 增加 `totalPages`、`currentPage`、`hasNext` 字段。

### 4.2 阅读进度 API

| 方法 | 路径 | 说明 |
|---|---|---|
| GET  | `/api/reading-progress/{mangaId}` | 获取某漫画的阅读进度 |
| POST | `/api/reading-progress` | 保存/更新阅读进度（body: mangaId, chapterId, imageIndex, pageIndex） |

---

## 五、前端 - 账号体系

### 5.1 新增文件

| 文件 | 说明 |
|---|---|
| `src/api/auth.ts` | 认证相关 API |
| `src/stores/user.ts` | Pinia 用户状态（token、用户信息、登录状态） |
| `src/views/LoginView.vue` | 登录页 |
| `src/views/RegisterView.vue` | 注册页 |
| `src/views/ProfileView.vue` | 个人信息页 |
| `src/components/UserAvatar.vue` | 头部用户头像组件 |

### 5.2 修改文件
- `src/router/index.ts`：新增 `/login`、`/register`、`/profile` 路由；添加路由守卫（未登录跳转登录页）
- `src/api/request.ts`：请求拦截器自动附加 `Authorization: Bearer <token>`；响应拦截器处理 401 跳转登录
- `src/App.vue`：头部右侧增加用户头像/登录入口

---

## 六、前端 - 书架文件夹管理

### 6.1 新增文件

| 文件 | 说明 |
|---|---|
| `src/api/shelf.ts` | 书架 API（文件夹 CRUD、漫画归类） |
| `src/stores/shelf.ts` | Pinia 书架状态 |
| `src/components/FolderSidebar.vue` | 文件夹侧边栏组件 |
| `src/components/FolderDialog.vue` | 新建/重命名文件夹弹窗 |

### 6.2 修改文件
- `src/views/ShelfView.vue`：
  - 左侧增加 `FolderSidebar` 组件，展示文件夹列表（全部/未分类/各自定义文件夹）
  - 点击文件夹筛选右侧漫画网格
  - 工具栏增加「新建文件夹」按钮
  - 漫画卡片增加右键菜单（移动到文件夹/从书架移除）

---

## 七、前端 - 阅读器增强

### 7.1 侧边栏章节列表
- 修改 `src/components/ChapterList.vue`：从 `el-drawer` 改为固定侧边栏 `div`，支持折叠/展开
- 修改 `src/views/ReaderView.vue`：布局改为 `flex` 三栏（侧边栏 + 主内容 + 可选右侧），侧边栏宽度 260px，折叠时 0px

### 7.2 章节图片分页（每页 100 张）
- 修改 `src/api/chapter.ts`：新增 `getChapterImagesPaged(chapterId, page, size)` 函数
- 修改 `src/views/ReaderView.vue`：
  - 维护 `currentPage`（图片页码）和 `images` 数组
  - 初始加载第 0 页（100 张图）
  - 监听滚动到底部时，若有下一页则加载追加到 `images` 数组
  - ComicScroller 组件无需大改，追加图片时保留已有 DOM

### 7.3 阅读进度保存
- 新增 `src/api/progress.ts`：`getProgress(mangaId)`、`saveProgress(data)`
- 修改 `src/views/ReaderView.vue`：
  - 进入阅读器时调用 `getProgress` 恢复到上次阅读位置（章节 + 页码 + 图片索引）
  - 使用 `setInterval` 每 30 秒（配置可调）调用 `saveProgress` 保存当前进度
  - 切换章节/离开页面时也触发保存

---

## 八、实施顺序

按依赖关系分阶段执行：

**阶段 1 - 基础设施（账号体系后端）**
1. 数据库：执行 `table_v2.sql` 创建新表
2. pom.xml 添加 Spring Security + JJWT 依赖
3. 后端：User 实体 -> Mapper -> Service -> AuthController/UserController
4. 后端：SecurityConfig + JwtUtils + JwtAuthenticationFilter

**阶段 2 - 账号体系前端**
5. 前端：auth API + user store
6. 前端：LoginView / RegisterView / ProfileView
7. 前端：路由守卫 + 请求拦截器 + App.vue 用户入口

**阶段 3 - 书架管理**
8. 后端：ShelfFolder/ShelfManga 实体 -> Mapper -> Service -> ShelfController
9. 前端：shelf API + shelf store + FolderSidebar + ShelfView 改造

**阶段 4 - 阅读器增强**
10. 后端：分页图片 API + 阅读进度 API
11. 前端：ChapterList 侧边栏改造 + 图片分页加载 + 阅读进度保存
