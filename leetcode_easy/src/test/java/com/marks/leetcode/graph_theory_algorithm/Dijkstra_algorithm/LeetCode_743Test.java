package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

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
 * @date 2024/12/30 18:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_743Test {

    @ParameterizedTest
    @CsvFileSource(resources = "/data/testCsvData/LeetCode_743TestData.csv", numLinesToSkip = 1, nullValues = "null")
    void networkDelayTime(ArgumentsAccessor data) {
        StringBuffer sb = new StringBuffer();
        int[] nums = ArgumentAccessorUtils.ArgumentAccessorToIntArray(data, sb);

    }
}