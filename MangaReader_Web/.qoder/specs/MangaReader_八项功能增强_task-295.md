
# MangaReader 八项功能增强计划

---

## 1. 消除魔法数字 — 统一常量/枚举管理

### 后端

**新建 `constant/ResultCode.java`** — HTTP 业务状态码常量：
```java
public class ResultCode {
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int SERVER_ERROR = 500;
}
```

**新建 `enums/UserStatus.java`** — 用户状态枚举：
```java
public enum UserStatus {
    DISABLED(0, "禁用"),
    ACTIVE(1, "正常");
    // code + desc + fromCode()
}
```

**需替换的文件：**
- `Result.java`：`200` → `ResultCode.SUCCESS`，`500` → `ResultCode.SERVER_ERROR`
- `BusinessException.java`：默认 code `500` → `ResultCode.SERVER_ERROR`
- `UserServiceImpl.java`：所有 `400/401/403/404` → 对应常量；`user.setStatus(1)` → `UserStatus.ACTIVE.getCode()`；`user.getStatus() != 1` → `!= UserStatus.ACTIVE.getCode()`
- `UserController.java`、`ShelfController.java`、`AuthController.java`、`ReadingProgressController.java`：`getCurrentUserId()` 方法中的 `401`、`substring(7)` → 使用常量和提取为公共方法
- `GlobalExceptionHandler.java`：`400`、`500` → 常量

**提取公共 Token 解析方法：** 将 `AuthController`、`UserController`、`ShelfController`、`ReadingProgressController` 中重复的 `resolveToken()` / `getCurrentUserId()` 提取到工具类 `security/TokenResolver.java`，消除 `substring(7)` 魔法值。

**前端常量：**
- `ReaderView.vue`：`PAGE_SIZE = 20` 移至 `api/config.ts` 或独立常量文件；`30000`（进度保存间隔）提取为常量
- `ComicScroller.vue`：`SCROLL_END_THROTTLE = 500`、`100`（底部距离阈值）提取为常量
- `ShelfView.vue`：`-1`（未分类）、`2`（漫画完成状态码）提取为常量

---

## 2. AES 对称加密模块

### 后端

**新建 `util/AesUtils.java`：**
- 使用 AES-128-CBC 算法
- 密钥从 `application.yml` 的 `manga.encrypt.secret-key` 读取（16 字节）
- 提供 `encrypt(String)` / `decrypt(String)` 静态方法
- 加密结果使用 Base64 编码

**新建 `config/EncryptProperties.java`：**
```java
@ConfigurationProperties(prefix = "manga.encrypt")
public class EncryptProperties {
    private String secretKey;
    private List<String> fields; // 需要加密的字段名列表
}
```

**新建 `config/ResponseEncryptAdvice.java`（ResponseBodyAdvice）：**
- 拦截 `Result<T>` 类型的响应
- 对 VO 中标注了自定义注解 `@EncryptedField` 的字段进行 AES 加密
- 仅加密 `EncryptProperties.fields` 中列出的字段

**新建注解 `annotation/EncryptedField.java`：** 标记需要加密的字段

**修改 VO 类：**
- `UserVO.java`：对 `email`、`nickname` 字段添加 `@EncryptedField`

**`application.yml` 新增配置：**
```yaml
manga:
  encrypt:
    secret-key: MangaReader2026Aes
    fields: email,nickname
```

### 前端

**新建 `manga-web/src/utils/crypto.ts`：**
- 实现 AES-128-CBC 解密函数（使用 Web Crypto API 或 crypto-js 库）
- 需在 `package.json` 添加 `crypto-js` 依赖

**修改 `manga-web/src/api/request.ts`：**
- 响应拦截器中，对包含加密字段的响应自动解密
- 解密后再进行 `code !== 200` 判断

---

## 3. 图片缩放功能

### 前端

**修改 `ComicScroller.vue`：**
- 新增 `zoomScale` prop（默认 1.0）
- 对 `.comic-image` 应用 CSS `transform: scale(zoomScale)` + `transform-origin: top center`
- 缩放后调整 `image-slot` 高度占位，避免滚动跳动

**修改 `ReaderView.vue`：**
- 新增 `zoomScale` ref，从 `sessionStorage` 读取初始值
- 工具栏添加缩放控制：`-` 按钮、缩放百分比显示、`+` 按钮
- 缩放范围：0.5 ~ 2.0，步进 0.1
- `zoomScale` 变化时同步写入 `sessionStorage.setItem('manga_zoom_scale', value)`
- 传递 `zoomScale` 给 `ComicScroller` 作为 prop

**缩放比例存储：**
- Key: `manga_zoom_scale`
- 使用 `sessionStorage`，浏览器当前会话有效
- 进入阅读器时读取，修改时实时更新

---

## 4. Validation 校验增强

### 后端

**补全 DTO 校验注解：**
- `ProfileUpdateRequest.java`：
  - `nickname`: `@Size(max = 50, message = "昵称长度不能超过50")`
  - `email`: `@Email(message = "邮箱格式不正确")`、`@Size(max = 100)`
  - `avatarUrl`: `@Size(max = 500)`
- `MangaMoveRequest.java`：
  - `mangaId`: `@NotNull(message = "漫画ID不能为空")`
- `PasswordChangeRequest.java`：
  - `newPassword`: `@Size(min = 6, max = 100, message = "新密码长度需在6-100之间")`
- `PasswordResetRequest.java`：
  - `email`: `@Email(message = "邮箱格式不正确")`
  - `newPassword`: `@Size(min = 6, max = 100)`
  - `verifyCode`: `@Size(min = 6, max = 6, message = "验证码为6位数字")`
- `FolderCreateRequest.java`：
  - `folderName`: `@Size(max = 50, message = "文件夹名称不能超过50个字符")`
- `MangaAddRequest.java`：
  - `mangaUrl`: `@URL(message = "网址格式不正确")` 或自定义 URL 校验

**补全 `@Valid` 注解：**
- `UserController.updateProfile()`：`@RequestBody` 前加 `@Valid`
- `ShelfController.renameFolder()`：将 `Map<String, String>` 替换为 DTO 并使用 `@Valid`

---

## 5. 注册时随机生成 Nickname

### 后端

**修改 `UserServiceImpl.register()`：**
- 当 `nickname` 为 null 时，生成随机昵称：`"nick_" + 18位随机数字`
- 使用 `ThreadLocalRandom` 生成 18 位数字字符串

**新建 `util/NicknameGenerator.java`：**
```java
public class NicknameGenerator {
    public static String generate() {
        return "nick_" + String.format("%018d", ThreadLocalRandom.current().nextLong(1_000_000_000_000_000_000L));
    }
}
```

**昵称唯一性校验：**
- `UserMapper.java` 新增 `User findByNickname(String nickname)` 方法
- `UserMapper.xml` 新增对应 SQL
- `UserServiceImpl.updateProfile()` 中：修改昵称时检查是否与其他用户重复，重复则抛出 `BusinessException`
- `UserServiceImpl.register()` 中：生成昵称后检查唯一性（概率极低但兜底）

---

## 6. 退出登录跳转到登录页

### 前端

**修改 `UserAvatar.vue` 的 `handleCommand`：**
- 当前逻辑 `userStore.logout()` → `router.push('/login')` 看起来正确
- 问题：`logout()` 是 async 函数，内部 `await logoutApi()` 如果收到 401 响应，`request.ts` 拦截器会先执行 `router.push('/login')`，但此时 token 尚未清除，路由守卫可能阻止跳转
- 修复：使用 `router.replace('/login')` 替代 `router.push('/login')`，并在调用 logout 前先清除本地 token 再发 API 请求

**修改 `stores/user.ts` 的 `logout()`：**
```typescript
async function logout() {
    // 先清除本地状态，避免路由守卫拦截
    const currentToken = token.value
    token.value = ''
    user.value = null
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
    try {
        await logoutApi(currentToken) // 传递 token 手动调用
    } catch {
        // 静默失败
    }
}
```

**修改 `api/auth.ts` 的 `logout()`：** 支持传入 token 参数，避免依赖 localStorage

---

## 7. 自动播放修复 — 到底后加载下一页而非切换章节

### 前端

**修改 `ReaderView.vue` 的 `onScrollEnd()`：**
```typescript
async function onScrollEnd() {
    if (isLoadingNextPage) return

    if (currentImagePage.value < totalPages.value - 1) {
        // 还有下一页：加载下一页图片（无论是否自动播放）
        isLoadingNextPage = true
        try {
            await loadNextPage()
        } finally {
            isLoadingNextPage = false
        }
    } else if (isPlaying.value) {
        // 自动播放且已到本章最后一页：切换下一章
        loadNextChapter()
    }
}
```

核心逻辑变更：
- 移除原来 `if (isPlaying) { loadNextChapter(); return }` 的优先判断
- 改为：先检查是否有下一页可加载 → 有则加载下一页（累计追加图片）
- 只有当已到本章最后一页且正在自动播放时，才切换下一章

**修改 `loadImagesPage()`：** 自动播放模式下需要改为追加模式而非替换模式：
- 当 `isPlaying.value === true` 且 `page > 0` 时，使用 `[...images.value, ...data.images]` 追加
- 非自动播放时保持当前替换行为（手动翻页）

---

## 8. 章节侧边栏隐藏功能 — 双模式

### 前端

**模式 A — 进入阅读时自动收起：**
- 修改 `ReaderView.vue`：`sidebarVisible` 初始值改为 `false`
- 用户通过工具栏「目录」按钮手动切换

**模式 B — 无操作自动隐藏 + 鼠标悬停唤出：**
- 修改 `ChapterList.vue`：
  - 新增 `autoHide` prop（布尔值，由父组件控制启用哪种模式）
  - 侧边栏展开时启动空闲计时器（如 5 秒无鼠标移动/点击则自动收起）
  - 收起状态下，鼠标悬停到左侧边缘区域（24px 宽的 toggle 区域）时，侧边栏滑出展开
  - 鼠标离开侧边栏区域后重新启动空闲计时器

**新增 `useAutoHide` composable：**
- 管理空闲检测定时器逻辑
- 参数：`delay`（空闲时间，默认 5000ms）、`onHide` 回调

**修改 `ReaderView.vue`：**
- 工具栏添加模式切换按钮（自动收起 / 悬停唤出 / 常驻），循环切换
- 将当前模式存入 `sessionStorage`，保持会话内一致

---

## 实施顺序

建议按依赖关系分组实施：

| 批次 | 任务 | 理由 |
|------|------|------|
| 第 1 批 | 任务 1（常量提取）、任务 4（Validation）、任务 5（随机昵称） | 纯后端改动，无前端依赖，可并行 |
| 第 2 批 | 任务 2（AES 加密） | 依赖任务 1 的常量；涉及前后端联调 |
| 第 3 批 | 任务 6（退出登录修复）、任务 7（自动播放修复） | 前端 bug 修复，独立模块 |
| 第 4 批 | 任务 3（图片缩放）、任务 8（侧边栏隐藏） | 前端阅读器增强，可并行 |

## 验证计划

- 每批次完成后执行 `npx vite build` 验证前端编译
- 后端使用 `mvn compile` 验证 Java 编译
- 任务 2 需验证加密/解密的端到端正确性
- 任务 7 需手动测试自动播放到底后的行为
- 任务 8 需手动测试两种隐藏模式的交互体验
