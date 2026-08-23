package com.marks.leetcode.weekly;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Q2Test {

    @Test
    void findDisappearedNumbers() {
        // 输入： nums = [3,9,7], lower = 1, upper = 12©leetcode
        int[] nums = {3,9,7};
        int lower = 1, upper = 12;
        Q2 q2 = new Q2();
        q2.findDisappearedNumbers(nums, lower, upper);
    }
}