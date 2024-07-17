package com.marks.leetcode.gridDP;

import java.util.Arrays;

public class LeetCode_LCR_166 {
    /**
     * 现有一个记作二维矩阵 frame 的珠宝架，其中 frame[i][j] 为该位置珠宝的价值。拿取珠宝的规则为：
     * 只能从架子的左上角开始拿珠宝
     * 每次可以移动到右侧或下侧的相邻位置
     * 到达珠宝架子的右下角时，停止拿取
     * 注意：珠宝的价值都是大于 0 的。除非这个架子上没有任何珠宝，比如 frame = [[0]]。
     *
     * E1:
     * 输入: frame = [[1,3,1],[1,5,1],[4,2,1]]
     * 输出: 12
     * 解释: 路径 1→3→5→2→1 可以拿到最高价值的珠宝
     * 如下图所示
     * <1>-<3>- 1
     * 1 - <5>- 1
     * 4 - <2>-<1>
     *
     * @param frame
     * @return
     */
    public int jewelleryValue(int[][] frame) {
        int result = 0;
        result = method_01(frame);
        return result;
    }

    /**
     * 思路:
     * 假设我取第[i][j]位置的珠宝
     * 则取该珠宝之前的路径为
     * max(frame[i-1][j], frame[i][j-1])
     * dp[0][0] = frame[0][0]
     * dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])+frame[i][j]
     * int res = dp[frame.length-1][frame[0].length-1]
     * @param frame
     * @return
     */
    private int method_01(int[][] frame) {
        int row = frame.length;
        int col = frame[0].length;
        int[][] dp = new int[row][col];
        int res = 0;
        dp[0][0] = frame[0][0];
        if (row == 1) {
            return Arrays.stream(frame[0]).sum();
        }
        if (col == 1) {
            for (int i = 0; i < col; i++) {
                res += frame[i][0];
            }
            return res;
        }
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i-1][0] + frame[i][0];
        }

        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i-1] + frame[0][i];
        }

        dp[1][0] = dp[0][0] + frame[1][0];
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + frame[i][j];
            }
        }
        res = dp[row-1][col-1];
        return res;
    }
}
