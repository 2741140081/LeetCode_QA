package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/16 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2509Test {

    @Test
    void cycleLengthQueries() {
        int n = 3;
        int[][] queries = {{5,3}, {4,7}, {2,3}};
        int[] ans = new LeetCode_2509().cycleLengthQueries(n, queries);
    }
}