package com.marks.redisInAction.chapter_5;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.ZParams;
import redis.clients.jedis.resps.Tuple;

import java.io.File;
import java.io.FileReader;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static final Collator COLLATOR = Collator.getInstance();

    public static final SimpleDateFormat TIMESTAMP =
            new SimpleDateFormat("EEE MMM dd HH:00:00 yyyy");
    private static final SimpleDateFormat ISO_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd'T'HH:00:00");

    public List<Object> updateStats(Jedis conn, String context, String type, double value){
        int timeout = 5000;
        String destination = "stats:" + context + ':' + type;
        String startKey = destination + ":start";
        long end = System.currentTimeMillis() + timeout;
        while (System.currentTimeMillis() < end){
            conn.watch(startKey);
            String hourStart = ISO_FORMAT.format(new Date());

            String existing = conn.get(startKey);
            Transaction trans = conn.multi();
            if (existing != null && COLLATOR.compare(existing, hourStart) < 0){
                trans.rename(destination, destination + ":last");
                trans.rename(startKey, destination + ":pstart");
                trans.set(startKey, hourStart);
            }

            String tkey1 = UUID.randomUUID().toString();
            String tkey2 = UUID.randomUUID().toString();
            trans.zadd(tkey1, value, "min");
            trans.zadd(tkey2, value, "max");

            trans.zunionstore(
                    destination,
                    new ZParams().aggregate(ZParams.Aggregate.MIN),
                    destination, tkey1);
            trans.zunionstore(
                    destination,
                    new ZParams().aggregate(ZParams.Aggregate.MAX),
                    destination, tkey2);

            trans.del(tkey1, tkey2);
            trans.zincrby(destination, 1, "count");
            trans.zincrby(destination, value, "sum");
            trans.zincrby(destination, value * value, "sumsq");

            List<Object> results = trans.exec();
            if (results == null){
                continue;
            }
            return results.subList(results.size() - 3, results.size());
        }
        return null;
    }

    public Map<String,Double> getStats(Jedis conn, String context, String type){
        String key = "stats:" + context + ':' + type;
        Map<String,Double> stats = new HashMap<String,Double>();
        List<Tuple> data = conn.zrangeWithScores(key, 0, -1);
        for (Tuple tuple : data){
            stats.put(tuple.getElement(), tuple.getScore());
        }
        stats.put("average", stats.get("sum") / stats.get("count"));
        double numerator = stats.get("sumsq") - Math.pow(stats.get("sum"), 2) / stats.get("count");
        double count = stats.get("count");
        stats.put("stddev", Math.pow(numerator / (count > 1 ? count - 1 : 1), .5));
        return stats;
    }

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
    private static final Map<String, Jedis> REDIS_CONNECTIONS = new HashMap<>();

    public Jedis getRedisConnection(String component) {
        // 从 map 中获取 Jedis 连接
        Jedis jedis = REDIS_CONNECTIONS.get("config");
        if (jedis == null) {
            // 新建jedis连接
            jedis = new Jedis("localhost");
            jedis.select(15);
            REDIS_CONNECTIONS.put("config", jedis);
        }

        String key = "config:jedis:" + component;
        Map<String, Object> oldConfig = CONFIGS.get(key);
        Map<String, Object> config = getConfig(jedis, "redis", component);
        if (!config.equals(oldConfig)) {
            // 更新
            Jedis conn = new Jedis("localhost");
            if (config.containsKey("db")) {
                conn.select(((Double)config.get("db")).intValue());
            }

            REDIS_CONNECTIONS.put(key, conn);
        }
        return REDIS_CONNECTIONS.get(key);
    }



    public void importIpsToRedis(Jedis conn, File file) {
        FileReader reader = null;
        try{
            reader = new FileReader(file);
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT);
            int count = 0;
            for (CSVRecord line : parser) {
                String startIp = line.size() > 1 ? line.get(0) : "";
                if (startIp.toLowerCase().indexOf('i') != -1){
                    continue;
                }
                int score = 0;
                if (startIp.indexOf('.') != -1){
                    score = ipToScore(startIp);
                }else{
                    try{
                        score = Integer.parseInt(startIp, 10);
                    }catch(NumberFormatException nfe){
                        continue;
                    }
                }

                String cityId = line.get(2) + '_' + count;
                conn.zadd("ip2cityid:", score, cityId);
                count++;
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            try{
                reader.close();
            }catch(Exception e){
                // ignore
            }
        }
    }

    public void importCitiesToRedis(Jedis conn, File file) {
        Gson gson = new Gson();
        FileReader reader = null;
        try{
            reader = new FileReader(file);
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT);
            for (CSVRecord line : parser) {
                if (line.size() < 4 || !Character.isDigit(line.get(0).charAt(0))){
                    continue;
                }
                String cityId = line.get(0);
                String country = line.get(1);
                String region = line.get(2);
                String city = line.get(3);
                String json = gson.toJson(new String[]{city, region, country});
                conn.hset("cityid2city:", cityId, json);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally{
            try{
                reader.close();
            }catch(Exception e){
                // ignore
            }
        }
    }

    public int ipToScore(String ipAddress) {
        int score = 0;
        for (String v : ipAddress.split("\\.")){
            score = score * 256 + Integer.parseInt(v, 10);
        }
        return score;
    }

    public String randomOctet(int max) {
        return String.valueOf((int)(Math.random() * max));
    }

    public String[] findCityByIp(Jedis conn, String ipAddress) {
        int score = ipToScore(ipAddress);
        List<String> results = conn.zrevrangeByScore("ip2cityid:", score, 0, 0, 1);
        if (results.size() == 0) {
            return null;
        }

        String cityId = results.iterator().next();
        cityId = cityId.substring(0, cityId.indexOf('_'));
        return new Gson().fromJson(conn.hget("cityid2city:", cityId), String[].class);
    }

    public class CleanCountersThread
            extends Thread
    {
        private Jedis conn;
        private int sampleCount = 100;
        private boolean quit;
        private long timeOffset; // used to mimic a time in the future.

        public CleanCountersThread(int sampleCount, long timeOffset){
            this.conn = new Jedis("localhost");
            this.conn.select(15);
            this.sampleCount = sampleCount;
            this.timeOffset = timeOffset;
        }

        public void quit(){
            quit = true;
        }

        public void run(){
            int passes = 0;
            while (!quit){
                long start = System.currentTimeMillis() + timeOffset;
                int index = 0;
                while (index < conn.zcard("known:")){
                    List<String> hashSet = conn.zrange("known:", index, index);
                    index++;
                    if (hashSet.size() == 0) {
                        break;
                    }
                    String hash = hashSet.iterator().next();
                    int prec = Integer.parseInt(hash.substring(0, hash.indexOf(':')));
                    int bprec = (int)Math.floor(prec / 60);
                    if (bprec == 0){
                        bprec = 1;
                    }
                    if ((passes % bprec) != 0){
                        continue;
                    }

                    String hkey = "count:" + hash;
                    String cutoff = String.valueOf(
                            ((System.currentTimeMillis() + timeOffset) / 1000) - sampleCount * prec);
                    ArrayList<String> samples = new ArrayList<String>(conn.hkeys(hkey));
                    Collections.sort(samples);
                    int remove = bisectRight(samples, cutoff);

                    if (remove != 0){
                        conn.hdel(hkey, samples.subList(0, remove).toArray(new String[0]));
                        if (remove == samples.size()){
                            conn.watch(hkey);
                            if (conn.hlen(hkey) == 0) {
                                Transaction trans = conn.multi();
                                trans.zrem("known:", hash);
                                trans.exec();
                                index--;
                            }else{
                                conn.unwatch();
                            }
                        }
                    }
                }

                passes++;
                long duration = Math.min(
                        (System.currentTimeMillis() + timeOffset) - start + 1000, 60000);
                try {
                    sleep(Math.max(60000 - duration, 1000));
                }catch(InterruptedException ie){
                    Thread.currentThread().interrupt();
                }
            }
        }

        // mimic python's bisect.bisect_right
        public int bisectRight(List<String> values, String key) {
            int index = Collections.binarySearch(values, key);
            return index < 0 ? Math.abs(index) - 1 : index + 1;
        }
    }

    public class AccessTimer {
        private Jedis conn;
        private long start;

        public AccessTimer(Jedis conn){
            this.conn = conn;
        }

        public void start(){
            start = System.currentTimeMillis();
        }

        public void stop(String context){
            long delta = System.currentTimeMillis() - start;
            List<Object> stats = updateStats(conn, context, "AccessTime", delta / 1000.0);
            double average = (Double)stats.get(1) / (Double)stats.get(0);

            Transaction trans = conn.multi();
            trans.zadd("slowest:AccessTime", average, context);
            trans.zremrangeByRank("slowest:AccessTime", 0, -101);
            trans.exec();
        }
    }

}
