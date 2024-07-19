package com.marks.leetcode.gridDP;

import java.util.List;

public class LeetCode_120 {
    /**
     * 三角形最小路径和
     * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
     * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，
     * 如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
     * E1:
     * 输入：triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
     * 输出：11
     * 解释：如下面简图所示：
     *    2
     *   3 4
     *  6 5 7
     * 4 1 8 3
     * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
     *
     * tips:
     * 1 <= triangle.length <= 200
     * triangle[0].length == 1
     * triangle[i].length == triangle[i - 1].length + 1
     * -10^4 <= triangle[i][j] <= 10^4
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int result = 0;
//        result = method_01(triangle);
        result = method_02(triangle);
        return result;
    }

    /**
     * triangle
     * 2
     * 3 4
     * 6 5 7
     * 4 1 8 3
     *
     * dp[][]
     * 2
     * 5  6
     * 11 10 13
     * 15 11 18 16
     * @param triangle
     * @return
     */
    private int method_01(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[][] dp = new int[size][triangle.get(size -1).size()];
        dp[0][0] = triangle.get(0).get(0);
        if (size == 1) {
            return dp[0][0];
        }
        for (int i = 1; i < size; i++) {
            dp[i][0] = dp[i-1][0] + triangle.get(i).get(0);
            dp[i][triangle.get(i).size()-1] = dp[i-1][triangle.get(i-1).size()-1] +
                    triangle.get(i).get(triangle.get(i).size()-1);
        }

        for (int i = 1; i < size; i++) {
            for (int j = 1; j < triangle.get(i).size() - 1; j++) {
                dp[i][j] = Math.min(dp[i-1][j-1], dp[i-1][j]) + triangle.get(i).get(j);
            }
        }
        int res = dp[size-1][0];
        for (int i = 0; i < dp[size - 1].length; i++) {
            res = Math.min(res, dp[size-1][i]);
        }
        return res;
    }

    private int method_02(List<List<Integer>> triangle) {
        int size = triangle.size();
        int[] dp = new int[size];
        dp[0] = triangle.get(0).get(0);
        if (size == 1) {
            return dp[0];
        }
        for (int i = 1; i < size; i++) {
            // 必须是从数组由后往前赋值, 否则如果从前往后赋值, 会发生值的覆盖
            dp[i] = dp[i-1] + triangle.get(i).get(i);

            for (int j = i-1; j > 0; j--) {
               dp[j] = Math.min(dp[j-1], dp[j]) + triangle.get(i).get(j);
            }

            dp[0] += triangle.get(i).get(0);
        }

        int res = dp[0];
        for (int i = 0; i < size; i++) {
            res = Math.min(res, dp[i]);
        }
        return res;
    }
}
