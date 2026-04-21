package com.marks.leetcode.DP_II;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1478 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/17 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1478 {

    /**
     * @Description:
     * 给你一个房屋数组houses 和一个整数 k ，其中 houses[i] 是第 i 栋房子在一条街上的位置，现需要在这条街上安排 k 个邮筒。
     * 请你返回每栋房子与离它最近的邮筒之间的距离的 最小 总和。
     * 答案保证在 32 位有符号整数范围以内。
     *
     * tips:
     * n == houses.length
     * 1 <= n <= 100
     * 1 <= houses[i] <= 10^4
     * 1 <= k <= n
     * 数组 houses 中的整数互不相同。
     * @param: houses
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDistance(int[] houses, int k) {
        int result;
        result = method_01(houses, k);
        return result;
    }

    /**
     * @Description:
     * 1. 这个应该是一种划分型dp
     * 2. 可以知道, 假设只有两个房屋, 那么邮筒在房子之间的任何位置对于总和是不变的, 那么如果扩展的3个房屋, 那么邮筒必然在中间房屋的位置上最小
     * 3. 继续拓展, 假设4间房屋, 显而易见邮筒在中间位置出最小 sum = d0 + d1 + d2 + d3; d0 + d3 = x3 - x0, 和 d1 + d2 = x2 - x1;
     * 只有这样才是最小和
     * 4. 当前只是查看官方题解, need todo
     * AC: 11ms/43.18MB
     * @param: houses
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] houses, int k) {
        int n = houses.length;
        Arrays.sort(houses);

        // 预处理中位数的最小和
        int[][] medsum = new int[n][n];
        for (int i = n - 2; i >= 0; --i) {
            for (int j = i + 1; j < n; ++j) {
                medsum[i][j] = medsum[i + 1][j - 1] + houses[j] - houses[i];
            }
        }

        int[][] f = new int[n][k + 1];
        for (int i = 0; i < n; ++i) {
            Arrays.fill(f[i], Integer.MAX_VALUE / 2);
        }
        for (int i = 0; i < n; ++i) {
            f[i][1] = medsum[0][i];
            for (int j = 2; j <= k && j <= i + 1; ++j) {
                for (int i0 = 0; i0 < i; ++i0) {
                    f[i][j] = Math.min(f[i][j], f[i0][j - 1] + medsum[i0 + 1][i]);
                }
            }
        }

        return f[n - 1][k];
    }

}
