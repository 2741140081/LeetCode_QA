package com.marks.leetcode.state_machine_DP;

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
 * @data 2024/9/11 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1186Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_1186TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void maximumSum(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] arr = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        // arr = [1,-2,0,3]
//        int[] arr = {1, -2, 0, 3};
        int result = new LeetCode_1186().maximumSum(arr);
        System.out.println(sb.toString() + "=====>" + result);
    }
}