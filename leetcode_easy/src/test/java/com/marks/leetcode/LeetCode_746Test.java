package com.marks.leetcode;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_746Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_746TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void minCostClimbingStairs(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        int result = new LeetCode_746().minCostClimbingStairs(nums);
        System.out.println(sb + "====>" + result);
    }
}