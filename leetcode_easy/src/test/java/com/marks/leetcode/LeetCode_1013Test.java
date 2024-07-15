package com.marks.leetcode;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class LeetCode_1013Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_1013TestData.csv", nullValues = "null", numLinesToSkip = 1)
    void canThreePartsEqualSum(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);

        boolean result = new LeetCode_1013().canThreePartsEqualSum(nums);
        System.out.println(sb + "=====>" + result);

    }
}

class LeetCode_70Test {

    @ParameterizedTest(name = "test case {index}, 参数:{0}")
    @ValueSource(ints = {2, 3, 4, 5, 42})
    void canThreePartsEqualSum(int num) {
        StringBuffer sb = new StringBuffer();
        long startMs = System.currentTimeMillis();
        long startTime = System.nanoTime();
        int result = new LeetCode_70().climbStairs(num);
        long endTime = System.nanoTime();
        long endMs = System.currentTimeMillis();
        System.out.println("花费的时间为" + (endTime - startTime)/1000000 + "ms");
        System.out.println("花费的时间为" + (endMs - startMs) + "ms");
        System.out.printf("需要上的台阶数%s; 共有%s", num ,result);
        System.out.println();


    }
}