package com.marks.leetcode.classic_linear_DP.LIS;

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
 * @data 2024/8/19 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1964Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_1964TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void longestObstacleCourseAtEachPosition(ArgumentsAccessor data) {
        // obstacles = [3,1,5,6,4,2]
        // int[] obstacles = {3,1,5,6,4,2};
        StringBuffer sb = new StringBuffer();
        int[] obstacles = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);
        int[] res = new LeetCode_1964().longestObstacleCourseAtEachPosition(obstacles);
        System.out.print("{");
        for (int i = 0; i < res.length; i++) {
            if (i == res.length - 1) {
                System.out.print(res[i]);
            }else {
                System.out.print(res[i] + ", ");
            }
        }
        System.out.println("}");
    }
}