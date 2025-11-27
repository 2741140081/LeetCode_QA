package com.marks.leetcode.binary_algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: TimeMap </p>
 * <p>描述: LeetCode_981 </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/26 15:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TimeMap {
    private Map<String, List<Node>> map;

    /**
     * @Description:
     * TimeMap() 初始化数据结构对象
     * 1. 用什么来存储? 首先一点key 应该用map来进行存储, 但是值需要用一个 List 来存储
     * AC: 150ms/105.95MB
     * @return
     * @author marks
     * @CreateDate: 2025/11/26 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TimeMap() {
        map = new HashMap<>();
    }

    /**
     * @Description:
     * void set(String key, String value, int timestamp) 存储给定时间戳 timestamp 时的键 key 和值 value。
     * @param: key
     * @param: value
     * @param: timestamp
     * @return void
     * @author marks
     * @CreateDate: 2025/11/26 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void set(String key, String value, int timestamp) {
        Node currNode = new Node(value, timestamp);
        if (map.containsKey(key)) {
            map.get(key).add(currNode);
        } else {
            List<Node> list = new ArrayList<>();
            list.add(currNode);
            map.put(key, list);
        }
    }

    /**
     * @Description:
     * 返回一个值，该值在之前调用了 set，其中 timestamp_prev <= timestamp 。
     * 如果有多个这样的值，它将返回与最大  timestamp_prev 关联的值。如果没有值，则返回空字符串（""）
     * @param: key
     * @param: timestamp
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/11/26 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String get(String key, int timestamp) {
        // 题目确保了get 操作中，key 存在
        if (!map.containsKey(key)) {
            return "";
        }
        List<Node> list = map.get(key);
        int left = 0;
        int right = list.size() - 1;
        int target = -1;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (list.get(mid).timestamp <= timestamp) {
                left = mid + 1;
                target = mid;
            } else {
                right = mid - 1;
            }
        }
        return target == -1 ? "" : list.get(target).value;
    }

    // 增加一个内部类
    private class Node {
        private String value;
        private int timestamp;
        public Node(String value, int timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }
}
