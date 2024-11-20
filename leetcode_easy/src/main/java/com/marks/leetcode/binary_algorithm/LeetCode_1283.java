package com.marks.leetcode.binary_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/19 16:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1283 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个正整数 threshold  ，你需要选择一个正整数作为除数，然后将数组里每个数都除以它，并对除法结果求和。
     *
     * 请你找出能够使上述结果小于等于阈值 threshold 的除数中 最小 的那个。
     *
     * 每个数除以除数后都向上取整，比方说 7/3 = 3 ， 10/2 = 5 。
     *
     * 题目保证一定有解。
     *
     * tips:
     * 1 <= nums.length <= 5 * 10^4
     * 1 <= nums[i] <= 10^6
     * nums.length <= threshold <= 10^6
     * ]
     * @param nums 
     * @param threshold
     * @return int
     * @author marks
     * @CreateDate: 2024/11/19 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int smallestDivisor(int[] nums, int threshold) {
        int result;
        result = method_01(nums, threshold);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,2,5,9], threshold = 6
     *
     * AC:39ms/47.34MB
     * ]
     * @param nums
     * @param threshold
     * @return int
     * @author marks
     * @CreateDate: 2024/11/19 17:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int threshold) {
        int left = 0;
        int right = Arrays.stream(nums).max().getAsInt();
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            int sum = getArraySum(nums, mid);
            if (sum > threshold) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    private int getArraySum(int[] nums, int mid) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // sum += Math.ceil((double) nums[i] / mid);
            sum += (nums[i] - 1) / mid + 1; // 将上面那种方式优化
        }
        return sum;
    }
}
