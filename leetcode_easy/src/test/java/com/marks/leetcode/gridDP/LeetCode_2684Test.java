package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2684Test {

    @Test
    void maxMoves() {
        int[][] grid = new int[][]{{137,112,78,67}, {76,65,122,135}};
        int result = new LeetCode_2684().maxMoves(grid);
        System.out.println(result);
    }
}