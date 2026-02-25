package com.marks.tools.download_txt_multi_threading.entity;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ChapterDownloadResult </p>
 * <p>描述: [章节下载结果类] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/25 11:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ChapterDownloadResult {
    public boolean success;
    public ChapterInfo chapter;
    public String content;
    public String errorMessage;

    public ChapterDownloadResult(boolean success, ChapterInfo chapter, String content, String errorMessage) {
        this.success = success;
        this.chapter = chapter;
        this.content = content;
        this.errorMessage = errorMessage;
    }
}
