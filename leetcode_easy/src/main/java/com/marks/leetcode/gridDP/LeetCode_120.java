package com.marks.leetcode.gridDP;

import java.util.List;

public class LeetCode_120 {
    /**
     * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
     * 每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。也就是说，
     * 如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int result = 0;
        result = method_01(triangle);
        return result;
    }

    private int method_01(List<List<Integer>> triangle) {
        return 0;
    }
}
