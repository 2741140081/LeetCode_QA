package com.marks.leetcode.data_structure.heap_advance;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * <p>项目名称: 股票价格波动 </p>
 * <p>文件名称: LeetCode_2034 </p>
 * <p>描述:
 * 给你一支股票价格的数据流。数据流中每一条记录包含一个 时间戳 和该时间点股票对应的 价格 。
 * 不巧的是，由于股票市场内在的波动性，股票价格记录可能不是按时间顺序到来的。
 * 某些情况下，有的记录可能是错的。如果两个有相同时间戳的记录出现在数据流中，前一条记录视为错误记录，后出现的记录 更正 前一条错误的记录。
 *
 * 请你设计一个算法，实现：
 *
 * 更新 股票在某一时间戳的股票价格，如果有之前同一时间戳的价格，这一操作将 更正 之前的错误价格。
 * 找到当前记录里 最新股票价格 。最新股票价格 定义为时间戳最晚的股票价格。
 * 找到当前记录里股票的 最高价格 。
 * 找到当前记录里股票的 最低价格 。
 * 请你实现 StockPrice 类：
 *
 * StockPrice() 初始化对象，当前无股票价格记录。
 * void update(int timestamp, int price) 在时间点 timestamp 更新股票价格为 price 。
 * int current() 返回股票 最新价格 。
 * int maximum() 返回股票 最高价格 。
 * int minimum() 返回股票 最低价格 。
 * </p>
 *
 * AC: 97ms/91.31MB
 * @author marks
 * @version v1.0
 * @date 2025/3/7 17:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class StockPrice {
    private Map<Integer, Integer> map;
    private Map<Integer, Integer> count;
    private TreeSet<Integer> set;
    private int max_timestamp;

    public StockPrice() {
        map = new HashMap<>();
        count = new HashMap<>();
        set = new TreeSet<>();
        max_timestamp = 0;
    }

    public void update(int timestamp, int price) {
        max_timestamp = Math.max(max_timestamp, timestamp);
        if (map.containsKey(timestamp)) {
            // 删除前一个错误的 pre_price
            int pre_price = map.get(timestamp);
            count.merge(pre_price, -1, Integer::sum);
            if (count.get(pre_price) < 1) {
                set.remove(pre_price);
            }
        }
        map.put(timestamp, price);
        set.add(price);
        count.merge(price, 1, Integer::sum);
    }

    /**
     * @Description: [功能描述]
     * 返回股票 最新价格
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int current() {
        return map.get(max_timestamp);
    }

    /**
     * @Description: [功能描述]
     * 返回股票 最高价格
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximum() {
        return set.last();
    }

    /**
     * @Description: [功能描述]
     * 返回股票 最低价格
     * @return int
     * @author marks
     * @CreateDate: 2025/3/7 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimum() {
        return set.first();
    }
}
