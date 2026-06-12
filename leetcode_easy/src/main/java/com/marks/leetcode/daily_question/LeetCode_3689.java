package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3689 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/9 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3689 {

    /**
     * @Description:
     * 给定一个长度为 n 的整数数组 nums 和一个整数 k。
     * 你必须从 nums 中选择 恰好 k 个非空子数组 nums[l..r]。子数组可以重叠，同一个子数组（相同的 l 和 r）可以 被选择超过一次。
     * 子数组 nums[l..r] 的 值 定义为：max(nums[l..r]) - min(nums[l..r])。
     * 总值 是所有被选子数组的 值 之和。
     * 返回你能实现的 最大 可能总值。
     * 子数组 是数组中连续的 非空 元素序列。
     * tips:
     * 1 <= n == nums.length <= 5 * 10^4
     * 0 <= nums[i] <= 10^9
     * 1 <= k <= 10^5
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/09 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxTotalValue(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 遍历数组, 找到最大值和最小值
     * 2. long abs = Math.abs(max - min); ans = abs * k;
     * AC: 2ms/61MB
     * @param: nums
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/09 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            max = Math.max(max, num);
            min = Math.min(min, num);
        }
        long abs = Math.abs(max - min);

        return abs * k;
    }

}
