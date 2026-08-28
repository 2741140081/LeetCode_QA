数据库更改文件:
08-24:
1. chapter 表添加字段, 名称章节网址, String 类型, private String chapterUrl; 待修改数据库
2. manga 表更改字段名称, manga_description 改成 manga_url, 漫画下载地址, private String mangaUrl; 待修改数据库


08-26:
1. manga_image 添加断点续传相关字段
   - fileSize: 文件大小(字节)
   - downloadedSize: 已下载大小(字节,用于断点续传)
   - retryCount: 重试次数 
   - errorMsg: 错误信息 
   - lastTriggerTime: 最后定时任务触发时间


08-27:
1. manga 表添加3个字段用于下载中心设置为缓存下载漫画, 增加心跳检测异常下载
   - lastHeartBeat: 最后心跳时间
   - totalChapters: 总章节数目
   - processedChapters: 已处理的章节数
2. 昨天新增 MangaChapterPageRecord 表


08-28:
1. 更改 manga 表 author_name 改成 url_id, 用于标识唯一网址
   - 重命名列 (以 MySQL 为例，其他数据库语法可能略有不同)
   ALTER TABLE manga CHANGE COLUMN author_name url_id VARCHAR(255) DEFAULT NULL COMMENT '网址唯一标识';

   - 根据已有 manga_url 初始化 url_id (截取最后一个 '/' 和 '.html' 之间的字符)
   UPDATE manga
   SET url_id = SUBSTRING_INDEX(SUBSTRING_INDEX(manga_url, '/', -1), '.html', 1)
   WHERE manga_url LIKE '%/%.html%';

   - 为 url_id 添加唯一索引，用于判断漫画是否已存在（重复网址识别）
   ALTER TABLE manga ADD UNIQUE INDEX uk_url_id (url_id);
2. 