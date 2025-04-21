package com.marks.leetcode.data_structure.prefix_sum;

/**
 * <p>项目名称: LeetCode_303 </p>
 * <p>文件名称:  </p>
 * <p>描述: AC:1ms/48.30MB </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/10 17:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class NumArray {
    private int[] pre;

    public NumArray(int[] nums) {
        int n = nums.length;
        pre = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
        }
    }

    public int sumRange(int left, int right) {
        return pre[right + 1] - pre[left];
    }
}
