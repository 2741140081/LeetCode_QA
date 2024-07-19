package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_931Test {

    @Test
    void minFallingPathSum() {
        int[][] matrix = new int[][]{{2, 1, 3},{6, 5, 4},{7, 8, 9}};
        int result = new LeetCode_931().minFallingPathSum(matrix);
        System.out.println(result);
    }
}