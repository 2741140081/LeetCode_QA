package com.marks.tools.kkplatform.common;

import org.opencv.core.Scalar;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: KingOfBeastsConstants </p>
 * <p>描述: [常量类] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/10 10:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public final class KingOfBeastsConstants {
    public static final String PREPARE_ROOM_TITLE = "KK官方对战平台";
    public static final String GAME_TITLE = "Warcraft III";
    public static final String MODIFIER_TITLE = "魔兽3内存修改器";

    public static final String TEMPLATE_DIR = "D:/images/automation/templates";
    public static final String OUTPUT_DIR = "D:/images/automation/results";
    public static final double MATCH_THRESHOLD = 0.75;

    public static final double BEST_SCALE = 0.6667;

    public static final int RECT_THICKNESS = 2;
    public static final Scalar RED_COLOR = new Scalar(0, 0, 255);
    public static final Scalar GREEN_COLOR = new Scalar(0, 255, 0);
    public static final double MIN_SCALE = 0.3;
    public static final double MAX_SCALE = 2.0;
}
