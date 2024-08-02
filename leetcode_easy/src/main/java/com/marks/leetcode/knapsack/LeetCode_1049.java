package com.marks.leetcode.knapsack;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/2 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1049 {
    /**
     * @Description: [
     * 有一堆石头，用整数数组 stones 表示。其中 stones[i] 表示第 i 块石头的重量。
     * 每一回合，从中选出任意两块石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块 石头。返回此石头 最小的可能重量 。如果没有石头剩下，就返回 0。
     * ]
     * @param stones
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int lastStoneWeightII(int[] stones) {
        int result = 0;
        result = method_01(stones);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：stones = [2,7,4,1,8,1]
     * 输出：1
     * 解释：
     * 组合 2 和 4，得到 2，所以数组转化为 [2,7,1,8,1]，
     * 组合 7 和 8，得到 1，所以数组转化为 [2,1,1,1]，
     * 组合 2 和 1，得到 1，所以数组转化为 [1,1,1]，
     * 组合 1 和 1，得到 0，所以数组转化为 [1]，这就是最优值。
     *
     * 思路: 与LeetCode_416一致, 即将集合分成2个集合, minValue = sum_1 - sum_2;
     *
     * ]
     * @param stones
     * @return int
     * @author marks
     * @CreateDate: 2024/8/2 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] stones) {
        int n = stones.length;
        if (n == 1) {
            return stones[0];
        }
        int sum = Arrays.stream(stones).sum();
        int target = sum / 2; // 子集合的sum值 <= target
        int[][] dp = new int[2][target + 1];
        int curr = 0;
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            int temp = stones[i - 1];
            for (int j = 0; j <= target; j++) {
                dp[curr][j] = dp[pre][j];
                if (j >= temp) {
                    dp[curr][j] = Math.max(dp[pre][j],dp[pre][j - temp] + temp);
                }
            }
        }
        // 获取子集合的最大值
        // 获取子集合的最大值
        int sum_1 = dp[curr][target];
        int sum_2 = sum - sum_1;
        return Math.abs(sum_1 - sum_2);
    }
}
