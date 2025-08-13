package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LeetCode_2246 Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>8月 13, 2025</pre>
 */
public class LeetCode_2246Test {

    /**
     *
     * Method: longestPath(int[] parent, String s)
     *
     */
    @Test
    public void testLongestPath() throws Exception {
        LeetCode_2246 solution = new LeetCode_2246();

        // Test case 1: Example from problem
        int[] parent1 = {-1, 0, 0, 1, 1, 2};
        String s1 = "abacbe";
        assertEquals(3, solution.longestPath(parent1, s1));

        // Test case 2: Example from problem
        int[] parent2 = {-1, 0, 0, 0};
        String s2 = "aabc";
        assertEquals(3, solution.longestPath(parent2, s2));

        // Test case 3: Single node
        int[] parent3 = {-1};
        String s3 = "a";
        assertEquals(1, solution.longestPath(parent3, s3));

        // Test case 4: All nodes have different characters
        int[] parent4 = {-1, 0, 0, 1, 1};
        String s4 = "abcde";
        assertEquals(4, solution.longestPath(parent4, s4));

        // Test case 5: All nodes have same characters
        int[] parent5 = {-1, 0, 0, 1, 1};
        String s5 = "aaaaa";
        assertEquals(1, solution.longestPath(parent5, s5));

        // Test case 6: Linear tree with alternating characters
        int[] parent6 = {-1, 0, 1, 2, 3};
        String s6 = "ababa";
        assertEquals(5, solution.longestPath(parent6, s6));
        
        // Test case 7: Complex tree with multiple branches
        int[] parent7 = {-1, 0, 0, 1, 1, 2, 2, 3, 3};
        String s7 = "abcdefghe";
        assertEquals(6, solution.longestPath(parent7, s7));
        
        // Test case 8: All characters the same in a complex tree
        int[] parent8 = {-1, 0, 0, 1, 1, 2, 2, 3, 3};
        String s8 = "aaaaaaaaa";
        assertEquals(1, solution.longestPath(parent8, s8));
        
        // Test case 9: Path goes through root with different characters
        int[] parent9 = {-1, 0, 0, 1, 2};
        String s9 = "abcde";
        assertEquals(5, solution.longestPath(parent9, s9));
        
        // Test case 10: Large tree with zigzag pattern
        int[] parent10 = {-1, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4};
        String s10 = "abcdefghijk";
        assertEquals(6, solution.longestPath(parent10, s10));
    }

    /**
     *
     * Method: method_01(int[] parent, String s)
     *
     */
    @Test
    public void testMethod_01() throws Exception {
        // This is a private method, so we test it indirectly through the public method
        LeetCode_2246 solution = new LeetCode_2246();

        // Test with a simple case
        int[] parent = {-1, 0, 0};
        String s = "abc";
        assertEquals(2, solution.longestPath(parent, s));
    }

    /**
     *
     * Method: buildGraph(int[] parent)
     *
     */
    @Test
    public void testBuildGraph() throws Exception {
        // This is a private method, so we test it indirectly
        LeetCode_2246 solution = new LeetCode_2246();

        int[] parent = {-1, 0, 0, 1, 1};
        String s = "abcde";
        assertEquals(4, solution.longestPath(parent, s));
    }

    /**
     *
     * Method: dfs(int node, int parent, List<Integer>[] graph, String s)
     *
     */
    @Test
    public void testDfs() throws Exception {
        // This is a private method, so we test it indirectly
        LeetCode_2246 solution = new LeetCode_2246();

        // Test a case that requires DFS traversal
        int[] parent = {-1, 0, 0, 1, 2, 3};
        String s = "abcdef";
        assertEquals(6, solution.longestPath(parent, s));
    }
}