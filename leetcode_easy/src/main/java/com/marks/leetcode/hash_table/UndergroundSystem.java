package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: UndergroundSystem </p>
 * <p>描述:
 *
 * 地铁系统跟踪不同车站之间的乘客出行时间，并使用这一数据来计算从一站到另一站的平均时间。
 * 实现 UndergroundSystem 类：
 * void checkIn(int id, string stationName, int t)
 * 通行卡 ID 等于 id 的乘客，在时间 t ，从 stationName 站进入
 * 乘客一次只能从一个站进入
 * void checkOut(int id, string stationName, int t)
 * 通行卡 ID 等于 id 的乘客，在时间 t ，从 stationName 站离开
 * double getAverageTime(string startStation, string endStation)
 * 返回从 startStation 站到 endStation 站的平均时间
 * 平均时间会根据截至目前所有从 startStation 站 直接 到达 endStation 站的行程进行计算，也就是从 startStation 站进入并从 endStation 离开的行程
 * 从 startStation 到 endStation 的行程时间与从 endStation 到 startStation 的行程时间可能不同
 * 在调用 getAverageTime 之前，至少有一名乘客从 startStation 站到达 endStation 站
 * 你可以假设对 checkIn 和 checkOut 方法的所有调用都是符合逻辑的。如果一名乘客在时间 t1 进站、时间 t2 出站，那么 t1 < t2 。所有时间都按时间顺序发生。
 *
 * tips:
 * 1 <= id, t <= 10^6
 * 1 <= stationName.length, startStation.length, endStation.length <= 10
 * 所有字符串由大小写英文字母与数字组成
 * 总共最多调用 checkIn、checkOut 和 getAverageTime 方法 2 * 10^4 次
 * 与标准答案误差在 10-5 以内的结果都被视为正确结果
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/21 15:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UndergroundSystem {
    private Map<Integer, Record> checkInMap;
    private Map<String, Double> avgTimeMap;
    private Map<String, int[]> timeSumMap;

    /**
     * @Description:
     * 1. checkIn 会添加一条入站记录, checkOut 会添加一条出站记录
     * 2. 一个人的id, 在某一个之间段内, 必定先进站后出站, 并且当处理到 personId 的时候出站时, 此前必定已经入站
     * 3. 用map 作为存储信息, key 为 personId, value 为 checkInTime, stationName, time
     * 4. 其实只需要存储checkIn 的信息, 当checkOut 被调用的时候, 从 map 中取出数据, 计算时间差, 存储结果集合
     * 5. 这个结果集合怎么存储? 站台名称是一个String 类型, 那就构建一个 start+ "-" + end 的key, value 为 avgTime;
     * 6. 还需要一个 key 的总时间和次数, 用于计算平均时间
     * AC: 110ms/56.95MB
     * @return
     * @author marks
     * @CreateDate: 2026/05/21 15:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public UndergroundSystem() {
        checkInMap = new HashMap<>();
        avgTimeMap = new HashMap<>();
        timeSumMap = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        // 创建record 记录
        Record record = new Record(stationName, t);
        checkInMap.put(id, record);
    }

    public void checkOut(int id, String stationName, int t) {
        // 通过 id 获取 record
        Record record = checkInMap.get(id);
        // 不需要进行remove操作, 因为后续checkIn 操作会覆盖之前的record.
        String key = record.getStartStation() + "-" + stationName;
        int currTime = t - record.getTime();
        // 判断 key 是否存在
        if (avgTimeMap.containsKey(key)) {
            // 获取总时间
            int[] timeSum = timeSumMap.get(key);
            double sumTime = timeSumMap.get(key)[0] + currTime;
            timeSum[0] = (int) sumTime;
            timeSum[1] += 1;
            timeSumMap.put(key, timeSum);
            // 获取平均时间
            double avgTime = sumTime / timeSum[1];
            // 添加到 map 中
            avgTimeMap.put(key, avgTime);
        } else {
            // 添加到 map 中
            avgTimeMap.put(key, (double) currTime);
            timeSumMap.put(key, new int[]{currTime, 1});
        }
    }

    public double getAverageTime(String startStation, String endStation) {
        // 拼接得到 key
        String key = startStation + "-" + endStation;
        return avgTimeMap.getOrDefault(key, 0.0);
    }

    private class Record {
        private String startStation;
        private int time;

        public Record(String startStation, int t) {
            this.startStation = startStation;
            this.time = t;
        }

        public String getStartStation() {
            return startStation;
        }

        public int getTime() {
            return time;
        }
    }
}
