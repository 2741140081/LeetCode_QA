package com.mangareader.model.vo;

import lombok.Data;

/**
 * 图片下载统计视图对象
 *
 * @author marks
 * @version v1.0
 */
@Data
public class DownloadStatsVO {

    private Integer total;
    private Integer pending;
    private Integer downloading;
    private Integer completed;
    private Integer failed;
    /** 成功率（百分比，如 95.5） */
    private Double successRate;
}
