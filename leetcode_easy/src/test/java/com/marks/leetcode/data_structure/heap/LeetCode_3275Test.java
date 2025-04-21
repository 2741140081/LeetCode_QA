package com.marks.leetcode.data_structure.heap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/8 10:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3275Test {

    @Test
    void resultsArray() {
        int[][] queries = {{1,2},{3,4},{2,3},{-3,0}};
        int k = 2;
        int[] resultsArray = new LeetCode_3275().resultsArray(queries, k);
    }
}