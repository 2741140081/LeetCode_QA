package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2343Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/14 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2343Test {

    @Test
    void smallestTrimmedNumbers() {
        // nums = ["24","37","96","04"], queries = [[2,1],[2,2]]
        String[] nums = {"24","37","96","04"};
        int[][] queries = {{2,1},{2,2}};
        int[] result = new LeetCode_2343().smallestTrimmedNumbers(nums, queries);
        // 输出
        System.out.println(Arrays.toString(result));
    }
}