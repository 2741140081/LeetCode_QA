package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3891 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/25 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3891 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums。
     * 如果 nums[i] > nums[i - 1] 且 nums[i] > nums[i + 1]，则下标 i (0 < i < n - 1) 是 特殊的 。
     * 你可以执行操作，选择 任意 下标 i 并将 nums[i] 增加 1。
     * 你的目标是：
     * 最大化 特殊 下标的数量。
     * 最小化 达到该 最大值 所需的总 操作 数。
     * 返回所需的 最小 总操作数。
     *
     * tips:
     * 3 <= n <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/08/25 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minIncrease(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 分情况讨论, 当 n 是奇数时, 此时只能选择奇数下标作为特殊点, 计算使得所有奇数下标是特殊的操作数
     * 2. 当 n 是偶数时, 可以选择奇数或者偶数作为特殊下标, 分别计算两者的操作数, 返回最小的即可
     * 3. 偶数情况需要进行讨论, 可以进行组合方式可以时 奇数 + 偶数的形式, 假设 n = 8, 可以选择 1,4,6 作为特殊点, 也可以选择{1,3,5}
     * 4. 如果前面时偶数, 后面时奇数, 还是用8, 这种会造成特殊点会少一个, 不合规, 舍弃
     * AC: 6ms/100.2MB
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/08/25 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        long ans = 0;
        if (n % 2 != 0) {
            // 奇数
            for (int i = 1; i < n - 1; i += 2) {
                long max = Math.max(nums[i - 1], nums[i + 1]);
                long cost = Math.max(max + 1 - nums[i], 0);
                ans += cost;
            }
        } else {
            // 偶数情况, 使用后缀预处理选择偶数下标需要的操作数
            long[] suffix = new long[n + 2];
            for (int i = n - 2; i > 0; i -= 2) {
                long max = Math.max(nums[i - 1], nums[i + 1]);
                long cost = Math.max(max + 1 - nums[i], 0);
                suffix[i] = suffix[i + 2] + cost;
            }
            long sum = 0;
            ans = suffix[2];
            // 处理奇数
            for (int i = 1; i < n - 1; i += 2) {
                long max = Math.max(nums[i - 1], nums[i + 1]);
                long cost = Math.max(max + 1 - nums[i], 0);
                sum += cost;
                ans = Math.min(ans, sum + suffix[i + 3]);
            }

        }
        return ans;
    }

}
