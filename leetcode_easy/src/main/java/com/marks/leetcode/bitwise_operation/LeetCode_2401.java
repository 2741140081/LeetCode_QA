package com.marks.leetcode.bitwise_operation;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/25 11:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2401 {

    /**
     * @Description:
     * 给你一个由 正 整数组成的数组 nums 。
     * 如果 nums 的子数组中位于 不同 位置的每对元素按位 与（AND）运算的结果等于 0 ，则称该子数组为 优雅 子数组。
     *
     * 返回 最长 的优雅子数组的长度。
     *
     * 子数组 是数组中的一个 连续 部分。
     *
     * 注意：长度为 1 的子数组始终视作优雅子数组。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/9/25 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestNiceSubarray(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 可以使用或运算, 将子数组的元素进行或运算, 得到 max, max & nums[i] == 0, 证明没有交集, 可以添加到子数组中, 更新 max值, max |= nums[i]
     * 2. 不能简单的重置len 和 max, 而是相当于一个不定长的滑动窗口来移动, 移动时, 删除最左边的元素, 添加最右边的元素, 直到 max & nums[i] == 0
     * AC: 3ms/54.73MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/9/25 11:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int max = 0;
        int ans = 0;
        int len = 0;
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((max & nums[i]) != 0) {
                // 不能单纯的将其重置, 而是需要通过 left 来计算新的 max 和 len, 相当于不定长的滑动窗口
                while ((max & nums[i]) != 0) {
                    max ^= nums[left];
                    left++;
                    len--;
                }
            }
            max |= nums[i];
            len++;
            ans = Math.max(ans, len);
        }

        return ans;
    }

}
