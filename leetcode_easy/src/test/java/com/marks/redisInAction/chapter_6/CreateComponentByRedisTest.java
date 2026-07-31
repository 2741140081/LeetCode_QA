package com.marks.redisInAction.chapter_6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: CreateComponentByRedisTest </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/30 17:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class CreateComponentByRedisTest {
    private Jedis conn;

    @BeforeEach
    void setUp() {
        conn = new Jedis("localhost", 6379);
    }

    @AfterEach
    void tearDown() {
        if (conn != null) {
            conn.close();
        }
    }

    @Test
    void buildPrefix() {
        // 准备测试数据
        String guild = "testGuild";
        List<String> members = Arrays.asList("alice", "bob", "charlie", "alice2", "alice3");
        CreateComponentByRedis component = new CreateComponentByRedis();
        component.buildPrefix(conn, guild, members);

        List<String> list = component.searchByPrefix(conn, guild, "ali");
        // 输出
        System.out.println(list);
    }


}