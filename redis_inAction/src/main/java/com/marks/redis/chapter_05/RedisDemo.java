package com.marks.redis.chapter_05;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: RedisDemo </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/15 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class RedisDemo {
    private JedisPool jedisPool;

    public RedisDemo(String redisHost, int redisPort) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);

        this.jedisPool = new JedisPool(poolConfig, redisHost, redisPort);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void updateCounter(Jedis conn, String name, int count) {
        updateCounter(conn, name, count, System.currentTimeMillis() / 1000);
    }

    public static final int[] PRECISION = new int[]{1, 5, 60, 300, 3600, 18000, 86400};
    public void updateCounter(Jedis conn, String name, int count, long now){
        // 开启一个 redis 事务
        Transaction trans = conn.multi();
        for (int prec : PRECISION) {
            long pnow = (now / prec) * prec;
            String hash = String.valueOf(prec) + ':' + name;
            trans.zadd("known:", 0, hash);
            trans.hincrBy("count:" + hash, String.valueOf(pnow), count);
        }
        trans.exec();
    }


    public static void main(String[] args) {
        RedisDemo redisDemo = new RedisDemo("localhost", 6379);
        Jedis jedis = redisDemo.getJedis();
    }
}
