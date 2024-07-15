package com.marks.leetcode.array;


public class LeetCode_11 {
    public static void main(String[] args) {
        int[] height = {1,8,6,2,5,4,8,3,7};
        System.out.println(maxArea(height));
    }

    private static int maxArea(int[] height) {
        int left = 0, right = height.length-1;
        int temp = height[left] > height[right] ? height[right] : height[left];
        int rsg = temp * (right - left);

        while (right > left) {
            int area = Math.min(height[left], height[right]) * (right-left);
            rsg = Math.max(area, rsg);
            if (height[left] >= height[right]) {
                right--;
            }else {
                left++;
            }

        }

        return rsg;
    }
}
