package com.marks.leetcode.array;

import java.util.ArrayList;

/**
 * 给你一个由正整数组成的整数数组 nums ，返回其中可被 3 整除的所有偶数的平均值。
 * 注意：n 个元素的平均值等于 n 个元素 求和 再除以 n ，结果 向下取整 到最接近的整数。
 * Eg:
 * 输入：nums = [1,3,6,10,12,15]
 * 输出：9
 * 解释：6 和 12 是可以被 3 整除的偶数。(6 + 12) / 2 = 9 。
 */
public class LeetCode_2455 {
    public static void main(String[] args) {
        int[] nums = {1,3,6,10,12,15};
        System.out.println(averageValue(nums));
    }

    private static int averageValue(int[] nums) {
        ArrayList<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i]%2 ==0) && (nums[i]%3==0)) {
                numberList.add(nums[i]);
            }
        }
        int sum = 0;
        for (int i = 0; i < numberList.size(); i++) {
            sum += numberList.get(i);
        }
        int res = (int) Math.floor(sum/ numberList.size());
        return res;
    }
}
