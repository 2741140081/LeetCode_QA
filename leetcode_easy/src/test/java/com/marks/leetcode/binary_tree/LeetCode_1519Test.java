package com.marks.leetcode.binary_tree;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class LeetCode_1519Test {

    @Test
    void testCountSubTreesExample1() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 7;
        int[][] edges = {{0,1},{0,2},{1,4},{1,5},{2,3},{2,6}};
        String labels = "abaedcd";
        int[] expected = {2,1,1,1,1,1,1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSubTreesExample2() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 4;
        int[][] edges = {{0,1},{1,2},{0,3}};
        String labels = "bbbb";
        int[] expected = {4,2,1,1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSubTreesExample3() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 5;
        int[][] edges = {{0,1},{0,2},{1,3},{0,4}};
        String labels = "aabab";
        int[] expected = {3,2,1,1,1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSubTreesSingleNode() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 1;
        int[][] edges = {};
        String labels = "a";
        int[] expected = {1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSubTreesTwoNodes() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 2;
        int[][] edges = {{0,1}};
        String labels = "aa";
        int[] expected = {2, 1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSubTreesLinearTree() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 4;
        int[][] edges = {{0,1},{1,2},{2,3}};
        String labels = "abcd";
        int[] expected = {1,1,1,1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }

    @Test
    void testCountSubTreesAllSameLabels() {
        LeetCode_1519 solution = new LeetCode_1519();
        int n = 4;
        int[][] edges = {{0,1},{1,2},{2,3}};
        String labels = "aaaa";
        int[] expected = {4,3,2,1};
        int[] actual = solution.countSubTrees(n, edges, labels);
        assertArrayEquals(expected, actual);
    }
}