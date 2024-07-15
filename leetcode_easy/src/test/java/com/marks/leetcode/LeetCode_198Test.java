package com.marks.leetcode;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_198Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_198TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void rob(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        int rob = new LeetCode_198().rob(nums);
        int rob_II = new LeetCode_213().rob(nums);
        System.out.println(sb + "======>" + rob);
    }
}