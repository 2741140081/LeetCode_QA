package com.marks.tools.dbUtil;

import com.marks.tools.spider.common.ConfigCommon;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: DatabasePool </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/12 15:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class DatabasePool {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");  // 如需要可显式指定
        config.setJdbcUrl(ConfigCommon.DB_URL);
        config.setUsername(ConfigCommon.DB_USER);
        config.setPassword(ConfigCommon.DB_PASSWORD);

        // 连接池设置
        config.setMaximumPoolSize(50); // 最大连接数 - 提高以应对高并发
        config.setMinimumIdle(10); // 最小空闲连接数 - 保持更多空闲连接
        config.setConnectionTimeout(60000); // 连接超时时间 - 增加到60秒
        config.setIdleTimeout(300000); // 空闲连接超时时间 - 5分钟
        config.setMaxLifetime(1800000); // 连接最大生命周期 - 30分钟
        config.setLeakDetectionThreshold(60000); // 启用连接泄漏检测 - 1分钟

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
