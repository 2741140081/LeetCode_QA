package com.marks.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_740Test {

    @Test
    void deleteAndEarn() {
        int[] ints = {2,2,3,3,3,4};
        int result = new LeetCode_740().deleteAndEarn(ints);
    }

    @Test
    void maximumTotalDamage() {
        int[] power = {7,1,6,6};
        int[] power2 = {7,1,6,3};
        long result = new LeetCode_3186().maximumTotalDamage(power2);
    }
}