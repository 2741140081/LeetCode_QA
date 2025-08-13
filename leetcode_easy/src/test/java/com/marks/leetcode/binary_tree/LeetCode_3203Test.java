package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_3203 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 13, 2025</pre>
 */
public class LeetCode_3203Test {

    /**
     * Method: minimumDiameterAfterMerge(int[][] edges1, int[][] edges2)
     */
    @Test
    public void testMinimumDiameterAfterMerge() throws Exception {
        LeetCode_3203 solution = new LeetCode_3203();

        // Test case 1: Example from problem
        int[][] edges1 = {{0,1},{0,2},{0,3},{2,4},{2,5},{3,6},{2,7}};
        int[][] edges2 = {{0,1},{0,2},{0,3},{2,4},{2,5},{3,6},{2,7}};
        assertEquals(5, solution.minimumDiameterAfterMerge(edges1, edges2));

        // Test case 2: Two simple linear trees
        int[][] edges3 = {{0,1}};
        int[][] edges4 = {{0,1}};
        assertEquals(3, solution.minimumDiameterAfterMerge(edges3, edges4));
    }

    /**
     * Method: method_01(int[][] edges1, int[][] edges2)
     */
    @Test
    public void testMethod_01() throws Exception {
        // This is a private method, so we test it indirectly through the public method
        LeetCode_3203 solution = new LeetCode_3203();

        // Test with simple case
        int[][] edges1 = {{0,1}};
        int[][] edges2 = {{0,1},{1,2}};
        assertEquals(3, solution.minimumDiameterAfterMerge(edges1, edges2));
    }
}