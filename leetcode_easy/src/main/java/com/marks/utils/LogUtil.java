package com.marks.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LogUtil </p>
 * <p>描述: 日志工具类 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/5 15:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LogUtil {
    // 获取Logger实例
    private static final Logger logger = LogManager.getLogger(LogUtil.class);

    // 私有构造方法，防止实例化
    private LogUtil() {}

    /**
     * 记录调试级别日志
     * @param message 日志信息
     */
    public static void debug(String message) {
        logger.debug(message);
    }

    /**
     * 记录调试级别日志（带参数）
     * @param message 日志信息模板
     * @param params 参数
     */
    public static void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    /**
     * 记录信息级别日志
     * @param message 日志信息
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * 记录信息级别日志（带参数）
     * @param message 日志信息模板
     * @param params 参数
     */
    public static void info(String message, Object... params) {
        logger.info(message, params);
    }

    /**
     * 记录警告级别日志
     * @param message 日志信息
     */
    public static void warn(String message) {
        logger.warn(message);
    }

    /**
     * 记录警告级别日志（带参数）
     * @param message 日志信息模板
     * @param params 参数
     */
    public static void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    /**
     * 记录错误级别日志
     * @param message 日志信息
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * 记录错误级别日志（带参数）
     * @param message 日志信息模板
     * @param params 参数
     */
    public static void error(String message, Object... params) {
        logger.error(message, params);
    }

    /**
     * 记录错误级别日志（带异常）
     * @param message 日志信息
     * @param throwable 异常对象
     */
    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * 记录致命级别日志
     * @param message 日志信息
     */
    public static void fatal(String message) {
        logger.fatal(message);
    }

    /**
     * 记录致命级别日志（带异常）
     * @param message 日志信息
     * @param throwable 异常对象
     */
    public static void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }

    /**
     * 获取指定类的Logger实例
     * @param clazz 目标类
     * @return Logger实例
     */
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    /**
     * 获取指定名称的Logger实例
     * @param name Logger名称
     * @return Logger实例
     */
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }
}
