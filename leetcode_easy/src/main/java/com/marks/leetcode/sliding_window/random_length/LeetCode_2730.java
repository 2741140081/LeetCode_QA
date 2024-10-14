package com.marks.leetcode.sliding_window.random_length;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.HashSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 11:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2730 {
    private int left = 0;
    /**
     * @Description: [
     * 给你一个下标从 0 开始的字符串 s ，这个字符串只包含 0 到 9 的数字字符。
     * 如果一个字符串 t 中至多有一对相邻字符是相等的，那么称这个字符串 t 是 半重复的 。例如，"0010" 、"002020" 、"0123" 、"2002"
     * 和 "54944" 是半重复字符串，而 "00101022" （相邻的相同数字对是 00 和 22）和 "1101234883"
     * （相邻的相同数字对是 11 和 88）不是半重复字符串。
     * 请你返回 s 中最长 半重复子字符串的长度。
     * tips:
     * 1 <= s.length <= 50
     * '0' <= s[i] <= '9'
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 11:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSemiRepetitiveSubstring(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：s = "52233"
     * 输出：4
     * 解释：最长的半重复子字符串是 "5223"。整个字符串 "52233" 有两个相邻的相同数字对 22 和 33，但最多只能选取一个。
     * AC:5ms/44.10MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 11:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int ans = 0;
        left = 0;
        char pre = s.charAt(0);
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pre == ch) {
                // pre == ch
                checkIsSemiRepetitive(s, i);
            }
            ans = Math.max(ans, i - left + 1);
            pre = ch;
        }
        return ans;
    }

    private void checkIsSemiRepetitive(String s, int end) {
        char pre = s.charAt(left);
        int count = 0;
        int first = 0;
        for (int i = left + 1; i <= end; i++) {
            char curr = s.charAt(i);
            if (pre == curr && count == 0) {
                count++;
                first = i;
            }else if (pre == curr && count == 1) {
                left = first;
            }
            pre = curr;
        }
    }
}
