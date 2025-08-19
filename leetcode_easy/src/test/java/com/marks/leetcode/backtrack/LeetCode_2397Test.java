package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_2397 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 18, 2025</pre>
 */
public class LeetCode_2397Test {

    /**
     * Method: maximumRows(int[][] matrix, int numSelect)
     */
    @Test
    public void testMaximumRows() {
        LeetCode_2397 solution = new LeetCode_2397();
        
        // 测试用例1: 题目示例
        int[][] matrix1 = {{0,0,0},{1,0,1},{0,1,1},{0,0,1}};
        int numSelect1 = 2;
        int expected1 = 3;
        assertEquals(expected1, solution.maximumRows(matrix1, numSelect1));
        
        // 测试用例2: 选择所有列
        int[][] matrix2 = {{1,0,0},{0,1,0},{1,1,1}};
        int numSelect2 = 3;
        int expected2 = 3;
        assertEquals(expected2, solution.maximumRows(matrix2, numSelect2));
        
        // 测试用例3: 只能选择一列
        int[][] matrix3 = {{1,0,1},{0,1,0},{1,0,1}};
        int numSelect3 = 1;
        int expected3 = 1;
        assertEquals(expected3, solution.maximumRows(matrix3, numSelect3));
        
        // 测试用例4: 空矩阵
        int[][] matrix4 = {{0,0,0},{0,0,0},{0,0,0}};
        int numSelect4 = 1;
        int expected4 = 3;
        assertEquals(expected4, solution.maximumRows(matrix4, numSelect4));
        
        // 测试用例5: 选择0列
        int[][] matrix5 = {{0,0,0},{0,0,0}};
        int numSelect5 = 0;
        int expected5 = 2;
        assertEquals(expected5, solution.maximumRows(matrix5, numSelect5));
    }
}