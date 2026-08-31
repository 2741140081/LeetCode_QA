package com.mangareader.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 章节视图对象（含上一章/下一章导航）
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ChapterVO {

    private Long chapterId;
    private Long mangaId;
    private Integer chapterNum;
    private String chapterUrl;
    private String title;
    private Integer imageCount;
    private LocalDateTime createdAt;

    /** 上一章 ID，无则为 null */
    private Long prevChapterId;
    /** 下一章 ID，无则为 null */
    private Long nextChapterId;
}
