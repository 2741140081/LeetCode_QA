package com.marks.tools.download_cartoon.entity;

import lombok.Data;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageInfo </p>
 * <p>描述: 漫画图片信息实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/26 15:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Data
public class ImageInfo {
    /**
     * 图片ID (img0, img1, img2...)
     */
    private String imageId;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 所属章节ID
     */
    private String chapterId;

    /**
     * 图片序号
     */
    private int imageIndex;

    /**
     * 下载状态：0-未下载，1-下载中，2-已下载
     */
    private int downloadStatus;

    public ImageInfo() {
    }

    public ImageInfo(String imageId, String imageUrl, String chapterId, int imageIndex) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.chapterId = chapterId;
        this.imageIndex = imageIndex;
        this.downloadStatus = 0;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                "imageId='" + imageId + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", imageIndex=" + imageIndex +
                ", downloadStatus=" + downloadStatus +
                '}';
    }
}
