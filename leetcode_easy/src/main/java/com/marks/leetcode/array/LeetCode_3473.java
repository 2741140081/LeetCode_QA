package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3473 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/15 10:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3473 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 k 和 m。
     * 返回数组 nums 中 k 个不重叠子数组的 最大 和，其中每个子数组的长度 至少 为 m。
     * 子数组 是数组中的一个连续序列。
     *
     * tips:
     * 1 <= nums.length <= 2000
     * -10^4 <= nums[i] <= 10^4
     * 1 <= k <= floor(nums.length / m)
     * 1 <= m <= 3
     * @param: nums
     * @param: k
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/07/15 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSum(int[] nums, int k, int m) {
        int result;
        result = method_01(nums, k, m);
        return result;
    }

    /**
     * @Description:
     * 1. 使用动态规划
     * 2. 由于需要计算连续子数组的和, 所以可以使用前缀和进行预处理, O(1) 时间计算区间和
     * todo 待完成
     * @param: nums
     * @param: k
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/07/15 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k, int m) {
        int n = nums.length;
        int[] preSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        return 0;
    }

}
