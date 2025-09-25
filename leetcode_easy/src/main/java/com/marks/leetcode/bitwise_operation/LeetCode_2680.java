package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/25 14:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2680 {

    /**
     * @Description:
     * 给你一个下标从 0 开始长度为 n 的整数数组 nums 和一个整数 k 。每一次操作中，你可以选择一个数并将它乘 2 。
     *
     * 你最多可以进行 k 次操作，请你返回 nums[0] | nums[1] | ... | nums[n - 1] 的最大值。
     *
     * a | b 表示两个整数 a 和 b 的 按位或 运算。
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/9/25 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumOr(int[] nums, int k) {
        long result = 0;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [12,9], k = 1
     * 输出：30
     * 1100 | 1001 = 1101 << 1 = 11010 = 16 + 8 + 2 = 26
     * 1100 << 1 = 11000 | 1001 = 11001 = 16 + 8 + 1 = 25
     * 1001 << 1 = 10010 | 1100 = 11110 = 30
     * 1. 前缀和 与 后缀和, ans = Math.max(ans, 前缀 | (nums[i] << k) | 后缀)
     * AC: 3ms/54.33MB
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/9/25 14:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        int[] suffix = new int[n + 1]; // 后缀或
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = nums[i] | suffix[i + 1];
        }
        long ans = 0;
        int prev = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, prev | ((long) nums[i] << k) | suffix[i + 1]);
            prev = prev | nums[i]; // 前缀或
        }

        return ans;
    }

}
