package com.marks.leetcode.weekly;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q3Test {

    @Test
    void longestSubarray() {
        // 输入： nums = [7,6,10,12,11], k = 3©leetcode
        int[] nums = {7,6,10,12,11};
        int k = 3;
        int result = new Q3().longestSubarray(nums, k);
        System.out.println(result);
    }
}