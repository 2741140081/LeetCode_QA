package com.marks.leetcode.maximum_subarray_sum;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;


class LeetCode_152Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_152TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void maxProduct(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        int result = new LeetCode_152().maxProduct(nums);
        System.out.println(sb + "===result====>" + result);
    }
}