package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_51 Tester.
 *
 * @author marks
 * @version 1.0
 * @since <pre>8月 25, 2025</pre>
 */
public class LeetCode_51Test {

    /**
     * Method: solveNQueens(int n)
     */
    @Test
    public void testSolveNQueensWithN1() throws Exception {
        // 测试 n = 1 的情况
        LeetCode_51 leetCode51 = new LeetCode_51();
        List<List<String>> result = leetCode51.solveNQueens(1);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).size());
        assertEquals("Q", result.get(0).get(0));
    }

    @Test
    public void testSolveNQueensWithN2() throws Exception {
        // 测试 n = 2 的情况，应该没有解
        LeetCode_51 leetCode51 = new LeetCode_51();
        List<List<String>> result = leetCode51.solveNQueens(2);
        assertEquals(0, result.size());
    }

    @Test
    public void testSolveNQueensWithN3() throws Exception {
        // 测试 n = 3 的情况，应该没有解
        LeetCode_51 leetCode51 = new LeetCode_51();
        List<List<String>> result = leetCode51.solveNQueens(3);
        assertEquals(0, result.size());
    }

    @Test
    public void testSolveNQueensWithN4() throws Exception {
        // 测试 n = 4 的情况，应该有两个解
        LeetCode_51 leetCode51 = new LeetCode_51();
        List<List<String>> result = leetCode51.solveNQueens(4);
        assertEquals(2, result.size());

        // 验证解的格式
        for (List<String> solution : result) {
            assertEquals(4, solution.size());
            for (String row : solution) {
                assertEquals(4, row.length());
            }
        }

        // 验证第一个解的内容
        List<String> firstSolution = result.get(0);
        assertEquals(".Q..", firstSolution.get(0));
        assertEquals("...Q", firstSolution.get(1));
        assertEquals("Q...", firstSolution.get(2));
        assertEquals("..Q.", firstSolution.get(3));

        // 验证第二个解的内容
        List<String> secondSolution = result.get(1);
        assertEquals("..Q.", secondSolution.get(0));
        assertEquals("Q...", secondSolution.get(1));
        assertEquals("...Q", secondSolution.get(2));
        assertEquals(".Q..", secondSolution.get(3));
    }

    @Test
    public void testSolveNQueensWithN8() throws Exception {
        // 测试 n = 8 的情况，应该有解
        LeetCode_51 leetCode51 = new LeetCode_51();
        List<List<String>> result = leetCode51.solveNQueens(8);
        assertTrue(result.size() > 0);
        
        // 验证解的格式
        for (List<String> solution : result) {
            assertEquals(8, solution.size());
            for (String row : solution) {
                assertEquals(8, row.length());
            }
        }
    }
}