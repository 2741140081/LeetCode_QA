package com.marks.redisInAction.chapter_6;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.ZParams;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: CreateComponentByRedis </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/30 15:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class CreateComponentByRedis {

    /**
     * @Description:
     * 1. 更新最近联系人
     * 2. 每个用户都有自己的联系人列表, 列表存储在 redis 中
     * 3. addAndUpdate 方法用于更新指定用户的联系人列表, 并且对于每个用户仅保存最近的100个联系人信息
     * 4. 执行以下操作:
     * 4.1 获取当前用户 user 的联系人列表, 从列表中删除 contact 的旧数据, 如果 contact 是存在的.
     * 4.2 添加当前联系人 contact 到列表的顶部, 这是一条最新的联系人记录
     * 4.3 如果当前用户的联系人超过 100 个人, 那么删除最久未联系的 联系人信息
     * 5. 由于整个操作是一个事务, 需要保证原子性, 所以需要开启 redis 事务, 然后执行操作
     * lrem:(key, count, value): count = 0 表示移除所有; count > 0 表示最多从头到尾部 最多移除 count 个; count < 0, 则从尾部到头部最多移除 |count| 个
     * @param: conn
     * @param: user
     * @param: contact
     * @return void
     * @author marks
     * @CreateDate: 2026/07/30 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void addAndUpdateContact(Jedis conn, String user, String contact) {
        contact = contact.toLowerCase(); // 存入redis 中的都是小写字母
        // 构建用户联系人列表的 Redis key
        String key = "recent:" + user;
        // 开启 redis 事务
        Transaction trans = conn.multi();
        try {
            // 删除 contact 旧数据, 如果存在
            trans.lrem(key, 0, contact);
            // 添加联系人到列表顶部(左侧)
            trans.lpush(key, contact);
            // 删除末尾多余的联系人
            trans.ltrim(key, 0, 99);
            // 执行事务
            trans.exec();
        } catch (Exception e) {
            // 回滚
            trans.discard();
            throw new RuntimeException("更新联系人列表失败", e);
        }
    }

    /**
     * @Description:
     * 1. 构建联系人的自动补全列表
     * 2. 本来用字典树查询字符串的前缀应该是最优解, 但是由于列表会经常更新, 所以每次补全时都需要构建字典树,
     * 所以只能使用普通的 startWith 来进行前缀判断
     * @param: conn
     * @param: user
     * @param: prefix
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/07/30 15:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> fetchAutoCompleteList(Jedis conn, String user, String prefix) {
        prefix = prefix.toLowerCase();
        // 构建 key
        String key = "recent:" + user;
        // 从 redis 中读取联系人列表
        List<String> contactList = conn.lrange(key, 0, -1);
        List<String> ans = new ArrayList<>();
        for (String contact : contactList) {
            if (contact.startsWith(prefix)) {
                ans.add(contact);
            }
        }

        return ans;
    }


    // 使用 ZSET + ZRANGEBYLEX 来进行大量数据下的前缀查询

    public void buildPrefix(Jedis conn, String guild, List<String> members) {
        for (String member : members) {
            joinGuild(conn, guild, member);
        }
    }

    public void joinGuild(Jedis conn, String guild, String user) {
        conn.zadd("members:" + guild, 0, user);
    }

    public void leaveGuild(Jedis conn, String guild, String user) {
        conn.zrem("members:" + guild, user);
    }

    public List<String> searchByPrefix(Jedis conn, String guild, String prefix) {
        String key = "members:" + guild;
        String min = "[" + prefix;
        String max = "[" + prefix + "\uffff";
        return conn.zrangeByLex(key, min, max);
    }



    // 6.2 分布式锁

    /**
     * @Description:
     * 1. 通过 jedis.setnx() 来构建锁
     * @param: conn
     * @param: lockName
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/07/31 15:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String acquireLock(Jedis conn, String lockName) {
        return acquireLock(conn, lockName, 100000);
    }

    public String acquireLock(Jedis conn, String lockName, long acquireTimeout) {
        // 使用 UUID 获取唯一 id
        String identifier = UUID.randomUUID().toString();
        // 结束时间
        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {
            // 执行循环体
            if (conn.setnx("lock:" + lockName, identifier) == 1) {
                return identifier;
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

        return null;
    }

    // 过期锁
    public String acquireLockWithTimeout(Jedis conn, String lockName, long acquireTimeout, long lockTime) {
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:" + lockName;
        int expireTime = (int) (lockTime / 1000);

        long end = System.currentTimeMillis() + acquireTimeout;
        while (System.currentTimeMillis() < end) {
            if (conn.setnx(lockKey, identifier) == 1) {
                conn.expire(lockKey, expireTime); // 设置锁的过期时间
                return identifier;
            }

            // ttl: time to live 生存时间, > 0: 表示剩余的生存时间; -1: 表示永不过期; -2: key 不存在
            if (conn.ttl(lockKey) == -1) {
                // -1 表示 key 存在, 且没有过期时间
                conn.expire(lockKey, expireTime); // 设置过期时间
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }


    // 释放锁
    public boolean releaseLock(Jedis conn, String lockName, String identifier) {
        String lockKey = "lock:" + lockName;
        while (true) {
            conn.watch(lockKey);
            if (identifier.equals(conn.get(lockKey))) {
                Transaction trans = conn.multi(); // 开启事务
                trans.del(lockKey);
                List<Object> result = trans.exec();
                // 释放成功：result 不为 null，且 result.get(0) 等于 1。
                if (result == null) {
                    // 释放失败（事务中断）：result 为 null。
                    continue;
                }
                return true;
            }
            conn.unwatch();
            break;
        }

        return false;
    }


    public String acquireFairSemaphore(Jedis conn, String semName, int limit, long timeout) {
        // 1. 初始化标识符与key
        String identifier = UUID.randomUUID().toString();
        String czSet = semName + ":owner";
        String ctr = semName + ":counter";

        // 2. 清理过期请求
        long now = System.currentTimeMillis();
        Transaction trans = conn.multi();
        // 从有序集合 semName 中移除所有 score (时间戳) 小于 now - timeout 的成员, 目的: 释放那些超时未释放的信号量, 防止死锁
        trans.zremrangeByScore(semName.getBytes(), "-inf".getBytes(), String.valueOf(now - timeout).getBytes());

        // 3. 计算当前有效持有者
        ZParams zParams = new ZParams();
        // 权重设置, 意味着结果集合的 score 完全取决于 czSet 的 score（通常代表获取信号量的时间），而忽略 semName 的时间。
        // 这步操作是为了更新 czSet，确保它只包含那些在 semName 中依然存在的有效请求。
        zParams.weights(1, 0);
        // 对 czSet (旧的持有者集合) 和 semName(清理后的所有请求集合) 求交集
        trans.zinterstore(czSet, zParams, czSet, semName);
        // 计数器加1, 计数器的值代表了当前请求的“排队号”或“总数”。
        trans.incr(ctr);
        List<Object> response = trans.exec();
        int counter = ((Long)response.get(response.size() - 1)).intValue();

        // 4. 尝试获取信号量
        trans = conn.multi();
        trans.zadd(semName, now, identifier);
        trans.zadd(czSet, counter, identifier);
        trans.zrank(czSet, identifier);
        response = trans.exec();
        // 获取返回结果
        int result = ((Long) response.get(response.size() - 1)).intValue();
        if (result < limit) {
            // 如果计数器的值小于限制值 limit，说明还有空余的信号量名额，返回 identifier 表示获取成功。
            return identifier;
        }

        // 5. 获取失败处理, result >= limit, 信号量已满
        trans = conn.multi();
        // 从 semName 和 czSet 中移除当前 identifier.
        trans.zrem(semName, identifier);
        trans.zrem(czSet, identifier);
        trans.exec();
        return null;
    }


    public boolean releaseFairSemaphore(Jedis conn, String semName, String identifier) {
        Transaction trans = conn.multi();
        trans.zrem(semName, identifier);
        trans.zrem(semName + ":owner", identifier); // czSet
        List<Object> res = trans.exec();
        return (Long)res.get(res.size() - 1) == 1;
    }



}
