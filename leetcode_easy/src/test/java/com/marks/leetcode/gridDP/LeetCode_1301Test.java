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

        System.out.println("[E23, 2X2, 12S] result is=====>" + result[0]);
        // ["E12","1X1","21S"]
        border.clear();
        border.add("E12");
        border.add("1X1");
        border.add("21S");
        result = new LeetCode_1301().pathsWithMaxScore(border);
        System.out.println("[E12, 1X1, 21S] result is=====>" + result[0]);

        // ["E11","XXX","11S"]
        border.clear();
        border.add("E11");
        border.add("XXX");
        border.add("11S");
        result = new LeetCode_1301().pathsWithMaxScore(border);
        System.out.println("[E11, XXX, 11S] result is=====>" + result[0]);

        // ["E11","XXX","11S"]
        border.clear();
        border.add("EX");
        border.add("XS");
        result = new LeetCode_1301().pathsWithMaxScore(border);
        System.out.println("[EX, XS] result is=====>" + result[0]);
    }
}