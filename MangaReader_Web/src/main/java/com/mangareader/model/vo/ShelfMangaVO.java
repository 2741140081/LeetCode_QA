package com.mangareader.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 书架漫画 VO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ShelfMangaVO {

    private Long mangaId;

    private String mangaName;

    private String coverUrl;

    private Integer mangaStatusCode;

    private String mangaStatusDesc;

    private Integer totalChapters;

    private Integer processedChapters;

    private Long folderId;

    private String folderName;

    private LocalDateTime addedAt;
}
