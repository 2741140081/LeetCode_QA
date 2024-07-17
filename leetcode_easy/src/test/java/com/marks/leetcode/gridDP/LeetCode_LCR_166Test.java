package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_LCR_166Test {

    @Test
    void jewelleryValue() {
//        int[][] frame = new int[][]{{1,3,1},{1,5,1},{4,2,1}};
        int[][] frame_1 = new int[][]{{0}};
        int result = new LeetCode_LCR_166().jewelleryValue(frame_1);
        System.out.println(result);

    }
}