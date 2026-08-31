package com.mangareader.model.vo;

import lombok.Data;

/**
 * 章节图片视图对象
 *
 * @author marks
 * @version v1.0
 */
@Data
public class ChapterImageVO {

    private Long imageId;
    private Integer sortOrder;
    /** 浏览器可访问的图片 URL（/images/...） */
    private String url;
    private Integer width;
    private Integer height;
}
