package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_120Test {

    /**
     *      * 2
     *      * 3 4
     *      * 6 5 7
     *      * 4 1 8 3
     */
    @Test
    void minimumTotal() {
        List<List<Integer>> triangle = new ArrayList<>();
        ArrayList<Integer> list_1 = new ArrayList<>();
        list_1.add(2);
        triangle.add(list_1);

        ArrayList<Integer> list_2 = new ArrayList<>();
        list_2.add(3);
        list_2.add(4);
        triangle.add(list_2);

        ArrayList<Integer> list_3 = new ArrayList<>();
        list_3.add(6);
        list_3.add(5);
        list_3.add(7);
        triangle.add(list_3);

        ArrayList<Integer> list_4 = new ArrayList<>();
        list_4.add(4);
        list_4.add(1);
        list_4.add(8);
        list_4.add(3);
        triangle.add(list_4);

        int result = new LeetCode_120().minimumTotal(triangle);
        System.out.println(result);
    }
}