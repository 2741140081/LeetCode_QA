package com.marks.leetcode;

import java.util.Arrays;

public class LeetCode_2357 {
    public static void main(String[] args) {

    }

    public static int minimumOperations(int[] nums){
        int count = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                nums = getNewNums(nums, nums[i]);
                count++;
            }
        }
        return count;
    }

    private static int[] getNewNums(int[] nums, int num) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[i] = nums[i] - num;

            }
        }
        return nums;
    }
}
