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