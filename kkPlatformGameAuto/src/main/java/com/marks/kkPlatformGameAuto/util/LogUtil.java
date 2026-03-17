package com.marks.kkPlatformGameAuto.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 日志工具类
 * 基于 SLF4J + Logback 实现
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/17
 */
@Slf4j
@Component
public class LogUtil {
    /**
     * 输出 DEBUG 级别日志
     */
    public static void debug(String message) {
        log.debug(message);
    }

    /**
     * 输出 DEBUG 级别日志（带参数）
     */
    public static void debug(String format, Object... args) {
        log.debug(format, args);
    }

    /**
     * 输出 DEBUG 级别日志（带异常）
     */
    public static void debug(String message, Throwable t) {
        log.debug(message, t);
    }

    /**
     * 输出 INFO 级别日志
     */
    public static void info(String message) {
        log.info(message);
    }

    /**
     * 输出 INFO 级别日志（带参数）
     */
    public static void info(String format, Object... args) {
        log.info(format, args);
    }

    /**
     * 输出 INFO 级别日志（带异常）
     */
    public static void info(String message, Throwable t) {
        log.info(message, t);
    }

    /**
     * 输出 WARN 级别日志
     */
    public static void warn(String message) {
        log.warn(message);
    }

    /**
     * 输出 WARN 级别日志（带参数）
     */
    public static void warn(String format, Object... args) {
        log.warn(format, args);
    }

    /**
     * 输出 WARN 级别日志（带异常）
     */
    public static void warn(String message, Throwable t) {
        log.warn(message, t);
    }

    /**
     * 输出 ERROR 级别日志
     */
    public static void error(String message) {
        log.error(message);
    }

    /**
     * 输出 ERROR 级别日志（带参数）
     */
    public static void error(String format, Object... args) {
        log.error(format, args);
    }

    /**
     * 输出 ERROR 级别日志（带异常）
     */
    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    /**
     * 判断是否开启 DEBUG 级别
     */
    public static boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * 判断是否开启 INFO 级别
     */
    public static boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }
}
