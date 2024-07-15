package com.marks.leetcode;

public class LeetCode_1775 {
    public static void main(String[] args) {
        int[] nums1 = {1,2,3,4,5,6};
        int[] nums2 = {1,1,2,2,2,2};
        minOperations(nums1,nums2);

    }

    private static int minOperations(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length * 6 || nums2.length > nums1.length *6) {
            return -1;
        }
        return 0;
    }
}
