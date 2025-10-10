package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_55 {

    /**
     * @Description:
     * 给你一个非负整数数组 nums ，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 判断你是否能够到达最后一个下标，如果可以，返回 true ；否则，返回 false 。
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/10 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canJump(int[] nums) {
        boolean result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 与LeetCode_45.java 类似, 都是使用贪心的思想来解决
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2025/10/10 15:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int n = nums.length;
        int end = 0;
        int maxPos = 0;
        for (int i = 0; i < n; i++) {
            maxPos = Math.max(maxPos, i + nums[i]);
            if (i <= end) {
                end = maxPos;
            }
        }
        return end >= n - 1;
    }

}
