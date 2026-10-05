package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3976 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/17 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3976 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个正整数 k。
     * 你必须选择 nums 的一个 子数组 并执行以下操作之一：
     * 将所选子数组中的每个数字乘以 k。
     * 将所选子数组中的每个数字除以 k。
     * 当正数除以 k 时，除法结果 向下取整。
     * 当负数除以 k 时，除法结果 向上取整。
     * 返回结果数组中 非空 子数组的 最大 可能和。
     * 注意，用于执行操作的 子数组 与用于求和的 子数组 可以是 不同 的。
     * 子数组 是数组中一段连续的 非空 元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * -105 <= nums[i] <= 10^5
     * 1 <= k <= 10^5
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/09/17 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxSubarraySum(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 存在3种状态, 不进行任何操作0, 正在进行乘法/除法 1, 已经进行过了乘法/除法 2
     * dp[i][2] = Math.max(dp[i - 1][0], dp[i - 1][2]) + nums[i]
     * dp[i][1] = Math.max(dp[i - 1][0], dp[i - 1][1], 0) + nums[i] ops k; (ops 表示进行乘法或者除法)
     * dp[i][0] = Math.max(dp[i - 1][0], 0) + nums[i]
     *
     * AC: 71ms/148.7MB
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/09/17 17:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] nums, int k) {
        return Math.max(solve(nums, k, true), solve(nums, k, false));
    }

    private long solve(int[] nums, int k, boolean isMul) {
        int n = nums.length;
        // f[i+1][0] 表示右端点为 i 的最大子数组和，且不修改任何元素
        // f[i+1][1] 表示右端点为 i 的最大子数组和，且修改了 nums[i]
        // f[i+1][2] 表示右端点为 i 的最大子数组和，且在 nums[i] 的左边发生了修改（没有修改 nums[i]）
        long[][] f = new long[n + 1][3];
        long res = Long.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            long x = nums[i];
            long y = isMul ? x * k : x / k;
            // 不修改 x，和 f[i][0] 拼起来，或者 x 是子数组的第一个数
            f[i + 1][0] = Math.max(f[i][0], 0) + x;
            // 修改 x，和 f[i][0] 或者 f[i][1] 拼起来，或者 y 是子数组的第一个数
            f[i + 1][1] = Math.max(Math.max(f[i][0], f[i][1]), 0) + y;
            // 不修改 x，和 f[i][1] 或者 f[i][2] 拼起来
            f[i + 1][2] = Math.max(f[i][1], f[i][2]) + x;
            // 枚举子数组的右端点为 i
            res = Math.max(res, Math.max(f[i + 1][1], f[i + 1][2]));
        }

        return res;
    }

    /**
     * @Description:
     * 1. 分类讨论, 由于乘法和除法只能选择一种, 那么可以分类进行讨论并且求和
     * 2. 使用乘法对子数组进行操作, 并且得到求和子数组的最大值。由于子数组需要连续, 所以可以使用动态规划
     * 状态转移方程, long[][] dp = new long[nums.length][2]; (0 表示不选择, 1 表示选择)
     * 3. 感觉不对, 因为只有一个子数组, 还是用滑动窗口, 即求子数组的最大和
     * 4. 经过思考, 乘法操作, 即为找到子数组的最大和, 假设原本的总和是 sum, 并且最大子数组和是 maxSum, 那么
     * 经过乘法操作后 ans = sum + maxSum * (k - 1)
     * 5. 除法应该也是类似的, 需要找到一个最小和, 假设原本的总和是 sum, 并且最小子数组和是 minSum, 那么
     * 经过除法操作后 ans = sum - minSum * (k - 1)
     * 6. 但是应该有一个需要关注点在于 负数的除法应该是向上取整, 即假设两个数 -3 和 -6, k = 4,那么 -3 / 4 = 0, -6 / 4 = -1
     * 总和是 -1, 但是 -6 + -3 = -9 / 4 = -2, 这回导致如何先计算最小总和会导致错误
     * 7. 除法是需要减少更多来实现最后和更大, ans = Math.max(ans, sum - minSub) minSub 的定义为 sub_i = nums[i] - nums[i] / k,
     * 现在需要求 sub_i 的最小值
     * WA: 717/721, 除法存在错误, 即某些正数可以不进行除法操作, 导致结果偏小
     * 8. 感觉还是需要用动态规划来解决
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/09/17 16:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        // 乘法处理
        long currMax = nums[0];
        long globalMax = nums[0];
        for (int i = 1; i < n; i++) {
            currMax = Math.max(nums[i], currMax + nums[i]);
            globalMax = Math.max(globalMax, currMax);
        }
        long ans = globalMax * k;

        // 除法处理
        int[] sub = new int[n]; // 创建一个数组用于处理除法操作
        for (int i = 0; i < n; i++) {
            if (nums[i] >= 0) { // 向下取整
                sub[i] = nums[i] / k;
            } else { // 向上取整
//                sub[i] = (nums[i] + k - 1) / k; // 错误写法, 只能用于两个正数
                sub[i] = (int) Math.ceil((double) nums[i] / k);
            }
        }
        long currMin = sub[0];
        long globalMin = sub[0];

        for (int i = 1; i < n; i++) {
            currMin = Math.max(sub[i], currMin + sub[i]);
            globalMin = Math.max(globalMin, currMin);
        }
        ans = Math.max(ans, globalMin);

        return ans;
    }

}
