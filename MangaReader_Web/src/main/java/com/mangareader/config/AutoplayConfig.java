package com.mangareader.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: AutoplayConfig </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/19 16:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@ConfigurationProperties(prefix = "manga.autoplay")
@Data
public class AutoplayConfig {

    private double defaultScrollDistance;  // 默认滚动距离(像素)
    private int defaultScrollInterval;  // 默认滚动间隔(毫秒)
    private double scrollDistanceStep;  // 加速/减速时滚动距离的调整步长(像素)
    private double minScrollDistance;  // 最小滚动距离(像素)
    private double maxScrollDistance;  // 最大滚动距离(像素)
}
