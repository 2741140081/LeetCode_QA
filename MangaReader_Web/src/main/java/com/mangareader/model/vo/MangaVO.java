package com.mangareader.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 书架卡片视图对象
 *
 * @author marks
 * @version v1.0
 */
@Data
public class MangaVO {

    private Long mangaId;
    private String mangaName;
    private String coverUrl;

    /** 状态码 */
    private Integer mangaStatusCode;
    /** 状态描述 */
    private String mangaStatusDesc;

    private Integer totalChapters;
    private Integer processedChapters;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
