package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description: LeetCode_473 单元测试类
 * @Author: marks
 * @CreateDate: 2025/4/5 10:00
 */
public class LeetCode_473Test {

    private final LeetCode_473 solution = new LeetCode_473();

    @Test
    @DisplayName("TC01: 无法组成正方形 - [1,1,2,2,2]")
    public void testMakesquare_TC01() {
        int[] matchsticks = {1, 1, 2, 2, 2};
        boolean result = solution.makesquare(matchsticks);
        assertTrue(result);
    }

    @Test
    @DisplayName("TC02: 无法组成正方形 - [3,3,3,3,4]")
    public void testMakesquare_TC02() {
        int[] matchsticks = {3, 3, 3, 3, 4};
        boolean result = solution.makesquare(matchsticks);
        assertFalse(result);
    }

    @Test
    @DisplayName("TC03: 可以组成正方形 - [1,1,1,1]")
    public void testMakesquare_TC03() {
        int[] matchsticks = {1, 1, 1, 1};
        boolean result = solution.makesquare(matchsticks);
        assertTrue(result);
    }

    @Test
    @DisplayName("TC05: 复杂情况无法组成正方形")
    public void testMakesquare_TC05() {
        int[] matchsticks = {5, 5, 5, 5, 16, 4, 4, 4, 4, 4, 3, 3, 3, 3, 4};
        boolean result = solution.makesquare(matchsticks);
        assertFalse(result);
    }

    @Test
    @DisplayName("TC06: 复杂情况可以组成正方形")
    public void testMakesquare_TC06() {
        int[] matchsticks = {5, 5, 5, 5, 4, 4, 4, 4, 3, 3, 3, 3};
        boolean result = solution.makesquare(matchsticks);
        assertTrue(result);
    }

    @Test
    @DisplayName("TC08: 火柴数量不足")
    public void testMakesquare_TC08() {
        int[] matchsticks = {1};
        boolean result = solution.makesquare(matchsticks);
        assertFalse(result);
    }
}
