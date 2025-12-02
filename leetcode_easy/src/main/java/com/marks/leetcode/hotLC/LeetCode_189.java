package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_189 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/1 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_189 {

    /**
     * @Description:
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * tips:
     * 1 <= nums.length <= 10^5
     * -2^31 <= nums[i] <= 2^31 - 1
     * 0 <= k <= 10^5
     *
     * AC: 1ms/62.13MB
     * @param: nums
     * @param: k
     * @return void
     * @author marks
     * @CreateDate: 2025/12/01 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k == 0) {
            return;
        }
        int[] temp = new int[k];
        for (int i = 0; i < k; i++) {
            temp[i] = nums[n - k + i];
        }
        for (int i = n - k - 1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }
        for (int i = 0; i < k; i++) {
            nums[i] = temp[i];
        }
    }

}
