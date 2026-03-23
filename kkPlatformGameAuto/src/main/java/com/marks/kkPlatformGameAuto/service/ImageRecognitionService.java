package com.marks.kkPlatformGameAuto.service;

import java.awt.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ImageRecognitionService </p>
 * <p>描述: 图片识别接口 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface ImageRecognitionService {
    // 识别图片, 返回图片中心点坐标
    Point findImageCenter(String imagePath, int timeout, int delayTime);
    Point findImageCenter(String imagePath, int timeout, int delayTime, boolean fromCache);

    // 识别图片并执行点击操作, 返回执行结果
    boolean findAndClickImage(String imagePath, int timeout);

    // 查找图片 (使用配置文件默认参数)
    boolean findImage(String imagePath);

    // 查找图片 (指定超时时间)
    boolean findImage(String imagePath, int timeout);

    // 查找图片 (指定超时时间和间隔延迟)
    boolean findImage(String imagePath, int timeout, int delayTime);
}
