package com.marks.redisInAction.chapter_2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: ServiceDiscoveryAndConfig </p>
 * <p>描述: 5.4 服务发现与配置 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/22 15:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class ServiceDiscoveryAndConfig {
    // 5.4.1 维护状态
    private long lastChecked; // 上一次检测的时间
    private boolean underMaintenance; // 是否处于维护状态

    public boolean isUnderMaintenance(Jedis conn) {
        // 每隔1s执行检测, 获取 redis 中数据, 判断是否处于维护中
        if (System.currentTimeMillis() - lastChecked > 1000) {
            lastChecked = System.currentTimeMillis(); // 更新检测时间
            String res = conn.get("is-under-maintenance");
            underMaintenance = "yes".equals(res);
        }
        return underMaintenance;
    }

    public void setConfig(Jedis conn, String type, String component, Map<String, Object> config) {
        Gson gson = new Gson();
        conn.set("config:" + type + ":" + component, gson.toJson(config));
    }

    // 5.4.2 缓存配置信息
    private static final Map<String, Map<String, Object>> CONFIGS = new HashMap<>();
    private static final Map<String, Long> CHECKED = new HashMap<>();


    public Map<String, Object> getConfig(Jedis conn, String type, String component) {
        int wait = 1000;
        String key = "config:" + type + ":" + component;
        Long lastChecked = CHECKED.get(key);
        if (lastChecked == null || System.currentTimeMillis() - lastChecked > wait) {
            CHECKED.put(key, System.currentTimeMillis());
            String value = conn.get(key);
            Map<String, Object> config;
            if (value != null) {
                Gson gson = new Gson();
                config = gson.fromJson(value, new TypeToken<Map<String, Object>>(){}.getType());
            } else {
                config = new HashMap<>();
            }
            CONFIGS.put(key, config);
        }

        return CONFIGS.get(key);
    }

    // 5.4.3 自动 redis 连接管理

}
