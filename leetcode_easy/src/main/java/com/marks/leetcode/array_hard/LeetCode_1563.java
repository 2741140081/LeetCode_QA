package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1563 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/10 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1563 {

    /**
     * @Description:
     * 几块石子 排成一行 ，每块石子都有一个关联值，关联值为整数，由数组 stoneValue 给出。
     * 游戏中的每一轮：Alice 会将这行石子分成两个 非空行（即，左侧行和右侧行）；
     * Bob 负责计算每一行的值，即此行中所有石子的值的总和。
     * Bob 会丢弃值最大的行，Alice 的得分为剩下那行的值（每轮累加）。
     * 如果两行的值相等，Bob 让 Alice 决定丢弃哪一行。下一轮从剩下的那一行开始。
     * 只 剩下一块石子 时，游戏结束。Alice 的分数最初为 0 。
     * 返回 Alice 能够获得的最大分数 。
     *
     * tips:
     * 1 <= stoneValue.length <= 500
     * 1 <= stoneValue[i] <= 10^6
     * @param: stoneValue
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int stoneGameV(int[] stoneValue) {
        int result;
        result = method_01(stoneValue);
        return result;
    }

    private int[][] dp;
    private int[] prev;
    /**
     * @Description:
     * 1. 需要快速计算连续区间和, 使用前缀和进行预处理.
     * 2. 分割点的选择, 有两种方案, 选择左侧或者选择右侧, 并且为了使得总分数最大化,
     * 需要选择最大下标来保留最多分数, 此时采用贪心的策略(可能不是最优解, 也没有证明正确性)
     * 3. 由于无法证明贪心的正确性, 所有舍弃, 改用枚举所有的可能性, 通过动态规划来得到最大得分
     * 4. 定义一个 int[][] dp; dp[i][j] 为 区间 [i, j] 可以获取的最大得分, 并且 i == j 时, 返回 0,
     * i < j 时, 假设 k 是[i, j] 的分割点, 并且 sum[i, k] < sum[k, j], 此时 dp[i][j] = dp[i][k] + prev[k] - prev[i],
     * 如果 sum[i, k] == sum[k, j], 此时两个都要取 dp[i][j] = Math.max(dp[i][k] + prev[k] - prev[i], dp[k][j] + prev[j] - prev[k])
     * 5. dp 初始化为 -1, 此时可以进行记忆化功能, 如果 dp[i][j] != -1, return dp[i][j]
     * AC: 261ms/46.93MB
     * 时间复杂度是 每个状态计算是 O(n) * 所有状态是 O(n^2), 总时间复杂度是 O(n^3)
     * @param: stoneValue
     * @return int
     * @author marks
     * @CreateDate: 2026/08/10 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] stoneValue) {
        int n = stoneValue.length;
        dp = new int[n][n];
        prev = new int[n + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1); // 初始化
            prev[i + 1] = prev[i] + stoneValue[i]; // 前缀和
        }
        return divideGetScore(0, n - 1);
    }

    private int divideGetScore(int left, int right) {
        if (left >= right) { // 单个元素或者非法分割
            return 0;
        }
        if (dp[left][right] != -1) { // 返回之前处理过的状态
            return dp[left][right];
        }
        int ans = 0;
        // 将 i 作为分割点
        for (int i = left + 1; i <= right; i++) {
            // 分别计算 [left, i - 1] 和 [i, right] 的sum 值
            int sumLeft = prev[i] - prev[left];
            int sumRight = prev[right + 1] - prev[i];
            if (sumLeft == sumRight) {
                // 需要分别计算两种情况, 因为是自选的方式
                ans = Math.max(ans, divideGetScore(left, i - 1) + sumLeft);
                ans = Math.max(ans, divideGetScore(i, right) + sumRight);
            } else if (sumLeft < sumRight) {
                // 取[left, i - 1]
                ans = Math.max(ans, divideGetScore(left, i - 1) + sumLeft);
            } else {
                // 取 [i, right]
                ans = Math.max(ans, divideGetScore(i, right) + sumRight);
            }
        }
        dp[left][right] = ans;
        return ans;
    }

}
