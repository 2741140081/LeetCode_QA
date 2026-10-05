package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3524 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/21 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3524 {

    /**
     * @Description:
     * 给你一个由 正 整数组成的数组 nums，以及一个 正 整数 k。
     * 你可以对 nums 执行 一次 操作，该操作中可以移除任意 不重叠 的前缀和后缀，使得 nums 仍然 非空 。
     * 你需要找出 nums 的 x 值，即在执行操作后，剩余元素的 乘积 除以 k 后的 余数 为 x 的操作数量。
     * 返回一个大小为 k 的数组 result，其中 result[x] 表示对于 0 <= x <= k - 1，nums 的 x 值。
     * 数组的 前缀 指从数组起始位置开始到数组中任意位置的一段连续子数组。
     * 数组的 后缀 是指从数组中任意位置开始到数组末尾的一段连续子数组。
     * 子数组 是数组中一段连续的元素序列。
     * 注意，在操作中选择的前缀和后缀可以是 空的 。
     *
     * tips:
     * 1 <= nums[i] <= 10^9
     * 1 <= nums.length <= 10^5
     * 1 <= k <= 5
     * @param: nums
     * @param: k
     * @return long[]
     * @author marks
     * @CreateDate: 2026/09/21 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long[] resultArray(int[] nums, int k) {
        long[] result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }


    /**
     * @Description:
     * 使用滚动数组优化, 降低空间复杂度, 减少由于复杂空间导致数组的寻址耗时
     * AC: 12ms/90.3MB
     * @param: nums
     * @param: k
     * @return long[]
     * @author marks
     * @CreateDate: 2026/09/21 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] method_02(int[] nums, int k) {
        long[] result = new long[k];
        long[] dp = new long[k];  // 初始状态，表示尚未处理任何元素，因此不存在非空子数组

        for (int num : nums) {
            long[] ndp = new long[k];  // 当前层状态（滚动数组）
            int currMod = num % k;
            ndp[currMod]++;
            for (int r = 0; r < k; r++) {
                ndp[(r * currMod) % k] += dp[r];
            }
            dp = ndp;  // 更新状态
            // 累加答案
            for (int r = 0; r < k; r++) {
                result[r] += dp[r];
            }
        }

        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： nums = [1,2,3,4,5], k = 3
     * 1. 假设剩余的元素[i,j] 是一次操作后的剩余元素, 那么 x = (nums[i] * nums[i+1] * ... * nums[j]) % k => nums[i] % k * nums[i+1] % k * ... * nums[j] % k = x
     * 2. 由于 k 是 [1, 5], 所以得到的余数也不会很大,[0 ~ k -1], => [1,2,0,1,2], [1,2,0,0,0] [2,0,0,0],[0,0,0], [1,2], [2] [9, 2, 4]
     * 3. 应该可以用一个 int[k] 大小的数组来统计前 i 个的不同组合的数量 [0 ~ i],[1 ~ i]...[i~i], 假设 i - 1 已经统计完成得到 dp[i - 1][k] 这样一个数组,
     * 然后需要乘以当前的余数, 得到 currMod, dp[i][k_j * currMod] += dp[i - 1][k_j]; 最后返回 dp[n - 1] 即可
     * 4. [1,2,0,1,2] => dp[0][1] = 1, dp[1][2] = 2, dp[2][0] = 3, dp[3][0] = 3 & dp[3][1] = 1, dp[4][0] =
     * 5. 要确定动态转移方程 i = 0 {1}; i = 1 {1,2} {2}; i = 2 {0} {1,2,0} {2,0}; i = 3 {1,2,0,1} {2,0,1} {0,1} {1}
     * AC: 38ms/99.05MB
     * 6. 优化空间复杂度, 由于 i + 1 只与 i 存在关联, 所以采用滚动数组优化空间复杂度
     * @param: nums
     * @param: k
     * @return long[]
     * @author marks
     * @CreateDate: 2026/09/21 10:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] method_01(int[] nums, int k) {
        int n = nums.length;
        long[][] dp = new long[n + 1][k];
        long[] ans = new long[k];
        for (int i = 0; i < n; i++) {
            int currMod = nums[i] % k;
            for (int j = 0; j < k; j++) {
                dp[i + 1][j * currMod % k] += dp[i][j];
            }
            // 添加当前单个元素
            dp[i + 1][currMod] += 1;

            for (int j = 0; j < k; j++) {
                ans[j] += dp[i + 1][j];
            }
        }

        return ans;
    }

}
