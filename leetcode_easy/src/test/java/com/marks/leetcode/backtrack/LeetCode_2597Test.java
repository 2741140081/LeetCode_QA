package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_2597 Tester.
 *
 * @author marks
 * @since 2025-08-19
 * @version 1.0
 */
public class LeetCode_2597Test {

    private LeetCode_2597 leetCode2597;

    @BeforeEach
    public void setUp() {
        leetCode2597 = new LeetCode_2597();
    }

    @AfterEach
    public void tearDown() {
        leetCode2597 = null;
    }

    /**
     * Test case for nums = [2,4,6], k = 2
     * Expected result: 4
     */
    @Test
    public void testCase1() {
        int[] nums = {2, 4, 6};
        int k = 2;
        int expected = 4;
        int result = leetCode2597.beautifulSubsets(nums, k);
        assertEquals(expected, result, "Test case 1 failed");
    }

    /**
     * Test case for nums = [1], k = 1
     * Expected result: 1
     */
    @Test
    public void testCase2() {
        int[] nums = {1};
        int k = 1;
        int expected = 1;
        int result = leetCode2597.beautifulSubsets(nums, k);
        assertEquals(expected, result, "Test case 2 failed");
    }

    /**
     * Test case for nums = [4,2,5,9,10,3], k = 1
     * 6 + 4
     * Expected result: 23
     */
    @Test
    public void testCase3() {
        int[] nums = {4,2,5,9,10,3};
        int k = 1;
        int expected = 23;
        int result = leetCode2597.beautifulSubsets(nums, k);
        assertEquals(expected, result, "Test case 3 failed");
    }

    /**
     * Test case for nums = [4,2,5,9,10,3], k = 1
     * Expected result: 24
     */
    @Test
    public void testCase4() {
        int[] nums = {4, 2, 5, 9, 10, 3};
        int k = 1;
        int expected = 24;
        int result = leetCode2597.beautifulSubsets(nums, k);
        assertEquals(expected, result, "Test case 4 failed");
    }

    /**
     * Test case for nums = [1,1,1,1], k = 1
     * Expected result: 15 (all non-empty subsets are beautiful since there's no difference of 1)
     */
    @Test
    public void testCase5() {
        int[] nums = {1, 1, 1, 1};
        int k = 1;
        int expected = 15;
        int result = leetCode2597.beautifulSubsets(nums, k);
        assertEquals(expected, result, "Test case 5 failed");
    }

    /**
     * Test case for nums = [5,2,8], k = 3
     * Expected result: 6
     */
    @Test
    public void testCase6() {
        int[] nums = {5, 2, 8};
        int k = 3;
        int expected = 6;
        int result = leetCode2597.beautifulSubsets(nums, k);
        assertEquals(expected, result, "Test case 6 failed");
    }
}