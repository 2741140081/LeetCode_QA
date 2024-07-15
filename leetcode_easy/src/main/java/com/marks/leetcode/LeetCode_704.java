package com.marks.leetcode;

public class LeetCode_704 {
    public static void main(String[] args) {
        int[] nums = {-1,0,3,5,9,12};
        int target = 9;
//        String a = "" + target;
//        long b = Integer.parseInt(a);

        System.out.println(search1(nums, target));
        System.out.println(search2(nums, target));
    }

    private static int search2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == target) {
                return left;
            }else if (nums[right] == target){
                return right;
            }

            if (nums[left] < target) {
                left++;
            }
            if (nums[right] > target) {
                right--;
            }

        }
        return -1;
    }

    private static int search1(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left)/2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
