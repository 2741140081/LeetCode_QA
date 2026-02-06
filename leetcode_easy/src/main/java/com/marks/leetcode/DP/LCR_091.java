package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_091 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 10:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_091 {


    /**
     * @Description:
     * 假如有一排房子，共 n 个，每个房子可以被粉刷成红色、蓝色或者绿色这三种颜色中的一种，你需要粉刷所有的房子并且使其相邻的两个房子颜色不能相同。
     * 当然，因为市场上不同颜色油漆的价格不同，所以房子粉刷成不同颜色的花费成本也是不同的。每个房子粉刷成不同颜色的花费是以一个 n x 3 的正整数矩阵 costs 来表示的。
     * 例如，costs[0][0] 表示第 0 号房子粉刷成红色的成本花费；costs[1][2] 表示第 1 号房子粉刷成绿色的花费，以此类推。
     * 请计算出粉刷完所有房子最少的花费成本。
     * tips:
     * costs.length == n
     * costs[i].length == 3
     * 1 <= n <= 100
     * 1 <= costs[i][j] <= 20
     * @param: costs
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int[][] costs) {
        int result;
        result = method_01(costs);
        result = method_02(costs);
        return result;
    }

    /**
     * @Description:
     * 滚动数组优化空间复杂度
     * AC: 1ms/44.07MB
     * @param: costs
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] costs) {
        int n = costs.length;
        int[][] dp = new int[2][3];
        // 初始化
        Arrays.fill(dp[1], Integer.MAX_VALUE);
        // 使用滚动数组进行优化空间复杂度
        int curr = 0;
        int prev = 0;
        for (int i = 1; i < n; i++) {
            curr = 1 - curr;
            for (int j = 0; j < 3; j++) {
                dp[curr][j] = Math.min(dp[prev][(j + 1) % 3], dp[prev][(j + 2) % 3]) + costs[i][j];
            }
            // 更新滚动数组
            prev = curr;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            ans = Math.min(ans, dp[curr][i]);
        }
        return ans;
    }


    /**
     * @Description:
     * 1. 经典的动态规划问题, 可以直接看出状态转移方程0, 1, 2 分别表示颜色种类
     * 2. int[][] dp = new int[n + 1][3]; 初始化 dp[i] = Integer.MAX_VALUE; dp[0] = {0, 0, 0}
     * 3. int currCost = costs[i][j]; dp[i + 1][j] = Math.min(dp[i - 1][(j + 1) % 3], dp[i - 1][(j + 2) % 3]) + currCost;
     * AC: 1ms/43.78MB
     *
     * 4. 由于 i 只与 i - 1 有关，所以可以进行空间复杂度的优化, 即将O(n) 优化为 O(1), 见method_02()
     *
     * @param: costs
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] costs) {
        int n = costs.length;
        int[][] dp = new int[n + 1][3];
        for (int i = 1; i < n + 1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                int currCost = costs[i][j];
                dp[i + 1][j] = Math.min(dp[i][(j + 1) % 3], dp[i][(j + 2) % 3]) + currCost;
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            ans = Math.min(ans, dp[n][i]);
        }

        return ans;
    }

}
