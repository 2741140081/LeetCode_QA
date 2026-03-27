package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3840 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/26 17:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3840 {

    /**
     * @Description:
     * 你是一名专业小偷，计划偷窃沿街的房屋。每间房屋都藏有一定的现金，并由带有颜色代码的安全系统保护。
     * 给你两个长度为 n 的整数数组 nums 和 colors，其中 nums[i] 是第 i 间房屋中的金额，而 colors[i] 是该房屋的颜色代码。
     * 如果两间 相邻 的房屋具有 相同 的颜色代码，则你 不能同时偷窃 它们。
     * 返回你能偷窃到的 最大 金额。
     *
     * tips:
     * 1 <= n == nums.length == colors.length <= 10^5
     * 1 <= nums[i], colors[i] <= 10^5
     * @param: nums
     * @param: colors
     * @return long
     * @author marks
     * @CreateDate: 2026/03/26 17:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long rob(int[] nums, int[] colors) {
        long result;
        result = method_01(nums, colors);
        return result;

    }

    /**
     * @Description:
     * 1. 动态规划, 但是需要记录前面一间房屋的颜色, int prevColor = 0, 初始值为0
     * 2. 对于 第 i 间房屋, 选中偷或者不偷 int[][][] dp = new int[n][n][2]; 0表示不偷当前房屋, 1表示偷当前房屋
     * @param: nums
     * @param: colors
     * @return long
     * @author marks
     * @CreateDate: 2026/03/26 17:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int[] colors) {


        return 0;
    }

}
