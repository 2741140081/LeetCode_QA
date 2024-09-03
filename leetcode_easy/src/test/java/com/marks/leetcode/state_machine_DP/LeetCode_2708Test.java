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
 * @data 2024/9/3 17:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2708Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_2708TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void maxStrength(ArgumentsAccessor data) {
        // nums = [3,-1,-5,2,5,-9]
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        long result = new LeetCode_2708().maxStrength(nums);
        System.out.println(sb.toString() + "=====>" + result);
    }
}