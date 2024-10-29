package com.marks.leetcode.sliding_window.random_length;

import com.marks.common.ArgumentAccessorUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/28 16:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1574Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_1574TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void findLengthOfShortestSubarray(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] arr = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        int result = new LeetCode_1574().findLengthOfShortestSubarray(arr);
        System.out.println(sb + "======>" + result);

    }
}