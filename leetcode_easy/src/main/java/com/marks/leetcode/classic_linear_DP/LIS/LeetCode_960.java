package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 11:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_960 {
    /**
     * @Description: [
     * 给定由 n 个小写字母字符串组成的数组 strs ，其中每个字符串长度相等。
     * 选取一个删除索引序列，对于 strs 中的每个字符串，删除对应每个索引处的字符。
     * 比如，有 strs = ["abcdef","uvwxyz"] ，删除索引序列 {0, 2, 3} ，删除后为 ["bef", "vyz"] 。
     * 假设，我们选择了一组删除索引 answer ，那么在执行删除操作之后，最终得到的数组的行中的 每个元素 都是按字典序排列的
     * （即 (strs[0][0] <= strs[0][1] <= ... <= strs[0][strs[0].length - 1]) 和
     * (strs[1][0] <= strs[1][1] <= ... <= strs[1][strs[1].length - 1]) ，依此类推）。
     * 请返回 answer.length 的最小可能值 。
     *
     * tips:
     * n == strs.length
     * 1 <= n <= 100
     * 1 <= strs[i].length <= 100
     * strs[i] 由小写英文字母组成
     * ]
     * @param strs
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDeletionSize(String[] strs) {
        int result = 0;
        result = method_01(strs);
        int result2 = method_02(strs);
        return result == result2 ? result : result2;
    }
    /**
     * @Description: [官方题解]
     * @param strs
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String[] strs) {
        int n = strs[0].length();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = n-2; i >= 0; --i) {
            // 标识符: + continue 标识符 相当于goto语句
            search:
            for (int j = i + 1; j < n; ++j) {
                for (String row : strs) {
                    if (row.charAt(i) > row.charAt(j)) {
                        continue search;
                    }
                }
                dp[i] = Math.max(dp[i], 1 + dp[j]);
            }
        }
        int kept = 0;
        for (int x: dp)
            kept = Math.max(kept, x);
        return n - kept;
    }

    /**
     * @Description: [
     * E1:
     * 输入：strs = ["babca","bbazb"]
     * 输出：3
     * 解释：
     * 删除 0、1 和 4 这三列后，最终得到的数组是 strs = ["bc", "az"]。
     * 这两行是分别按字典序排列的（即，strs[0][0] <= strs[0][1] 且 strs[1][0] <= strs[1][1]）。
     * 注意，strs[0] > strs[1] —— 数组 strs 不一定是按字典序排列的。
     *
     * s1[1,1,2,2,2]
     * s2[1,2,1,3,3]
     *
     * 思路: 对每一个strs[i] 进行dp， 求第i个序列能组成字典序的最大值
     * 结论：错误的！！！！，官方题解method_02
     * ]
     * @param strs
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] strs) {
        int m = strs.length;
        int n = strs[0].length();
        int[][] dp = new int[m][n];


        for (int k = 0; k < m; k++) {
            Arrays.fill(dp[k], 1);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (strs[k].charAt(i) >= strs[k].charAt(j)) {
                        dp[k][i] = Math.max(dp[k][i], dp[k][j] + 1);
                    }
                }
            }
        }

        return 0;
    }
}
