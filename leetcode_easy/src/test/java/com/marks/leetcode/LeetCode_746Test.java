package com.marks.leetcode;

import com.marks.common.ArgumentAccessorUtils;
import com.marks.leetcode.climb_stairs.LeetCode_746;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

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