package com.marks.leetcode;

import java.util.Arrays;

public class LeetCode_724 {
    /**
     * description :
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
     *
     * example 1:
     * 输入：nums = [1, 7, 3, 6, 5, 6]
     * 输出：3
     * 解释：
     * 中心下标是 3 。
     * 左侧数之和 sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11 ，
     * 右侧数之和 sum = nums[4] + nums[5] = 5 + 6 = 11 ，二者相等。
     *
     * example 2:
     * 输入：nums = [1, 2, 3]
     * 输出：-1
     * 解释：
     * 数组中不存在满足此条件的中心下标。
     *
     * example 3:
     * 输入：nums = [2, 1, -1]
     * 输出：0
     * 解释：
     * 中心下标是 0 。
     * 左侧数之和 sum = 0 ，（下标 0 左侧不存在元素），
     * 右侧数之和 sum = nums[1] + nums[2] = 1 + -1 = 0 。
     *
     * 提示：
     *
     * 1 <= nums.length <= 104
     * -1000 <= nums[i] <= 1000
     *
     * @param nums
     * @return
     */

    public int pivotIndex(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int result = -1;
//        result = method_01(nums); // fail
//        result = method_02(nums); // pass, but not best
        result = method_solution(nums);
        return result;
    }

    /**
     * 依据LeetCode官方题解
     * 1.对于数组 nums内元素之和为total
     * 2. 若存在某一个元素num[i] ,使得left_sum == right_sum
     * 3. total = left_sum + num[i] + right_sum  ==> 即 2 * sum + num[i] == total 成立时 i满足条件
     *
     * @param nums
     * @return
     */
    private int method_solution(int[] nums) {
        int total = Arrays.stream(nums).sum();
        int sum = 0;
        // 遍历nums
        for (int i = 0; i < nums.length; i++) {
            if (2 * sum + nums[i] == total) {
                return i;
            }
            sum += nums[i];
        }
        return -1;
    }

    private int method_02(int[] nums) {
        // 方法2: 暴力求解
        /**
         * 对目标数组进行遍历, 判断数组元素下标i,
         * if(i_left_sum == i_right_sum) return i;
         */

        for (int i = 0; i < nums.length; i++) {
            int i_left_sum = 0;
            int i_right_sum = 0;
            // get i_left_sum
            for (int left_i = 0; left_i < i; left_i++) {
                i_left_sum = i_left_sum + nums[left_i];
            }

            for (int right_i = i+1; right_i < nums.length; right_i++) {
                i_right_sum = i_right_sum + nums[right_i];
            }

            if (i_left_sum == i_right_sum) {
                return i;
            }
        }
        return -1;
    }

    private int method_01(int[] nums) {
        // 方法1: 双指针

        // 初始化数据
        int left = 0;
        int right = nums.length - 1;

        int sumLeft = 0;
        int sumRight = 0;

        /**
         * [1, 7, 3, 6, 5, 6]
         * init: left = 0, right = 5, sumLeft = 0, sumRight = 0
         * left++
         * step1: left = 1, right = 5, sumLeft = 1, sumRight = 0
         * right--
         * step2: left = 1, right = 4, sumLeft = 1, sumRight = 6
         * left++
         * step3: left = 2, right = 4, sumLeft = 8, sumRight = 6
         * right--
         * step4: left = 2, right = 3, sumLeft = 8, sumRight = 11
         * left++
         * step5: left = 3, right = 3, sumLeft = 11, sumRight = 11
         */
        while (left < right) {
            // eg: nums = [1, 7, 3, 6, 5, 6]

            if (sumLeft > sumRight) {
                sumRight = sumRight + right;
                right--;
            } else if (sumLeft == sumRight && right - left == 1) {
                break;
            } else {
                sumLeft = sumLeft + left;
                left++;
            }

        }
        return right - left == 1 ? right - 1 : -1;
    }
}
