package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 11:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3462 {
    /**
     * @Description:
     * 给你一个大小为 n x m 的二维矩阵 grid ，以及一个长度为 n 的整数数组 limits ，和一个整数 k 。
     * 你的目标是从矩阵 grid 中提取出 至多 k 个元素，并计算这些元素的最大总和，提取时需满足以下限制：
     *
     * 从 grid 的第 i 行提取的元素数量不超过 limits[i] 。
     *
     * 返回最大总和。
     * @param grid 
     * @param limits 
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/3/28 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxSum(int[][] grid, int[] limits, int k) {
        long result;
        result = method_01(grid, limits, k);
        return result;
    }

    /**
     * @Description:
     * 模拟 + 队列超时
     * TLE: 超时, 故意搞 593 / 594
     *
     * 贪心
     * AC: 143ms/70.75MB
     * @param grid 
     * @param limits 
     * @param k 
     * @return long
     * @author marks
     * @CreateDate: 2025/3/28 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] grid, int[] limits, int k) {
        int n = grid.length;
        int m = grid[0].length;
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Arrays.sort(grid[i]);
            int count = limits[i];
            for (int j = m - 1; count > 0 && j >= 0; j--, count--) {
                list.add((long) grid[i][j]);
            }
        }
        long ans = 0;
        list.sort(Collections.reverseOrder());
        for (int i = 0; i < k; i++) {
            ans += list.get(i);
        }
        return ans;
    }
}
