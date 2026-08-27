package com.mangareader.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaChapterPageRecord </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/27 10:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Data
public class MangaChapterPageRecord {
    private Long id;
    private Long chapterId;
    private Integer pageNum;
    private String pageUrl;
    private Integer downloadStatus; // 0=待下载，1=下载成功，2=下载失败
    private Integer retryCount;
    private String lastFailReason;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
}

