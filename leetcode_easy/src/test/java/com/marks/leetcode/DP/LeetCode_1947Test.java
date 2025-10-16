package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 11:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1947Test {

    @Test
    void maxCompatibilitySum() {
        // [[1,1,0,1,0],[1,0,1,0,0],[0,1,0,0,0],[1,1,0,1,0]]
        // [[0,1,1,1,0],[1,0,0,0,1],[0,0,1,1,0],[1,1,0,0,0]]
        int[][] students = {{1, 1, 0, 1, 0}, {1, 0, 1, 0, 0}, {0, 1, 0, 0, 0}, {1, 1, 0, 1, 0}};
        int[][] mentors = {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {0, 0, 1, 1, 0}, {1, 1, 0, 0, 0}};
        LeetCode_1947 leetCode_1947 = new LeetCode_1947();
        int result = leetCode_1947.maxCompatibilitySum(students, mentors);
        System.out.println(result);
        assertEquals(12, result);
    }
}