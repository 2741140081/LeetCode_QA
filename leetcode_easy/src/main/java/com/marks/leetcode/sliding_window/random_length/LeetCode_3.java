package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;
import java.util.HashSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 16:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3 {
    /**
     * @Description: [
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串的长度。
     *
     * tips:
     * 0 <= s.length <= 5 * 10^4
     * s 由英文字母、数字、符号和空格组成
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lengthOfLongestSubstring(String s) {
        int result = 0;
        result = method_01(s);
        result = method_02(s);
        return result;
    }
    /**
     * @Description: [
     * 查看题解的Set集合, 滑动窗口和双指针
     * AC:6ms/43.67MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 17:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int n = s.length();
        HashSet<Character> set = new HashSet<>();
        int left = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            char curr = s.charAt(i);
            while (set.contains(curr)) {
                set.remove(s.charAt(left));
                left++;
            }
            set.add(curr);
            ans = Math.max(ans, i - left + 1);

        }
        return ans;
    }

    /**
     * @Description: [
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * AC:53ms/44.27MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 17:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        if (n == 0) {
            return 0;
        }
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            map.put(ch, map.getOrDefault(ch, 0) + 1);

        }
        int ans = 1;
        for (int k = map.size(); k >= 1 && ans < k; k--) {
            // 初始化窗口长度为k = map.size(), 即s字符串中不同字符的数量
            HashMap<Character, Integer> currMap = new HashMap<>();
            for (int i = 0; i < k; i++) {
                char ch = s.charAt(i);
                currMap.put(ch, currMap.getOrDefault(ch, 0) + 1);
            }
            int count = currMap.size() == k ? currMap.size() : 0;
            for (int i = k; i < n; i++) {
                char curr = s.charAt(i);
                char pre = s.charAt(i - k);
                currMap.put(curr, currMap.getOrDefault(curr, 0) + 1);
                if (currMap.get(pre) == 1) {
                    currMap.remove(pre);
                }else {
                    currMap.put(pre, currMap.get(pre) - 1);
                }
                if (currMap.size() == k) {
                    count = k;
                    break;
                }
            }
            ans = Math.max(ans, count);
        }
        return ans;
    }
}
