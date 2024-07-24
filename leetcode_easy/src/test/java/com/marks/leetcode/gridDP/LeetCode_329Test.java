package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_329Test {

    @Test
    void longestIncreasingPath() {
        // [[3,4,5],[3,2,6],[2,2,1]]
//        int[][] matrix = new int[][]{{3,4,5},{3,2,6},{2,2,1}};
        // [[7,7,5],[2,4,6],[8,2,0]]
        int[][] matrix = new int[][]{{7,7,5},{2,4,6},{8,2,0}};
        int result = new LeetCode_329().longestIncreasingPath(matrix);
        System.out.println(result);
    }
}