package com.marks.leetcode.difference_array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2772 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/11 11:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2772 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个正整数 k 。
     * 你可以对数组执行下述操作 任意次 ：
     * 从数组中选出长度为 k 的 任一 子数组，并将子数组中每个元素都 减去 1 。
     * 如果你可以使数组中的所有元素都等于 0 ，返回  true ；否则，返回 false 。
     * 子数组 是数组中的一个非空连续元素序列。
     * @param: nums
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/11 11:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean checkArray(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * AC: 3ms/103.84MB
     * @param: nums
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/11 11:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt += diff[i];
            if (nums[i] > cnt) {
                int abs = nums[i] - cnt;
                if (i + k >= n + 1) {
                    return false;
                }
                diff[i + k] = -abs;
                cnt += abs;
            } else if (nums[i] < cnt) {
                return false;
            }
        }

        return true;
    }

}
