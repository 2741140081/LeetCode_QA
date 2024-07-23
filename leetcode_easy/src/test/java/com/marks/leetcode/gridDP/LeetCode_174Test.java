package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_174Test {

    @Test
    void calculateMinimumHP() {
        int[][] dungeon = new int[][]{{-2,-3,3},{-5,-10,1},{10,30,-5}};
        int result = new LeetCode_174().calculateMinimumHP(dungeon);
        System.out.println(result);
    }
}