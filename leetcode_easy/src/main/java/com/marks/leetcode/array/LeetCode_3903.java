package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3903 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/4 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3903 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 和一个整数 k。
     * 对于每个下标 i，定义它的 不稳定值 为 max(nums[0..i]) - min(nums[i..n - 1])。
     * 换句话说：
     * max(nums[0..i]) 表示从下标 0 到下标 i 的元素中的 最大值 。
     * min(nums[i..n - 1]) 表示从下标 i 到下标 n - 1 的元素中的 最小值 。
     * 如果某个下标 i 的不稳定值 小于等于 k，则称该下标为 稳定下标 。
     * 返回 最小 的稳定下标。如果不存在这样的下标，则返回 -1。
     *
     * tips:
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 10^9
     * 0 <= k <= 10^9
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/04 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int firstStableIndex(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 预处理前缀最大值和后缀最小值
     * AC: 1ms/45.39MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/04 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] prefixMax = new int[n];
        int[] suffixMin = new int[n];
        prefixMax[0] = nums[0];
        suffixMin[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            prefixMax[i] = Math.max(prefixMax[i - 1], nums[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i + 1], nums[i]);
        }
        for (int i = 0; i < n; i++) {
            if (prefixMax[i] - suffixMin[i] <= k) {
                return i;
            }
        }
        return -1;
    }

}
