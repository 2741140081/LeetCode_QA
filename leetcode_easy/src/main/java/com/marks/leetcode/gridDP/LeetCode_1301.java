package com.marks.leetcode.gridDP;

import java.util.List;

public class LeetCode_1301 {
    /**
     * 给你一个正方形字符数组 board ，你从数组最右下方的字符 'S' 出发。
     * 你的目标是到达数组最左上角的字符 'E' ，数组剩余的部分为数字字符 1, 2, ..., 9 或者障碍 'X'。
     * 在每一步移动中，你可以向上、向左或者左上方移动，可以移动的前提是到达的格子没有障碍。
     * 一条路径的 「得分」 定义为：路径上所有数字的和。
     * 请你返回一个列表，包含两个整数：第一个整数是 「得分」 的最大值，第二个整数是得到最大得分的方案数，请把结果对 10^9 + 7 取余。
     * 如果没有任何路径可以到达终点，请返回 [0, 0] 。
     *
     * tips:
     * 2 <= board.length == board[i].length <= 100
     * @param board
     * @return
     */
    public int[] pathsWithMaxScore(List<String> board) {
        int[] result;
        result = method_01(board);
        return result;
    }

    /**
     * E1:
     * 输入：board = ["E23","2X2","12S"]
     * 输出：[7,1]
     * <p>
     * E2:
     * 输入：board = ["E12","1X1","21S"]
     * 输出：[4,2]
     * <p>
     * E3:
     * 输入：board = ["E11","XXX","11S"]
     * 输出：[0,0]
     * <p>
     * 思路:
     * dp[][]
     * 1. 判断是否可达
     * 2. 求得可达到的最大值
     * 3. 最大值的前提下, 获取方案数
     * <p>
     * 先做1,2
     * 前提条件: board[i][j] != 'X'
     * dp[i][j] = Math.max(dp[i-1][j], dp[i-1][j-1], dp[i][j-1])
     * 可以初始化dp[m-1][i] 和 dp[i][n-1]
     * <p>
     * 初始化
     * dp[m-1][n-1] = 0;
     * <p>
     * if board[m-1][i] != 'X'
     * dp[m-1][i] = dp[m-1][i-1] + board[m-1][i]
     * else dp[m-1][i] = 0;
     * <p>
     * dp[i][n-1] = dp[i-1][n-1] + board[i][n-1]
     * <p>
     * 对于3, 需要多一个二维数组存放方案数
     * dpNum[m-1][n-1] = 1
     * 右边界和下方边界的值初始化都是1
     *
     * @param board
     * @return
     */
    private int[] method_01(List<String> board) {
        final int MOD = 1000000007;
        // 数组是正方形
        int m = board.size();

        // dpMaxValue 存放最大值, dpSumPlan 存放方案数
        int[][] dpMaxValue = new int[m][m];
        int[][] dpSumPlan = new int[m][m];

        // 初始化dpMaxValue, dpSumPlan
        dpMaxValue[m-1][m-1] = 0;
        dpSumPlan[m-1][m-1] = 1;
        char[][] charArray = new char[m][m];

        for (int i = 0; i < board.size(); i++) {
            charArray[i] = board.get(i).toCharArray();
        }


        for (int i = m - 2; i >= 0; i--) {
            // 行
            if (charArray[m-1][i] != 'X' && dpMaxValue[m-1][i+1] != -1) {
                dpMaxValue[m-1][i] = dpMaxValue[m-1][i+1] + Integer.parseInt(String.valueOf(charArray[m-1][i]));
                dpSumPlan[m-1][i] = 1;
            }else {
                dpMaxValue[m-1][i] = -1;
                dpSumPlan[m-1][i] = -1;
            }

            // 列
            if (charArray[i][m-1] != 'X' && dpMaxValue[i+1][m-1] != -1) {
                dpMaxValue[i][m-1] = dpMaxValue[i+1][m-1] + Integer.parseInt(String.valueOf(charArray[i][m-1]));
                dpSumPlan[i][m-1] = 1;
            }else {
                dpMaxValue[i][m-1] = -1;
                dpSumPlan[i][m-1] = -1;
            }

        }
        // 先处理最大值




        return new int[2];
    }
}
