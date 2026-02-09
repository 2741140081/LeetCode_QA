package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2900 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 10:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2900 {

    /**
     * @Description:
     * 给定一个字符串数组 words ，和一个 二进制 数组 groups ，两个数组长度都是 n 。
     * 如果 words 的一个 子序列 是交替的，那么对于序列中的任意两个连续字符串，它们在 groups 中相同索引的对应元素是 不同 的（也就是说，不能有连续的 0 或 1），
     * 你需要从 words 中选出 最长交替子序列。
     * 返回选出的子序列。如果有多个答案，返回 任意 一个。
     * 注意：words 中的元素是不同的 。
     * tips:
     * 1 <= n == words.length == groups.length <= 100
     * 1 <= words[i].length <= 10
     * groups[i] 是 0 或 1。
     * words 中的字符串 互不相同 。
     * words[i] 只包含小写英文字母。
     * @param: words
     * @param: groups
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/02/09 10:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> getLongestSubsequence(String[] words, int[] groups) {
        List<String> result;
        result = method_01(words, groups);
        result = method_02(words, groups);
        return result;
    }

    /**
     * @Description:
     * 1. 直接贪心就好了, 不需要进行区分
     * AC: 1ms/46.15MB
     * @param: words
     * @param: groups
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/02/09 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_02(String[] words, int[] groups) {
        List<String> ans = new ArrayList<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            if (ans.isEmpty() || groups[i] != groups[i - 1]) {
                ans.add(words[i]);
            }
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 想复杂了, 就2种形式, 要么 0101, 要么 1010
     * AC: 4ms/45.74MB
     * @param: words
     * @param: groups
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/02/09 10:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] words, int[] groups) {
        int n = words.length;
        List<String> s0 = new ArrayList<>(); // 0101 类型
        List<String> s1 = new ArrayList<>(); // 1010 类型
        Map<String, Integer> map = new HashMap<>();
        map.put("S0", 0);
        s0.add("S0");
        map.put("S1", 1);
        s1.add("S1");
        for (int i = 0; i < n; i++) {
            map.put(words[i], groups[i]);
            int lastS0 = map.get(s0.get(s0.size() - 1));
            int lastS1 = map.get(s1.get(s1.size() - 1));
            if (lastS0 != groups[i]) {
                s0.add(words[i]);
            }
            if (lastS1 != groups[i]) {
                s1.add(words[i]);
            }
        }
        // 删除S0 和 S1
        if (s0.size() >= s1.size()) {
            s0.remove(0); // 删除额外 "S0"
            return s0;
        } else {
            s1.remove(0);
            return s1;
        }
    }

}
