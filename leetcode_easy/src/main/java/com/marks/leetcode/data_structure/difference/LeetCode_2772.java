package com.marks.leetcode.data_structure.difference;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/19 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2772 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个正整数 k 。
     *
     * 你可以对数组执行下述操作 任意次 ：
     *
     * 从数组中选出长度为 k 的 任一 子数组，并将子数组中每个元素都 减去 1 。
     * 如果你可以使数组中的所有元素都等于 0 ，返回  true ；否则，返回 false 。
     *
     * 子数组 是数组中的一个非空连续元素序列。
     * @param nums
     * @param k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/19 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean checkArray(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 差分
     *
     * AC: 2ms/61.05MB
     * @param nums
     * @param k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/2/19 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        int count = 0;
        for (int i = 0; i < n; i++) {
            count += diff[i];
            int abs = nums[i] - count;
            if (abs == 0) {
                continue;
            }

            if (abs < 0 || i + k > n) {
                return false;
            }

            if (abs > 0) {
                count += abs;
                diff[i + k] -= abs;
            }
        }
        return true;
    }
}
