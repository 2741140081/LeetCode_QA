package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1039 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 16:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1039 {

    /**
     * @Description:
     * 你有一个凸的 n 边形，其每个顶点都有一个整数值。给定一个整数数组 values ，其中 values[i] 是按 顺时针顺序 第 i 个顶点的值。
     * 假设将多边形 剖分 为 n - 2 个三角形。对于每个三角形，该三角形的值是顶点标记的乘积，三角剖分的分数是进行三角剖分后所有 n - 2 个三角形的值之和。
     * 返回 多边形进行三角剖分后可以得到的最低分 。
     * @param: values
     * @return int
     * @author marks
     * @CreateDate: 2026/03/24 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minScoreTriangulation(int[] values) {
        int result;
        result = method_01(values);
        return result;
    }

    /**
     * @Description:
     * 1. 这个好像类似于之前的打气球的题(LeetCode_312), 之前求最大乘积, 现在求最小乘积
     * 2. 好像题目不太一样, 先用记忆化递归, 不需要添加额外的数字, 两个数无法组成三角形,
     * AC: 3ms/42.38MB
     * @param: values
     * @return int
     * @author marks
     * @CreateDate: 2026/03/24 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] val;
    private int[][] memo;
    private int INF;
    private int method_01(int[] values) {
        INF = Integer.MAX_VALUE / 2;
        int n = values.length;
        val = values;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], INF);
        }
        dfsMinScore(0, n - 1);
        return memo[0][n - 1];
    }

    private int dfsMinScore(int left, int right) {
        if (left >= right - 1) {
            // 节点数量小于3
            return 0;
        }
        if (memo[left][right] != INF) {
            return memo[left][right];
        }
        for (int i = left + 1; i < right; i++) {
            int score = dfsMinScore(left, i) + dfsMinScore(i, right) + val[left] * val[i] * val[right];
            memo[left][right] = Math.min(memo[left][right], score);
        }

        return memo[left][right];
    }

}
