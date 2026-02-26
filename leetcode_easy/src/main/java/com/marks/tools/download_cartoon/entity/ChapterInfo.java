package com.marks.tools.download_cartoon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ChapterInfo </p>
 * <p>描述: 漫画章节信息实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 15:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class ChapterInfo {
    /**
     * 章节ID
     */
    private String chapterId;

    /**
     * 章节标题
     */
    private String chapterTitle;

    /**
     * 章节URL
     */
    private String chapterUrl;

    /**
     * 章节序号
     */
    private int chapterIndex;

    /**
     * 状态：0-未处理，1-处理中，2-已完成
     */
    private int status;

    public ChapterInfo() {
    }

    public ChapterInfo(String chapterId, String chapterTitle, String chapterUrl, int chapterIndex) {
        this.chapterId = chapterId;
        this.chapterTitle = chapterTitle;
        this.chapterUrl = chapterUrl;
        this.chapterIndex = chapterIndex;
        this.status = 0;
    }

    @Override
    public String toString() {
        return "ChapterInfo{" +
                "chapterId='" + chapterId + '\'' +
                ", chapterTitle='" + chapterTitle + '\'' +
                ", chapterUrl='" + chapterUrl + '\'' +
                ", chapterIndex=" + chapterIndex +
                ", status=" + status +
                '}';
    }
}
