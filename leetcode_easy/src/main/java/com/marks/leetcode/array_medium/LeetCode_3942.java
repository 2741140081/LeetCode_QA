package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3942 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/14 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3942 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums，其中 nums 是区间 [0..n - 1] 中所有数字的一个排列。
     * 你 只能 执行以下操作：
     * 反转 整个数组。
     * 左旋一位：将第一个元素移动到数组末尾，其余元素整体向左移动一位。
     * 返回将数组按 递增 顺序排序所需的 最少 操作次数。如果仅使用给定操作无法将数组排序，则返回 -1。
     * 排列 是数组中所有元素的一种重新排列。
     *
     * tips:
     * 1 <= n == nums.length <= 10^5
     * 0 <= nums[i] <= n - 1
     * nums 是从 0 到 n - 1 的整数排列。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/09/14 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 操作不会改变数组中元素的相对顺序, 即如果 0 和 1 是相邻的, 那么无论如何操作, 他们最后必定还是相邻的
     * 2. 所以可以直接找到最小元素 0, 然后
     * AC: 2ms/93.4MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/09/14 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int cnt = 0;
        int p = 0;
        for (int i = 1; i < n && cnt < 2; i++) {
            if (nums[i - 1] > nums[i]) {
                cnt++;
                p = i;
            }
        }

        if (cnt == 0) { // 已是递增
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        if (cnt == 1 && nums[0] > nums[n - 1]) { // 两个递增段
            ans = Math.min(p, n - p + 2);
        }

        cnt = p = 0;
        for (int i = 1; i < n && cnt < 2; i++) {
            if (nums[i - 1] < nums[i]) {
                cnt++;
                p = i;
            }
        }

        if (cnt == 0) { // 已是递减
            return 1;
        }
        if (cnt == 1 && nums[0] < nums[n - 1]) { // 两个递减段
            ans = Math.min(ans, Math.min(p, n - p) + 1);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

}
