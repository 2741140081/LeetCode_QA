package com.marks.leetcode;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_724Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_724TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void pivotIndex(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);

        LeetCode_724 solution = new LeetCode_724();
        int midResult = solution.pivotIndex(nums);
        System.out.println(sb.toString() + "==result is==>" +midResult);

    }
}