package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 9:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3090 {
    /**
     * @Description: [
     * 给你一个字符串 s ，请找出满足每个字符最多出现两次的最长子字符串，并返回该子字符串的 最大 长度。
     *
     * tips:
     * 2 <= s.length <= 100
     * s 仅由小写英文字母组成。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumLengthSubstring(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入： s = "bcbbbcba"
     * 输出： 4
     * 解释：
     * 以下子字符串长度为 4，并且每个字符最多出现两次："bcbbbcba"。
     * AC:6ms/43.36MB
     * 优化sum的求和方式: 不需要Arrays.stream(pre).sum()来计算sum, 直接用sum = 右边界i - 左边界left + 1
     * AC:1ms/41.29MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 10:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        // 存储left~i窗口中字符的个数, 由于全部为小写字母, 所以26空间即可
        int[] pre = new int[26];
        // left为窗口的左边界, 当进入窗口的字符pre[s.charAt(i) - 'a'] > 2时, 需要弹出字符(left ~ j),使得剩下的字符(j + 1 ~ i)符合条件
        int left = 0;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            pre[ch - 'a']++;
            if (pre[ch - 'a'] > 2) {
                // 弹出left ~ j之间的字母, 并且设置left = j + 1
                for (int j = left; j < i; j++) {
                    if (s.charAt(j) == ch) {
                        left = j + 1;
                        pre[ch - 'a']--;
                        break;
                    }else {
                        pre[s.charAt(j) - 'a']--;
                    }
                }
            }else {
                // sum保存pre的长度, 不需要Arrays.stream(pre).sum()来计算sum, 直接用sum = 右边界i - 左边界left + 1

                int sum = i - left + 1;
                ans = Math.max(ans, sum);
            }
        }
        return ans;
    }
}
