package com.marks.leetcode.maximum_subarray_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2606Test {

    @Test
    void maximumCostSubstring() {
        String s_01 = "adaa";
        String chars_01 = "d";
        int[] vals_01 = {-1000};

        int result_01 = new LeetCode_2606().maximumCostSubstring(s_01, chars_01, vals_01);

        String s_02 = "abc";
        String chars_02 = "abc";
        int[] vals_02 = {-1, -1, -1};

//        int result_02 = new LeetCode_2606().maximumCostSubstring(s_02, chars_02, vals_02);

        String s_03 = "hghhghgghh";
        String chars_03 = "hg";
        int[] vals_03 = {2, 3};

        int result_03 = new LeetCode_2606().maximumCostSubstring(s_03, chars_03, vals_03);

        String s_04 = "zox";
        String chars_04 = "zoxr";
        int[] vals_04 = {2,-5,-4,-5};

        int result_04 = new LeetCode_2606().maximumCostSubstring(s_04, chars_04, vals_04);

//        System.out.println("result_01===>" + result_01);
//        System.out.println("result_02===>" + result_02);
        System.out.println("result_03===>" + result_03);
        System.out.println("result_03===>" + result_04);
    }
}