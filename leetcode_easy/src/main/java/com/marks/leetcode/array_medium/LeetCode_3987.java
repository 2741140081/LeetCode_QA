package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3987 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/25 16:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3987 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k。
     * 初始时，你拥有 k 单位的资源。
     * 你必须从左到右依次处理 nums 中的元素。处理第 i 个元素需要消耗 nums[i] 单位的资源。
     * 如果当前可用资源少于 nums[i]，你可以执行一次操作，使可用资源增加 k。k 的值固定不变。
     * 第一次执行该操作的成本为 1，第二次的成本为 2，依此类推。
     * 处理完第 i 个元素后，可用资源会减少 nums[i]。
     * 返回处理完所有元素所需的 最小总成本。由于答案可能很大，请返回其对 10^9 + 7 取模后的结果。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 1 <= k <= 10^9
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/25 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumCost(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 直接模拟, long curr, 记录当前剩余的资源, long ans 记录成本
     * 2. 对于 nums[i], 如果 nums[i] <= curr, 则 curr -= nums[i]
     * 3. 如果 nums[i] > curr, long sub = nums[i] - curr, 需要提高的次数, long currCost
     * long time = (sub + (k - 1)) / k; 11 -> 4 14 % 4 = 3, 此时的总花费是 long sum = (currCost + (currCost + time - 1)) * time / 2,
     * 然后更新 currCost += time;
     * AC: 2ms/133.9MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/08/25 16:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        final int MOD = 1_000_000_007;
        long total = 0;
        for (int x : nums) {
            total += x;
        }
        long sum = (total - 1) / k % MOD;
        return (int) (sum * (sum + 1) / 2 % MOD);
    }

}
