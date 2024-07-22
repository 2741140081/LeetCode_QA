package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_1594Test {

    @Test
    void maxProductPath() {
//        int[][] grid = new int[][] {{1,-2,1}, {1,-2,1}, {3,-4,1}};
        int[][] grid = new int[][] {{-1,-2,-3}, {-2,-3,-3}, {-3,-3,-2}};
        int result = new LeetCode_1594().maxProductPath(grid);
        System.out.println(result);
    }
}