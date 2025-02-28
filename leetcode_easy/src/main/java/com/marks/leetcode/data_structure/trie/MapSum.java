package com.marks.leetcode.data_structure.trie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_677 </p>
 * <p>文件名称: 键值映射 </p>
 * <p>描述:
 * 设计一个 map ，满足以下几点:
 * 字符串表示键，整数表示值
 * 返回具有前缀等于给定字符串的键的值的总和
 * 实现一个 MapSum 类：
 *
 * MapSum() 初始化 MapSum 对象
 * void insert(String key, int val) 插入 key-val 键值对，字符串表示键 key ，整数表示值 val 。
 * 如果键 key 已经存在，那么原来的键值对 key-value 将被替代成新的键值对。
 *
 * int sum(string prefix) 返回所有以该前缀 prefix 开头的键 key 的值的总和。
 * </p>
 *
 * <p> AC: 14ms/41.71MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/2/28 15:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MapSum {
    Map<Character, MapSum> children;
    int value;

    Map<String, Integer> map;

    public MapSum() {
        children = new HashMap<>();
        map = new HashMap<>();
        value = 0;
    }

    public void insert(String key, int val) {
        MapSum curr = this;
        int currNum = val;
        if (map.containsKey(key)) {
            val = val - map.get(key);
        }
        map.put(key, currNum);
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            curr.children.putIfAbsent(ch, new MapSum());
            curr = curr.children.get(ch);
            curr.value += val;
        }
    }

    public int sum(String prefix) {
        MapSum curr = this;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            if (curr.children.containsKey(ch)) {
                curr = curr.children.get(ch);
            } else {
                return 0;
            }
        }
        return curr.value;
    }

}
