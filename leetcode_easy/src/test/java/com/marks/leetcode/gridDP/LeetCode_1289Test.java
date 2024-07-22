package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_1289Test {

    @Test
    void minFallingPathSum() {
//        int[][] grid = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
        int[][] grid = new int[][]{{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2},{2,2,1,2,2}};
        int result = new LeetCode_1289().minFallingPathSum(grid);
        System.out.println(result);
    }
}