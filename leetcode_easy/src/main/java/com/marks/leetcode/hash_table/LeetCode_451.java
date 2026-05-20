package com.marks.leetcode.hash_table;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_451 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/20 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_451 {

    /**
     * @Description:
     * 给定一个字符串 s ，根据字符出现的 频率 对其进行 降序排序 。一个字符出现的 频率 是它出现在字符串中的次数。
     * 返回 已排序的字符串 。如果有多个答案，返回其中任何一个。
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/05/20 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String frequencySort(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 直接使用 HashMap 统计字符出现的次数，然后对字符进行排序
     * AC: 16ms/45.59MB
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/05/20 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            map.merge(c, 1, Integer::sum);
        }
        Node[] nodes = new Node[map.size()];
        int i = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            nodes[i] = new Node();
            nodes[i].c = entry.getKey();
            nodes[i].count = entry.getValue();
            i++;
        }
        Arrays.sort(nodes);
        StringBuilder sb = new StringBuilder();
        for (Node node : nodes) {
            for (int j = 0; j < node.count; j++) {
                sb.append(node.c);
            }
        }

        return sb.toString();
    }

    class Node implements Comparable<Node>{
        char c;
        int count;

        @Override
        public int compareTo(@NotNull Node o) {
            return o.count - this.count;
        }
    }

}
