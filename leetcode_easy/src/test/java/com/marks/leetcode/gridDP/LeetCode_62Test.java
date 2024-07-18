package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_62Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_62TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void uniquePaths(int m, int n) {
        int result = new LeetCode_62().uniquePaths(m, n);
        System.out.println(result);
    }
}