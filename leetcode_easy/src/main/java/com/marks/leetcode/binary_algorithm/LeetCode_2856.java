package com.marks.leetcode.binary_algorithm;

import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/19 14:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2856 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的 非递减 整数数组 nums 。
     *
     * 你可以执行以下操作任意次：
     *
     * 选择 两个 下标 i 和 j ，满足 nums[i] < nums[j] 。
     * 将 nums 中下标在 i 和 j 处的元素删除。剩余元素按照原来的顺序组成新的数组，下标也重新从 0 开始编号。
     * 请你返回一个整数，表示执行以上操作任意次后（可以执行 0 次），nums 数组的 最小 数组长度。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * nums 是 非递减 数组。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/19 14:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minLengthAfterRemovals(List<Integer> nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [功能描述]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/19 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> nums) {
        int n = nums.size();
        int x = nums.get(n / 2);
        int maxCnt = lowerBound(nums, x + 1) - lowerBound(nums, x);
        return Math.max(maxCnt * 2 - n, n % 2);
    }

    private int lowerBound(List<Integer> nums, int target) {
        int left = -1, right = nums.size(); // 开区间 (left, right)
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < target) {
                left = mid; // 范围缩小到 (mid, right)
            } else {
                right = mid; // 范围缩小到 (left, mid)
            }
        }
        return right; // 或者 left+1
    }
}
