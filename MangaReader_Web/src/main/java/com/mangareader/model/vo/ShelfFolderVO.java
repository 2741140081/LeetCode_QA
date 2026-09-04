package com.mangareader.model.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 书架文件夹 VO
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ShelfFolderVO {

    private Long folderId;

    private String folderName;

    private Integer sortOrder;

    private Integer mangaCount;

    private LocalDateTime createdAt;
}
