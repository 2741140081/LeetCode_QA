package com.mangareader.model.entity;

import com.mangareader.enums.ProcessStatus;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MangaImage </p>
 * <p>描述: 漫画图片实体类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/13 16:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Data
public class MangaImage {
    /**
     * 主键ID
     */
    private Long imageId;

    /**
     * 所属章节ID
     */
    private Long chapterId;

    /**
     * 图片文件名
     */
    private String imageName;

    /**
     * 图片类型
     */
    private String imageType;

    /**
     * 图片宽度(px)
     */
    private Integer imageWidth;

    /**
     * 图片高度(px)
     */
    private Integer imageHeight;

    /**
     * 图片保存地址
     */
    private String imageUrl;
    /**
     * 下载地址
     */
    private String downloadUrl;

    /**
     * 下载状态: 0:待处理 1:处理中 2:处理完成 3:处理失败
     */
    private ProcessStatus downloadStatus;

    /**
     * 图片在章节内的排序序号
     */
    private Integer sortOrder;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * 已下载大小(字节,用于断点续传)
     */
    private Long downloadedSize;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 最后定时任务触发时间
     */
    private LocalDateTime lastTriggerTime;


    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
