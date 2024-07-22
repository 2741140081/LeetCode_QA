package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_1301Test {

    @Test
    void pathsWithMaxScore() {
        List<String> border = new ArrayList<>();
        // ["E23","2X2","12S"]
        border.add("E23");
        border.add("2X2");
        border.add("12S");
        int[] result = new LeetCode_1301().pathsWithMaxScore(border);
    }
}