package com.mangareader.model.vo;

import lombok.Data;

/**
 * 自动播放配置视图对象
 *
 * @author marks
 * @version v1.0
 */
@Data
public class AutoplayVO {

    private double defaultScrollDistance;
    private int defaultScrollInterval;
    private double scrollDistanceStep;
    private double minScrollDistance;
    private double maxScrollDistance;
}
