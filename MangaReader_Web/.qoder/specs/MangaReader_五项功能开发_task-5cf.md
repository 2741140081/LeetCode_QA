# MangaReader 五项功能开发计划

---

## Task 1: 解决漫画阅读图片尺寸不正确问题

### 现状分析
- DB 表 `manga_image` 已有 `image_width`/`image_height` 字段，实体 `MangaImage` 已有对应属性
- `ChapterImageVO` 已有 `width`/`height`，`ChapterController.toImageVO()` 已做映射
- 前端 `ComicScroller.vue` 的 `getPlaceholderHeight()` 已根据 `img.width`/`img.height` 计算占位高度
- **核心问题**: `image_width`/`image_height` 从未被填充，始终为 null，导致前端回退到 600px 固定高度

### 实施方案

#### 1.1 后端: 图片下载完成后读取宽高并存储

修改 `MangaImageDownloadServiceImpl.downloadImage()` 方法，在 `markAsSuccess` 之后，使用 `javax.imageio.ImageIO.read()` 读取本地图片文件获取宽高:

```java
// 下载完成后，读取图片宽高
try {
    File imageFile = targetPath.toFile();
    BufferedImage bufferedImage = ImageIO.read(imageFile);
    if (bufferedImage != null) {
        task.setImageWidth(bufferedImage.getWidth());
        task.setImageHeight(bufferedImage.getHeight());
        mapper.updateImageDimensions(imageId, bufferedImage.getWidth(), bufferedImage.getHeight());
    }
} catch (Exception e) {
    log.warn("[Image-{}] 读取图片宽高失败: {}", imageId, e.getMessage());
}
```

涉及文件:
- `src/main/java/com/mangareader/service/impl/MangaImageDownloadServiceImpl.java` - 下载成功后读取宽高
- `src/main/java/com/mangareader/mapper/MangaImageMapper.java` - 新增 `updateImageDimensions` 方法
- `src/main/resources/mapper/MangaImageMapper.xml` - 新增对应 SQL

#### 1.2 后端: 图片 API 懒存储兜底

在 `ChapterController.toImageVO()` 中，当 `image.getImageWidth()` 为 null 但图片文件已存在时，从本地文件读取宽高并异步回写 DB:

修改 `MangaImageServiceImpl`，新增方法 `fillImageDimensions(MangaImage image)`:
- 检查 `imageWidth`/`imageHeight` 是否为 null
- 若为 null，根据 `imageUrl` + `imageName` 拼接本地路径
- 使用 `ImageIO.read()` 读取宽高
- 调用 mapper 回写 DB
- 设置到 entity 对象后返回

涉及文件:
- `src/main/java/com/mangareader/service/MangaImageService.java` - 新增接口方法
- `src/main/java/com/mangareader/service/impl/MangaImageServiceImpl.java` - 实现懒填充
- `src/main/java/com/mangareader/controller/ChapterController.java` - 在 `toImageVO()` 中调用填充

#### 1.3 前端: 图片自适应渲染

当前 `ComicScroller.vue` 的 `.comic-image` 样式为 `max-width: 100%`，已能实现等比缩放。但需确保:
- 当 `img.width` 和 `img.height` 有值时，`image-slot` 的高度动态设置为 `(height/width) * containerWidth`
- 当图片实际加载后，根据真实尺寸更新 slot 高度（防止 DB 尺寸与实际不符）

修改 `ComicScroller.vue`:
- `getPlaceholderHeight()` 已有正确逻辑，保持不变
- 在 `onImageLoad()` 中，读取 `img.naturalWidth` 和 `img.naturalHeight`，更新 slot 容器的实际高度为 `(naturalHeight/naturalWidth) * containerWidth`
- 确保 `.comic-image` 使用 `width: 100%; height: auto;` 实现等比缩放

涉及文件:
- `manga-web/src/components/ComicScroller.vue` - 图片加载后动态更新高度

---

## Task 2: 漫画删除功能

### 实施方案

#### 2.1 后端: 新增漫画删除 API

在 `MangaController` 中新增 `DELETE /api/manga/{mangaId}` 接口:

```java
@DeleteMapping("/{mangaId}")
public Result<Void> delete(@PathVariable Long mangaId) { ... }
```

在 `MangaService` 中新增 `deleteManga(Long mangaId)` 方法，逻辑:
1. 查询漫画信息获取 `dirId`（本地目录路径）
2. 删除 DB 数据（利用外键 CASCADE 自动删除关联的 chapter、manga_image、reading_progress 等）
3. 删除 `shelf_manga` 中的关联记录
4. 删除本地文件目录（`dirId` 对应的文件夹及其所有内容）
5. 通过 SSE 通知前端

涉及文件:
- `src/main/java/com/mangareader/controller/MangaController.java` - 新增删除接口
- `src/main/java/com/mangareader/service/MangaService.java` - 新增接口方法
- `src/main/java/com/mangareader/service/impl/MangaServiceImpl.java` - 实现删除逻辑
- `src/main/java/com/mangareader/mapper/MangaMapper.java` - 新增 `deleteMangaById` 方法
- `src/main/resources/mapper/MangaMapper.xml` - 新增 DELETE SQL
- `src/main/java/com/mangareader/mapper/ShelfMangaMapper.java` - 确保有按 mangaId 删除方法
- `src/main/resources/mapper/ShelfMangaMapper.xml` - 新增对应 SQL

#### 2.2 后端: 批量删除支持

新增 `DELETE /api/manga/batch` 接口，接收 `List<Long> mangaIds`:
- 遍历执行删除逻辑
- 使用 `@Transactional` 保证事务一致性
- 返回删除成功/失败数量

涉及文件:
- `src/main/java/com/mangareader/controller/MangaController.java` - 新增批量删除接口
- `src/main/java/com/mangareader/model/dto/MangaBatchDeleteRequest.java` - 新建 DTO

#### 2.3 前端: 书架管理模式

修改 `ShelfView.vue`:
- 工具栏新增"管理"按钮，点击进入管理模式
- 管理模式下，每个 `MangaCard` 左上角显示勾选框
- 底部固定栏显示已选数量 + "删除"按钮
- 点击删除时弹出确认框，显示将删除的漫画列表
- 确认后调用批量删除 API，刷新书架

新增 `manga-web/src/api/manga.ts` 中:
```ts
export function deleteManga(mangaId: number) { ... }
export function batchDeleteMangas(mangaIds: number[]) { ... }
```

涉及文件:
- `manga-web/src/views/ShelfView.vue` - 管理模式的 UI 和逻辑
- `manga-web/src/api/manga.ts` - 新增删除 API 调用
- `manga-web/src/components/MangaCard.vue` - 支持管理模式的勾选状态

---

## Task 3: 图片下载扫描任务中更新 manga/chapter 状态

### 现状分析
- `MangaDownloadScheduledTask` 通过 `processedChapters == totalChapters` 判断漫画完成
- 但缺少基于图片实际下载状态的判断逻辑
- chapter 表没有状态字段

### 实施方案

#### 3.1 数据库: chapter 表增加状态字段

```sql
ALTER TABLE chapter ADD COLUMN chapter_status TINYINT NOT NULL DEFAULT 0 
  COMMENT '状态: 0-待处理, 1-处理中, 2-已完成, 3-处理失败';
```

涉及文件:
- `src/main/resources/sql/` - 新增增量 SQL 脚本
- `src/main/java/com/mangareader/model/entity/Chapter.java` - 新增 `chapterStatus` 字段 (ProcessStatus 枚举)
- `src/main/resources/mapper/ChapterMapper.xml` - resultMap 和 SQL 增加字段

#### 3.2 后端: 新增状态汇总定时任务

在 `MangaImageDownloadScheduledTask` 中新增方法 `syncDownloadStatus()`，使用 `@Scheduled(fixedDelay = 60000)` 每分钟执行:

```
1. 查询所有 manga_status = 0(待下载) 或 1(处理中) 的漫画
2. 遍历每个漫画 i:
   a. 获取 i 的所有 chapter
   b. 遍历每个 chapter j:
      - 查询 j 下所有 manga_image 的 download_status
      - 如果所有图片 status = 2(完成)，更新 chapter j 的 status = 2
      - 如果存在 status = 0 或 1，更新 chapter j 的 status = 1
      - 如果所有图片 status = 3(失败)，更新 chapter j 的 status = 3
   c. 如果所有 chapter 的 status = 2，更新漫画 i 的 manga_status = 2
   d. 通过 SSE 发布状态变更事件
```

涉及文件:
- `src/main/java/com/mangareader/task/MangaImageDownloadScheduledTask.java` - 新增状态汇总方法
- `src/main/java/com/mangareader/mapper/ChapterMapper.java` - 新增 `updateChapterStatus`、`countImagesByStatus` 方法
- `src/main/resources/mapper/ChapterMapper.xml` - 新增 SQL
- `src/main/java/com/mangareader/mapper/MangaImageMapper.java` - 新增按章节统计各状态数量的方法
- `src/main/resources/mapper/MangaImageMapper.xml` - 新增 SQL

---

## Task 4: 创建前后端启动脚本

### 实施方案

在项目根目录创建两个 `.bat` 文件:

#### 4.1 `start-backend.bat`
```bat
@echo off
cd /d %~dp0
echo Building backend...
call mvn clean package -DskipTests
echo Starting backend server...
java -jar target\MangaReader_Web-1.0-SNAPSHOT.jar
pause
```

#### 4.2 `start-frontend.bat`
```bat
@echo off
cd /d %~dp0\manga-web
echo Installing dependencies...
call npm install
echo Starting frontend dev server...
call npm run dev
pause
```

涉及文件:
- `start-backend.bat` - 新建
- `start-frontend.bat` - 新建

---

## Task 5: 邮箱找回密码功能

### 现状分析
- 后端已有 `sendResetCode()` 和 `resetPassword()` 基础实现（存 Redis + 验证码模式）
- 前端 `auth.ts` 已有 `sendResetCode()` 和 `resetPassword()` API 封装
- 缺少: 实际邮件发送、邮件发送表、定时任务发送、频率限制、前端找回密码页面

### 实施方案

#### 5.1 数据库: 新增邮件发送表

```sql
CREATE TABLE mail_send_record (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    to_email      VARCHAR(100) NOT NULL COMMENT '收件人邮箱',
    subject       VARCHAR(255) NOT NULL COMMENT '邮件主题',
    content       TEXT         NOT NULL COMMENT '邮件内容(HTML)',
    mail_type     TINYINT      NOT NULL DEFAULT 1 COMMENT '类型: 1-密码重置',
    send_status   TINYINT      NOT NULL DEFAULT 0 COMMENT '0-待发送, 1-发送中, 2-已发送, 3-发送失败',
    retry_count   INT          NOT NULL DEFAULT 0 COMMENT '重试次数',
    error_msg     VARCHAR(512) DEFAULT NULL COMMENT '错误信息',
    created_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_send_status (send_status),
    INDEX idx_to_email (to_email)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='邮件发送记录表';
```

涉及文件:
- `src/main/resources/sql/` - 增量 SQL
- `src/main/java/com/mangareader/model/entity/MailSendRecord.java` - 新建实体
- `src/main/java/com/mangareader/mapper/MailSendRecordMapper.java` - 新建 Mapper
- `src/main/resources/mapper/MailSendRecordMapper.xml` - 新建 XML

#### 5.2 后端: 邮件发送服务

新增 `MailService`:
- `sendMail(String to, String subject, String content)` - 使用 `JavaMailSender` 发送
- `createResetMail(String email, String code)` - 创建密码重置邮件记录到 `mail_send_record`

涉及文件:
- `src/main/java/com/mangareader/service/MailService.java` - 新建接口
- `src/main/java/com/mangareader/service/impl/MailServiceImpl.java` - 新建实现

#### 5.3 后端: 定时任务扫描发送邮件

新增 `MailSendScheduledTask`:
- `@Scheduled(fixedDelay = 30000)` 每 30 秒扫描
- 查询 `send_status = 0` 的记录，批量发送
- 发送成功更新为 2，失败更新 retry_count，超过 3 次标记为 3

涉及文件:
- `src/main/java/com/mangareader/task/MailSendScheduledTask.java` - 新建

#### 5.4 后端: 改造现有 sendResetCode

修改 `UserServiceImpl.sendResetCode()`:
1. 增加频率限制: Redis 存储 `manga:reset_limit:{email}` 限制每分钟 1 次、每天 5 次
2. 生成验证码后，不再直接 log，而是调用 `MailService.createResetMail()` 写入邮件发送表
3. 修改返回提示为"如果该邮箱已注册，我们将发送重置链接"（防枚举）

涉及文件:
- `src/main/java/com/mangareader/service/impl/UserServiceImpl.java` - 改造 sendResetCode

#### 5.5 后端: 依赖和配置

`pom.xml` 新增:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

`application.yml` 新增邮件配置:
```yaml
spring:
  mail:
    host: smtp.qq.com
    port: 587
    username: your-email@qq.com
    password: your-auth-code
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

涉及文件:
- `pom.xml` - 新增 mail 依赖
- `application.yml` - 新增邮件配置

#### 5.6 前端: 找回密码页面

新建 `manga-web/src/views/ForgotPasswordView.vue`:
- 步骤一: 输入用户名 + 邮箱，发送验证码
- 步骤二: 输入验证码 + 新密码 + 确认密码，提交重置
- 重置成功后跳转到登录页

修改 `manga-web/src/views/LoginView.vue`:
- 登录表单下方添加"忘记密码？"链接，跳转到 `ForgotPasswordView`

修改 `manga-web/src/router/index.ts`:
- 新增 `/forgot-password` 路由

涉及文件:
- `manga-web/src/views/ForgotPasswordView.vue` - 新建
- `manga-web/src/views/LoginView.vue` - 添加找回密码链接
- `manga-web/src/router/index.ts` - 新增路由

---

## 实施顺序建议

1. **Task 4** (启动脚本) - 最简单，10 分钟完成
2. **Task 1** (图片尺寸) - 核心体验问题，优先解决
3. **Task 3** (状态更新) - 与 Task 1 关联（图片下载完成后更新状态链）
4. **Task 2** (漫画删除) - 独立功能
5. **Task 5** (邮箱找回) - 最复杂，涉及新建表、新依赖、新页面

## 涉及的新建文件汇总

| 文件 | 所属 Task |
|---|---|
| `start-backend.bat` | Task 4 |
| `start-frontend.bat` | Task 4 |
| `MailSendRecord.java` (entity) | Task 5 |
| `MailSendRecordMapper.java` | Task 5 |
| `MailSendRecordMapper.xml` | Task 5 |
| `MailService.java` | Task 5 |
| `MailServiceImpl.java` | Task 5 |
| `MailSendScheduledTask.java` | Task 5 |
| `ForgotPasswordView.vue` | Task 5 |
| `MangaBatchDeleteRequest.java` (DTO) | Task 2 |
| 增量 SQL 脚本 | Task 3, 5 |

## 涉及修改的文件汇总

| 文件 | 所属 Task |
|---|---|
| `MangaImageDownloadServiceImpl.java` | Task 1 |
| `MangaImageMapper.java` + XML | Task 1, 3 |
| `MangaImageServiceImpl.java` | Task 1 |
| `ChapterController.java` | Task 1 |
| `ComicScroller.vue` | Task 1 |
| `MangaController.java` | Task 2 |
| `MangaService.java` + Impl | Task 2 |
| `MangaMapper.java` + XML | Task 2 |
| `ShelfMangaMapper.java` + XML | Task 2 |
| `ShelfView.vue` | Task 2 |
| `MangaCard.vue` | Task 2 |
| `manga.ts` (api) | Task 2 |
| `Chapter.java` | Task 3 |
| `ChapterMapper.java` + XML | Task 3 |
| `MangaImageDownloadScheduledTask.java` | Task 3 |
| `pom.xml` | Task 5 |
| `application.yml` | Task 5 |
| `UserServiceImpl.java` | Task 5 |
| `LoginView.vue` | Task 5 |
| `router/index.ts` | Task 5 |
