package com.mangareader.model.entity;

import com.mangareader.enums.ProcessStatus;
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
    /**
     * 下载状态: 0:待处理 1:处理中 2:处理完成 3:处理失败
     */
    private ProcessStatus downloadStatus;
    private Integer retryCount;
    private String lastFailReason;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
}

