package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3862 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/25 16:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3862 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 如果某个下标 i 满足以下条件，则称其为 平衡下标： i 左侧所有元素的总和等于 i 右侧所有元素的乘积。
     * 如果左侧没有元素，则总和视为 0。同样，如果右侧没有元素，则乘积视为 1。
     * 要求返回最小的 平衡下标，如果不存在平衡下标，则返回 -1。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/25 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestBalancedIndex(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 由于 nums[i] 是一个正数, 所以前缀和是一个递增数组, 并且后缀的乘积也是一个递增的数组
     * 2. 使用双指针分别指向两端, 初始时
     * AC: 3ms/140.36MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/06/25 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return -1;
        }
        int left = 0, right = n - 1;
        long sum = 0, muti = 1;
        while (left < right) {
            if (sum < muti) {
                sum += nums[left];
                left++;
            } else {
                // 最大前缀和是 10^14, 如果 muti 超过 10^14, 则必定无法找到 平衡下标, 返回 -1
                if (muti > (long) 1e14 / nums[right]) {
                    return -1;
                }
                muti *= nums[right];
                right--;
            }
        }

        return sum == muti ? left : -1;
    }

}
