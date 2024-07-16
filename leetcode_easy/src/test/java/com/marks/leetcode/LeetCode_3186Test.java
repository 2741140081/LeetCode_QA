package com.marks.leetcode;

import com.marks.common.ArgumentAccessorUtils;
import com.marks.leetcode.rob.LeetCode_3186;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

class LeetCode_3186Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_3186TestData.csv", nullValues = "null", numLinesToSkip = 0)
    void maximumTotalDamage(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] power = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        long result = new LeetCode_3186().maximumTotalDamage(power);
    }


}