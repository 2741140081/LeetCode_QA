package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_692 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/29 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_692 {

    /**
     * @Description:
     * 给定一个单词列表 words 和一个整数 k ，返回前 k 个出现次数最多的单词。
     * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率， 按字典顺序 排序。
     * @param: words
     * @param: k
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/05/29 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> topKFrequent(String[] words, int k) {
        List<String> result;
        result = method_01(words, k);
        return result;
    }

    /**
     * @Description:
     * 1. 统计每一个单词出现的次数
     * 2. 使用优先队列, 将单词按照出现次数从高到低排序, 如果出现次数相同, 则按照字典顺序排序
     * AC: 7ms/45.57MB
     * @param: words
     * @param: k
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/05/29 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] words, int k) {
        // 1. 统计每一个单词出现的次数
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }
        // 创建一个优先队列, 默认是按照升序排序的
        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() == o1.getValue() ? o1.getKey().compareTo(o2.getKey()) : o2.getValue() - o1.getValue();
            }
        });
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            queue.offer(entry);
        }
        List<String> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(queue.poll().getKey());
        }

        return result;
    }

}
