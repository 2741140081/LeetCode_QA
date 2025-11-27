package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_74Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/27 11:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_74Test {

    @Test
    void searchMatrix() {
        // matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        int target = 3;
        LeetCode_74 leetCode_74 = new LeetCode_74();
        boolean result = leetCode_74.searchMatrix(matrix, target);
        System.out.println(result);
        // matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
        matrix = new int[][]{{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};
        target = 13;
        result = leetCode_74.searchMatrix(matrix, target);
        System.out.println(result);
    }
}