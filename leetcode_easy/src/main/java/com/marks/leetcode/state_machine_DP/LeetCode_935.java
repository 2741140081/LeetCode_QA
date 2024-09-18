package com.marks.leetcode.state_machine_DP;

import java.util.Arrays;

/**
 * <p>项目名称: 骑士拨号器 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/14 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_935 {

    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 象棋骑士有一个独特的移动方式，它可以垂直移动两个方格，水平移动一个方格，或者水平移动两个方格，垂直移动一个方格(两者都形成一个 L 的形状)。
     *
     * 象棋骑士可能的移动方式如下图所示:
     *
     * 我们有一个象棋骑士和一个电话垫，如下所示，骑士只能站在一个数字单元格上(即蓝色单元格)。
     *
     * 给定一个整数 n，返回我们可以拨多少个长度为 n 的不同电话号码。
     *
     * 你可以将骑士放置在任何数字单元格上，然后你应该执行 n - 1 次移动来获得长度为 n 的号码。所有的跳跃应该是有效的骑士跳跃。
     *
     * 因为答案可能很大，所以输出答案模 10^9 + 7.
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/14 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int knightDialer(int n) {
        int result = 0;
        result = method_01(n);
//        result = method_02(n);
        return result;
    }

    /**
     * @Description: [基于method_01, 使用空间优化复杂度
     * 由于dp[i] 只与dp[i - 1] 存在关联, 所以可以使用空间优化(这里我们使用滚动数组优化)
     *
     * AC:19ms/39.55MB
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/18 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n) {
        int[][] moves = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9},
                {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}};
        int[][] dp = new int[2][10];
        Arrays.fill(dp[0], 1);
        int curr = 0;
        for (int i = 1; i < n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            Arrays.fill(dp[curr], 0); // 清除上一次数据
            for (int j = 0; j < 10; j++) {
                for (int k : moves[j]) {
                    dp[curr][k] = (dp[curr][k] + dp[pre][j]) % MOD;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 10; i++) {
            ans = (ans + dp[curr][i]) % MOD;
        }
        return ans;
    }

    /**
     * @Description: [
     * 查看官方题解后
     * 对与每一个数字{0, 9}, 他们的下一个数字的可能性是确定的,
     * eg: 当前骑士站在 "0" 位置处, 那么骑士可以到大的下一个位置为"6", "8"
     * 记录0~9的位置，存放在数组moves中, 值为下一个位置的数值
     * 0: {4, 6},
     * 1: {6, 8},
     * 2: {7, 9},
     * 3: {4, 8},
     * 4: {0, 3, 9},
     * 5: {},
     * 6: {0, 1, 7},
     * 7: {2, 6},
     * 8: {1, 3},
     * 9: {2, 4}
     * ]
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2024/9/14 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[][] moves = {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9},
                {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}};
        int[][] dp = new int[n][10];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k : moves[j]) {
                    dp[i][k] = (dp[i][k] + dp[i - 1][j]) % MOD;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 10; i++) {
            ans = (ans + dp[n - 1][i]) % MOD;
        }
        return ans;
    }

}
