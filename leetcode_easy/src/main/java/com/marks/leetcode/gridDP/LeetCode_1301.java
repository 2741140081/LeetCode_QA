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
        long[][] dpMaxValue = new long[m][m];
        long[][] dpSumPlan = new long[m][m];

        // 初始化dpMaxValue, dpSumPlan
        dpMaxValue[m-1][m-1] = 0;
        dpSumPlan[m-1][m-1] = 1;
        char[][] charArray = new char[m][m];

        for (int i = 0; i < board.size(); i++) {
            charArray[i] = board.get(i).toCharArray();
        }

        if (m == 2) {
            if (charArray[0][1] == 'X') {
                if (charArray[1][0] == 'X') {
                    return new int[]{0, 1};
                }else {
                    int source = Integer.parseInt(String.valueOf(charArray[1][0]));
                    return new int[]{source, 1};
                }
            }else {
                if (charArray[1][0] == 'X') {
                    int source = Integer.parseInt(String.valueOf(charArray[1][0]));
                    return new int[]{source, 1};
                }else {
                    int source1 = Integer.parseInt(String.valueOf(charArray[1][0]));
                    int source2 = Integer.parseInt(String.valueOf(charArray[1][0]));
                    if (source1 == source2) {
                        return new int[]{source1, 2};
                    }else {
                        return new int[]{Math.max(source1, source2), 1};
                    }

                }
            }
        }

        for (int i = m - 2; i >= 0; i--) {
            // 行
            if (charArray[m-1][i] != 'X' && dpMaxValue[m-1][i+1] != -1) {
                dpMaxValue[m-1][i] = dpMaxValue[m-1][i+1] +
                        Integer.parseInt(String.valueOf(charArray[m-1][i]));
                dpSumPlan[m-1][i] = 1;
            }else {
                dpMaxValue[m-1][i] = -1;
                dpSumPlan[m-1][i] = -1;
            }

            // 列
            if (charArray[i][m-1] != 'X' && dpMaxValue[i+1][m-1] != -1) {
                dpMaxValue[i][m-1] = dpMaxValue[i+1][m-1] +
                        Integer.parseInt(String.valueOf(charArray[i][m-1]));
                dpSumPlan[i][m-1] = 1;
            }else {
                dpMaxValue[i][m-1] = -1;
                dpSumPlan[i][m-1] = -1;
            }

        }
        // 先处理最大值
        for (int i = m-2; i >= 0; i--) {
            for (int j = m-2; j >= 0; j--) {
                // 先判断当前节点是否是 'X'
                if (i ==0 && j==0) {
                    continue;
                }
                boolean flag = dpMaxValue[i+1][j] != -1 || dpMaxValue[i+1][j+1] != -1 || dpMaxValue[i][j+1] != -1;
                if (charArray[i][j] != 'X' && flag) {
                    dpMaxValue[i][j] = Math.max(dpMaxValue[i+1][j],
                            Math.max(dpMaxValue[i+1][j+1], dpMaxValue[i][j+1])) +
                            Integer.parseInt(String.valueOf(charArray[i][j]));
                }else {
                    dpMaxValue[i][j] = -1;
                }
            }
        }
        // 获取 'S' 是否可以到达, 以及最大得分值
        if (dpMaxValue[0][1] != -1 || dpMaxValue[1][1] != -1 || dpMaxValue[1][0] != -1) {
            dpMaxValue[0][0] = Math.max(dpMaxValue[0][1], Math.max(dpMaxValue[1][1], dpMaxValue[1][0]));
        }else {
            dpMaxValue[0][0] = 0;
        }

        // 我已获取得分的 dpMaxValue
        // 我该如何利用这个来处理
        // 先判断不可达的情况
        int[] res = new int[2];
        res[0] = (int)dpMaxValue[0][0] % MOD;
        if (res[0] == 0) {
            res[1] = 0;
            return res;
        }
        // 可达情况
        // 判断到达 dpMaxValue[0][0]
        for (int i = m-2; i >= 0; i--) {
            for (int j = m-2; j >= 0; j--) {
                if (i == 0 && j == 0) {
                    continue;
                }
                dpSumPlan[i][j] = getCountByMaxValue(dpSumPlan, dpMaxValue, charArray, i, j) % MOD;
            }
        }
        // 获取dpSumPlan[0][0]
        if (dpMaxValue[0][1] != -1 && dpMaxValue[0][1] == dpMaxValue[0][0]) {
            dpSumPlan[0][0] += dpSumPlan[0][1];
        }
        if (dpMaxValue[1][1] != -1 && dpMaxValue[1][1] == dpMaxValue[0][0]) {
            dpSumPlan[0][0] += dpSumPlan[1][1];
        }
        if (dpMaxValue[1][0] != -1 && dpMaxValue[1][0] == dpMaxValue[0][0]) {
            dpSumPlan[0][0] += dpSumPlan[1][0];
        }
        res[1] = (int) dpSumPlan[0][0];
        return res;
    }

    private long getCountByMaxValue(long[][] dpSumPlan, long[][] dpMaxValue, char[][] charArray, int i, int j) {
        if (dpMaxValue[i][j] == -1) {
            return -1;
        }
        long count = 0;
        long value = dpMaxValue[i][j] - Integer.parseInt(String.valueOf(charArray[i][j]));

        if (value == dpMaxValue[i+1][j]) {
            count += dpSumPlan[i+1][j];
        }
        if (value == dpMaxValue[i+1][j+1]) {
            count += dpSumPlan[i+1][j+1];
        }
        if (value == dpMaxValue[i][j+1]) {
            count += dpSumPlan[i][j+1];
        }
        return count;
    }
}
