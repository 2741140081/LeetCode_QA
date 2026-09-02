package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3288 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/2 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3288 {

    /**
     * @Description:
     * 给你一个长度为 n 的二维整数数组 coordinates 和一个整数 k ，其中 0 <= k < n 。
     * coordinates[i] = [xi, yi] 表示二维平面里一个点 (xi, yi) 。
     * 如果一个点序列 (x1, y1), (x2, y2), (x3, y3), ..., (xm, ym) 满足以下条件，那么我们称它是一个长度为 m 的 上升序列 ：
     * 对于所有满足 1 <= i < m 的 i 都有 x_i < x_i+1 且 y_i < y_i+1 。
     * 对于所有 1 <= i <= m 的 i 对应的点 (xi, yi) 都在给定的坐标数组里。
     * 请你返回包含坐标 coordinates[k] 的 最长上升路径 的长度。
     *
     * tips:
     * 1 <= n == coordinates.length <= 10^5
     * coordinates[i].length == 2
     * 0 <= coordinates[i][0], coordinates[i][1] <= 10^9
     * coordinates 中的元素 互不相同 。
     * 0 <= k <= n - 1
     * @param: coordinates
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/02 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxPathLength(int[][] coordinates, int k) {
        int result;
        result = method_01(coordinates, k);
        return result;
    }

    /**
     * @Description:
     * 1. 需要包含坐标 coordinates[k], 需要用动态规划, 但是会有两个条件
     * 2. 先对 x 进行升序排序.
     * 3. 至于 y, 使用 LIS (Longest Increasing Subsequence) 的方法, 贪心 + 二分查找来解决
     * AC: 75ms/165.23MB
     * @param: coordinates
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/09/02 15:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] coordinates, int k) {
        int n = coordinates.length;
        int kx = coordinates[k][0];
        int ky = coordinates[k][1];
        List<Integer> g = new ArrayList<>();
        Arrays.sort(coordinates, (a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return b[1] - a[1];
            }
        });
        for (int[] p : coordinates) {
            int x = p[0];
            int y = p[1];
            if (x < kx && y < ky || x > kx && y > ky) {
                int j = Collections.binarySearch(g, y); // g 没有重复元素，可以用 binarySearch
                if (j < 0) {
                    j = -j - 1;
                }
                if (j < g.size()) {
                    g.set(j, y);
                } else {
                    g.add(y);
                }
            }
        }
        return g.size() + 1; // 算上 coordinates[k]
    }

}
