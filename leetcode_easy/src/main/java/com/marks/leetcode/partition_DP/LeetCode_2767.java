package com.marks.leetcode.partition_DP;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/20 18:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2767 {
    private ArrayList<String> list = new ArrayList<>();
    /**
     * @Description: [
     * 给你一个二进制字符串 s ，你需要将字符串分割成一个或者多个 子字符串  ，使每个子字符串都是 美丽 的。
     *
     * 如果一个字符串满足以下条件，我们称它是 美丽 的：
     *
     * 它不包含前导 0 。
     * 它是 5 的幂的 二进制 表示。
     * 请你返回分割后的子字符串的 最少 数目。如果无法将字符串 s 分割成美丽子字符串，请你返回 -1 。
     *
     * 子字符串是一个字符串中一段连续的字符序列。
     *
     * tips:
     * 1 <= s.length <= 15
     * s[i] 要么是 '0' 要么是 '1' 。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 9:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumBeautifulSubstrings(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }
    /**
     * @Description: [
     * {"1", "101", "11001", "1111101"}
     * 解决这种问题的一般思路是什么？
     * 假设前 i 个已经划分为最少
     * 以 "1011" 为案例进行分析
     * list中包含 {"1", "101"}
     * 从左向右遍历 "1011"
     * dp[0] = 0;
     * dp[1] = Math.min()
     *
     * AC:19ms/43.95MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        if (s.charAt(0) == '0') {
            return -1;
        }
        for (int i = 0; i < 7; i++) {
            int res = (int) Math.pow(5, i);
            String str = Integer.toBinaryString(res);
            list.add(str);
            if (str.length() > n) {
                break;
            }
        }
        int[] dp = new int[n + 1];
        final int INF = 0x3f3f3f3f;
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            String t = "";
            for (int j = i - 1; j >= 0; j--) {
                t = s.charAt(j) + t;
                if (list.contains(t)) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n] == INF ? -1 : dp[n];
    }
}
