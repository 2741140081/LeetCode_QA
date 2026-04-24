package com.marks.leetcode.DP_II;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3366 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/22 15:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3366 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和三个整数 k、op1 和 op2。
     *
     * 你可以对 nums 执行以下操作：
     *
     * 操作 1：选择一个下标 i，将 nums[i] 除以 2，并 向上取整 到最接近的整数。你最多可以执行此操作 op1 次，并且每个下标最多只能执行一次。
     * 操作 2：选择一个下标 i，仅当 nums[i] 大于或等于 k 时，从 nums[i] 中减去 k。你最多可以执行此操作 op2 次，并且每个下标最多只能执行一次。
     * 注意： 两种操作可以应用于同一下标，但每种操作最多只能应用一次。
     *
     * 返回在执行任意次数的操作后，nums 中所有元素的 最小 可能 和 。
     *
     * tips:
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 10^5
     * 0 <= k <= 10^5
     * 0 <= op1, op2 <= nums.length
     * @param: nums
     * @param: k
     * @param: op1
     * @param: op2
     * @return int
     * @author marks
     * @CreateDate: 2026/04/22 15:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minArraySum(int[] nums, int k, int op1, int op2) {
        int result;
        result = method_01(nums, k, op1, op2);
        return result;
    }


    /**
     * @Description:
     * 1. 动态规划: int[][][] dp; dp[i][j][x]; 其中 i 表示前 i 个元素, j 表示 op1 执行的次数, x 表示 op2 执行的次数, value 为前 i 个元素的最小和。
     * 2. 初始化, INF = Integer.MAX_VALUE / 2; dp[0][0][0] = nums[0]; dp[0][1][0] = (nums[0] + 1) /2; dp[0][0][1] = nums[0] - k;
     * dp[0][1][1] 需要取两者的较小值 f1 = (nums[0] + 1) / 2; 需要判断 f1 是否小于 k 吗? 另外比较两种情况, 那种结果小, 先除以2然后减去 k, 还是先减去 k 然后除以2
     * 3. (num + 1) / 2 - k 与 (num - k + 1) / 2, 经过判断 n - 2k <= n - k 先执行除法在执行减法得到的结果更小
     * 4. dp[0][1][1] = (num[0] + 1) / 2 - k, 并且在执行操作2时, 需要判断 nums[i] 与 k 的关系, 当且 nums[i] >= k 才能执行操作2
     * 5. 转移方程: dp[i][j][x] = Math.min(dp[i - 1][j][x], dp[i - 1][j - 1][x - 1]) + nums[i];
     * AC: 84ms/49.87MB
     * @param: nums
     * @param: k
     * @param: op1
     * @param: op2
     * @return int
     * @author marks
     * @CreateDate: 2026/04/22 15:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_01(int[] nums, int k, int op1, int op2) {
        int INF = Integer.MAX_VALUE / 2;
        int n = nums.length;
        int[][][] dp = new int[n][op1 + 1][op2 + 1];
        // 初始化
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= op1; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        dp[0][0][0] = nums[0];
        int value = (nums[0] + 1) / 2;
        if (op1 > 0) {
            dp[0][1][0] = value;
        }
        if (nums[0] >= k && op2 > 0) {
            dp[0][0][1] = nums[0] - k;
            if (op1 > 0) {
                dp[0][1][1] = (nums[0] + 1 - k) / 2;
            }
        }
        if (value >= k && op2 > 0 && op1 > 0) {
            dp[0][1][1] = Math.min(dp[0][1][1], value - k);
        }
        // 执行动态规划
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= op1; j++) {
                for (int x = 0; x <= op2; x++) {
                    value = (nums[i] + 1) / 2;
                    // 对于当前 i, 不执行操作1和操作2
                    dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j][x] + nums[i]);
                    // 对于当前 i, 执行操作1, 即除以2
                    if (j > 0) {
                        dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j - 1][x] + value);
                    }
                    // 对于当前 i, 执行操作2, 即减去k
                    if (x > 0 && nums[i] >= k) {
                        dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j][x - 1] + nums[i] - k);
                    }
                    // 对于当前 i, 执行操作1和操作2
                    if (j > 0 && x > 0 && nums[i] >= k) {
                        if (value >= k) {
                            dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j - 1][x - 1] + value - k);
                        } else {
                            dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j - 1][x - 1] + (nums[i] - k + 1) / 2);
                        }
                    }
                }
            }
        }
        // 找到最小值
        int result = Integer.MAX_VALUE;
        for (int j = 0; j <= op1; j++) {
            for (int x = 0; x <= op2; x++) {
                result = Math.min(result, dp[n - 1][j][x]);
            }
        }

        return result;
    }

}
