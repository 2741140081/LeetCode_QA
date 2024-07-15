package com.marks.leetcode.array;

public class LeetCode_665 {
    public static void main(String[] args) {
        int[] nums = {3,4,2,3};
        System.out.println(checkPossibility(nums));
    }

    /**
     * 给你一个长度为 n的整数数组nums，请你判断在 最多 改变 1 个元素的情况下，该数组能否变成一个非递减数列。
     *
     * 我们是这样定义一个非递减数列的：对于数组中任意的i (0 <= i <= n-2)，总满足 nums[i] <= nums[i + 1]。
     *
     * 输入: nums = [4,2,3]
     * 输出: true
     * 解释: 你可以通过把第一个 4 变成 1 来使得它成为一个非递减数列。
     *
     * 输入: nums = [4,2,1]
     * 输出: false
     * 解释: 你不能在只改变一个元素的情况下将其变为非递减数列。
     *
     * 思考：
     * 对数组进行循环，判断数据当前有多少处是递减的
     * 如果超过2处时，则返回false
     * 如果只有一处则返回true
     *
     * -- 结果：解答错误 错误案例{3,4,2,3} 输出结果true; 预期结果: true;
     * 进一步在count == 1时改进，
     * 我需要记录递减时的下标值 index = 1
     *
     * @param nums
     * @return
     */

    private static boolean checkPossibility(int[] nums) {
        int count = 0;
        int index = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i+1]) {
                index = i;
                count++;
            }
            if (count>=2) {
                break;
            }
        }
        System.out.println(count);
        if (count>1) {
            return false;
        }else {
            if (nums[index] > nums[index+2]) {
                return false;
            }else {
                return true;
            }
        }

    }
}
