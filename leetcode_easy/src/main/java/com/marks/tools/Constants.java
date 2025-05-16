package com.marks.tools;

/**
 * <p>项目名称: 常量类  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/23 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public final class Constants {

    // 设置Robot 键入延迟毫秒数
    public static final int DELAY_MS = 100;

    // 匹配置信度, Match configuration reliability
    public static final int MATCH_CONF_RELIABILITY = 80;

    // 屏幕缩放比例, 0.67 = 1 / (150%), 150%是显示器的缩放比例
    public static final double RANK = 0.67;


    public static final int DURATION_MS = 1000;

    public static final int SYSTEM_EXIT_STATUE_CODE = 0;

    public static final String EXE_PROGRAM_NAME = ".exe";

    private Constants() {
        // 防止实例化
    }
}
