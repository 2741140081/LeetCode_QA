package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_64Test {

    @Test
    void minPathSum() {
        int[][] grid = new int[][]{{1,2,3},{4,5,6}};
        int result = new LeetCode_64().minPathSum(grid);
        System.out.println(result);
    }
}