package com.marks.leetcode.maximum_subarray_sum;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_918Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_918TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void maxSubarraySumCircular(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        int result = new LeetCode_918().maxSubarraySumCircular(nums);
        System.out.println(sb + "====result====>" + result);
    }
}