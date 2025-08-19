package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LCP_51 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 18, 2025</pre>
 */
public class LCP_51Test {

    /**
     * Method: perfectMenu(int[] materials, int[][] cookbooks, int[][] attribute, int limit)
     */
    @Test
    public void testPerfectMenu() {
        LCP_51 lcp51 = new LCP_51();
        
        // 测试用例1: 题目示例
        int[] materials1 = {3, 2, 4, 1, 2};
        int[][] cookbooks1 = {{1, 1, 0, 1, 2}, {2, 1, 4, 0, 0}, {3, 2, 4, 1, 0}};
        int[][] attribute1 = {{3, 2}, {2, 4}, {7, 6}};
        int limit1 = 5;
        int expected1 = 7;
        assertEquals(expected1, lcp51.perfectMenu(materials1, cookbooks1, attribute1, limit1));
        
        // 测试用例2: 无法满足饱腹感要求
        int[] materials2 = {1, 1, 1, 1, 1};
        int[][] cookbooks2 = {{1, 1, 1, 1, 1}, {2, 2, 2, 2, 2}};
        int[][] attribute2 = {{1, 1}, {2, 2}};
        int limit2 = 5;
        int expected2 = -1;
        assertEquals(expected2, lcp51.perfectMenu(materials2, cookbooks2, attribute2, limit2));
        
        // 测试用例3: 刚好满足饱腹感要求
        int[] materials3 = {2, 2, 2, 2, 2};
        int[][] cookbooks3 = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
        int[][] attribute3 = {{3, 2}, {4, 3}};
        int limit3 = 5;
        int expected3 = 7; // 选择两个料理，美味度3+4=7，饱腹感2+3=5
        assertEquals(expected3, lcp51.perfectMenu(materials3, cookbooks3, attribute3, limit3));
        
        // 测试用例4: 只能选择一个料理
        int[] materials4 = {3, 3, 3, 3, 3};
        int[][] cookbooks4 = {{3, 3, 3, 3, 3}, {1, 1, 1, 1, 1}};
        int[][] attribute4 = {{10, 6}, {1, 1}};
        int limit4 = 5;
        int expected4 = 10;
        assertEquals(expected4, lcp51.perfectMenu(materials4, cookbooks4, attribute4, limit4));
    }
}