package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2472 {
    /**
     * @Description: [
     * 给你一个字符串 s 和一个 正 整数 k 。
     * 从字符串 s 中选出一组满足下述条件且不重叠 的子字符串：
     * 每个子字符串的长度 至少 为 k 。
     * 每个子字符串是一个 回文串 。
     * 返回最优方案中能选择的子字符串的 最大 数目。
     *
     * 子字符串 是字符串中一个连续的字符序列。
     *
     * tips:
     * 1 <= k <= s.length <= 2000
     * s 仅由小写英文字母组成
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxPalindromes(String s, int k) {
        int result = 0;
        result = method_01(s, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = "abaccdbbd", k = 3
     * 输出：2
     * 解释：可以选择 s = "abaccdbbd" 中斜体加粗的子字符串。"aba" 和 "dbbd" 都是回文，且长度至少为 k = 3 。
     * 可以证明，无法选出两个以上的有效子字符串。
     * 磕磕绊绊还是做出来了, 哈哈哈！！1
     * AC:203ms/54.00MB
     * ]
     * @param s
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        boolean[][] pre = new boolean[n][n]; // 存储dp[i][j] 是否是回文串
        for (int i = 0; i < n; i++) {
            Arrays.fill(pre[i], true); // 设置默认值为true
        }
        // 预处理dp[i][j], 判断是否是回文串
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                pre[i][j] = (s.charAt(i) == s.charAt(j)) && pre[i + 1][j - 1];
            }
        }

        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];
            for (int j = i - 1; j >= 0; j--) {
                if (i - j >= k) {
                    if (pre[j][i - 1]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                    }
                }

            }
        }
        return dp[n];
    }
}
