package com.marks.utils;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: Logger </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/2 16:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class Logger {
    public enum Level {
        DEBUG, INFO, ERROR
    }

    private static Level currentLevel = Level.DEBUG; // 默认级别

    public Logger(Level level) {
        currentLevel = level;
    }

    public static void debug(String message) {
        if (currentLevel.ordinal() <= Level.DEBUG.ordinal()) {
            System.out.println("[DEBUG] " + message);
        }
    }

    public static void info(String message) {  // 改为static
        if (currentLevel.ordinal() <= Level.INFO.ordinal()) {
            System.out.println("[INFO] " + message);
        }
    }

    public static void error(String message) {  // 改为static
        if (currentLevel.ordinal() <= Level.ERROR.ordinal()) {
            System.err.println("[ERROR] " + message);
        }
    }
}
