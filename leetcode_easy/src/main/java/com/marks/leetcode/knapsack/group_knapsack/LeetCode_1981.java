package com.marks.leetcode.knapsack.group_knapsack;

import java.util.Arrays;

/**
 * <p>项目名称: 分组背包 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 17:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1981 {
    /**
     * @Description: [功能描述:
     * 给你一个大小为 m x n 的整数矩阵 mat 和一个整数 target 。
     * 从矩阵的 每一行 中选择一个整数，你的目标是 最小化 所有选中元素之 和 与目标值 target 的 绝对差 。
     * 返回 最小的绝对差 。
     * a 和 b 两数字的 绝对差 是 a - b 的绝对值。]
     * @param mat
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimizeTheDifference(int[][] mat, int target) {
        int result = 0;
//        result = method_01(mat, target);
        result = method_02(mat, target);
        return result;
    }

    private int method_02(int[][] mat, int target) {
        int n = mat.length;
        int m = 4910;   //每个数最大为70，最多70行
        int[][] f = new int[n][m];

        for (int j : mat[0]) f[0][j] = 1;    //数组第一行里的每个数都取得到

        for (int i = 1; i < n; i++) {   //物品组
            for (int j = 0; j < m; j++) {   //体积
                for (int k : mat[i])  {   //决策
                    if (j - k >= 0 && f[i - 1][j - k] > 0)
                        f[i][j] = f[i - 1][j - k];
                }
            }
        }

        int res = 0x3f3f3f3f;
        for (int j = 0; j < m; j++) {
            if (f[n - 1][j] > 0)
                res = Math.min(res, Math.abs(j - target));
        }
        return res;
    }

    /**
     * @Description: [功能描述: E1
     * 输入：mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13
     * 输出：0
     * 解释：一种可能的最优选择方案是：
     * - 第一行选出 1
     * - 第二行选出 5
     * - 第三行选出 7
     * 所选元素的和是 13 ，等于目标值，所以绝对差是 0 。
     * ]
     * @param mat
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/8/7 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] mat, int target) {
        int n = mat.length;
        int m = 4910; // 每个数最大为70, 最多70行

        int[][] dp = new int[2][m]; // 存放前n个数的和
        for (int i : mat[0]) {
            dp[0][i] = 1; // 数组第一行的每一个数都可以取到
        }
        int curr = 0;
        for (int i = 1; i < n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            for (int j = 0; j < m; j++) {
                dp[curr][j] = 0;
                for (int k : mat[i]) {
                    if (j >= k && dp[pre][j - k] > 0) {
                        dp[curr][j] = dp[pre][j - k];
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int j = 0; j < m; j++) {
            if (dp[curr][j] > 0) {
                res = Math.min(res, Math.abs(j - target));
            }
        }
        return res;
    }
}
