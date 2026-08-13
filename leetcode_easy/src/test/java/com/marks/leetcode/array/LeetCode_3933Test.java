package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3933Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/7 14:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3933Test {

    @Test
    void countLocalMaximums() {
        //  matrix = [[0,2,1],[1,1,0]]
        int[][] matrix = {{0,2,1},{1,1,0}};
        LeetCode_3933 leetCode_3933 = new LeetCode_3933();
        int result = leetCode_3933.countLocalMaximums(matrix);
        System.out.println(result);
    }
}