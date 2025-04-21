package com.marks.leetcode.data_structure.prefix_sum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2055Test {

    @Test
    void platesBetweenCandles() {
        // s = "**|**|***|", queries = [[2,5],[5,9]]
        String s = "**|**|***|";
        int[][] queries = {{2,5}, {5,9}};
        int[] result = new LeetCode_2055().platesBetweenCandles(s, queries);
    }
}