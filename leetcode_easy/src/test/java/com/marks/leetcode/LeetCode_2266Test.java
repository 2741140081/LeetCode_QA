package com.marks.leetcode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_2266Test {

    @ParameterizedTest
    @ValueSource(strings = {"22233", "222222222222222222222222222222222222", "22", "2", "222", "2222"})
    void countTexts(String str) {
        int result = new LeetCode_2266().countTexts(str);
        System.out.println( str + "=====result====" + result);
    }
}