package com.marks.leetcode.knapsack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/2 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_474 {
    /**
     * @Description: [
     * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
     * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
     * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。]
     * @param strs
     * @param m
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述
     * tips:
     * 1 <= strs.length <= 600
     * 1 <= strs[i].length <= 100
     * strs[i] 仅由 '0' 和 '1' 组成
     * 1 <= m, n <= 100
     * ]
     */
    public int findMaxForm(String[] strs, int m, int n) {
        int result = 0;
        result = method_01(strs, m, n);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
     * 输出：4
     * 解释：最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"} ，因此答案是 4 。
     * 其他满足题意但较小的子集包括 {"0001","1"} 和 {"10","1","0"} 。{"111001"} 不满足题意，因为它含 4 个 1 ，大于 n 的值 3 。
     * dp[2][j][k] j表示0的数量, k表示1的数量
     * 可以将strs转为二维数组存放0， 1的数量
     * 不取i dp[i][j][k] = dp[i-1][j][k]
     * 取i dp[i][j][k] = dp[i-1][j-x][k-y] + 1
     *
     * ]
     * @param strs
     * @param m
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] strs, int m, int n) {
        int len = strs.length;
        int[][] nums = new int[len][2];
        for (int i = 0; i < strs.length; i++) {
            int tempLength = strs[i].length();
            int zeroCount = tempLength - strs[i].replaceAll("0", "").length();
            int oneCount = tempLength - zeroCount;
            nums[i][0] = zeroCount;
            nums[i][1] = oneCount;
        }
        int[][][] dp = new int[2][m+1][n+1];
        dp[0][0][0] = 0;
        int curr = 0;
        for (int i = 1; i <= len; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int zeroCount = nums[i-1][0];
            int oneCount = nums[i-1][1];
            for (int j = 0; j <= m; j++) {
                for (int k = 0; k <= n; k++) {
                    dp[curr][j][k] = dp[pre][j][k];
                    if (j >= zeroCount && k >= oneCount) {
                        dp[curr][j][k] = Math.max(dp[pre][j][k], dp[pre][j-zeroCount][k-oneCount] + 1);
                    }
                }
            }
        }

        return dp[curr][m][n];
    }

}
