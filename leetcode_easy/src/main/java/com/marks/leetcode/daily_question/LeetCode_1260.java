package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1260 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/20 11:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1260 {

    /**
     * @Description:
     * 给你一个 m 行 n 列的二维网格 grid 和一个整数 k。你需要将 grid 迁移 k 次。
     * 每次「迁移」操作将会引发下述活动：
     * 位于 grid[i][j]（j < n - 1）的元素将会移动到 grid[i][j + 1]。
     * 位于 grid[i][n - 1] 的元素将会移动到 grid[i + 1][0]。
     * 位于 grid[m - 1][n - 1] 的元素将会移动到 grid[0][0]。
     * 请你返回 k 次迁移操作后最终得到的 二维网格。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m <= 50
     * 1 <= n <= 50
     * -1000 <= grid[i][j] <= 1000
     * 0 <= k <= 100
     * @param: grid
     * @param: k
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/07/20 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        List<List<Integer>> result;
        result = method_01(grid, k);
        return result;
    }

    /**
     * @Description:
     * 1. 由于数据范围很小, 所以创建一个空矩阵, 存储k次迁移后的数据
     * AC: 4ms/46.28MB
     * @param: grid
     * @param: k
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/07/20 11:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        int sum = m * n; // 最大移动次数
        k = k % sum;
        // 创建一个空矩阵, 存储k次迁移后的数据
        int[][] copyGrid = new int[m][n];
        // 遍历 grid
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 向下移动行数
                int newI = (i + (j + k) / n) % m;
                // 向右移动列数
                int newJ = (j + k) % n;
                copyGrid[newI][newJ] = grid[i][j];
            }
        }
        // 将 int[][] 转为 List<List<Integer>>
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                list.add(copyGrid[i][j]);
            }
            result.add(list);
        }

        return result;
    }

}
