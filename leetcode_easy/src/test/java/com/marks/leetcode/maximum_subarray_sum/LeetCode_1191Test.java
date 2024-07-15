package com.marks.leetcode.maximum_subarray_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_1191Test {

    @Test
    void kConcatenationMaxSum() {
        int[] arr = {10000,10000,10000,10000,10000,10000,10000,10000,10000,10000};
        int k = 100000;
        int result = new LeetCode_1191().kConcatenationMaxSum(arr, k);
        System.out.println("result ====> " + result);
    }
}