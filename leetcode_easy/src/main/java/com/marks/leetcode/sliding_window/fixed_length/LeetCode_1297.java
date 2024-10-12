package com.marks.leetcode.sliding_window.fixed_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1297 {
    /**
     * @Description: [
     * 给你一个字符串 s ，请你返回满足以下条件且出现次数最大的 任意 子串的出现次数：
     *
     * 子串中不同字母的数目必须小于等于 maxLetters 。
     * 子串的长度必须大于等于 minSize 且小于等于 maxSize 。
     * tips:
     * 1 <= s.length <= 10^5
     * 1 <= maxLetters <= 26
     * 1 <= minSize <= maxSize <= min(26, s.length)
     * s 只包含小写英文字母。
     * ]
     * @param s
     * @param maxLetters
     * @param minSize
     * @param maxSize
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int result = 0;
        result = method_01(s, maxLetters, minSize, maxSize);
        return result;
    }
    /**
     * @Description: [
     * AC:63ms/45.26MB
     * ]
     * @param s
     * @param maxLetters
     * @param minSize
     * @param maxSize
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int maxLetters, int minSize, int maxSize) {
        int n = s.length();
        int ans = 0;
        HashMap<String, Integer> map_str = new HashMap<>();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < minSize; i++) {
            char ch = s.charAt(i);
            map.put(ch,map.getOrDefault(ch, 0) + 1);
        }
        if (map.size() <= maxLetters) {
            map_str.put(s.substring(0, minSize), 1);
            ans = 1;
        }
        for (int i = minSize; i < n; i++) {
            // 滑动窗口, curr表示进入, pre表示移除
            char curr = s.charAt(i);
            char pre = s.charAt(i - minSize);
            map.put(curr, map.getOrDefault(curr, 0) + 1);
            if (map.get(pre) == 1) {
                map.remove(pre);
            }else {
                map.put(pre, map.get(pre) - 1);
            }
            // map.size()表示窗口中不同字符的长度
            if (map.size() <= maxLetters) {
                String curr_str = s.substring(i - minSize + 1, i + 1);
                map_str.put(curr_str, map_str.getOrDefault(curr_str, 0) + 1);
                int count = map_str.get(curr_str);
                ans = Math.max(ans, count);
            }
        }
        return ans;
    }
}
