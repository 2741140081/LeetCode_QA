package com.marks.leetcode.weekly;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q4Test {

    @Test
    void validSubarrays() {
        // [10,16,10,16,19]©leetcode
        int[] nums = {10,16,10,16,19};
        int k = 2;
        // [[3,4],[0,3],[1,4],[0,2]]©leetcode
        int[][] queries = {{3,4},{0,3},{1,4},{0,2}};
        Q4 q4 = new Q4();
        boolean[] result = q4.validSubarrays(nums, k, queries);
    }
}