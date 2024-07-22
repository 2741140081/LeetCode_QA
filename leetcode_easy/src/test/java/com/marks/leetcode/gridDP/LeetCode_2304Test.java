package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2304Test {

    @Test
    void minPathCost() {
        int[][] grid = new int[][]{{5,3},{4,0},{2,1}};
        int[][] moveCost = new int[][]{{9,8},{1,5},{10,12},{18,6},{2,4},{14,3}};
        int result = new LeetCode_2304().minPathCost(grid, moveCost);
        System.out.println(result);
    }
}