package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_417 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/16 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_417 {

    /**
     * @Description:
     * 有一个 m × n 的矩形岛屿，与 太平洋 和 大西洋 相邻。
     * “太平洋” 处于大陆的左边界和上边界，而 “大西洋” 处于大陆的右边界和下边界。
     * 这个岛被分割成一个由若干方形单元格组成的网格。给定一个 m x n 的整数矩阵 heights ， heights[r][c] 表示坐标 (r, c) 上单元格 高于海平面的高度 。
     * 岛上雨水较多，如果相邻单元格的高度 小于或等于 当前单元格的高度，雨水可以直接向北、南、东、西流向相邻单元格。
     * 水可以从海洋附近的任何单元格流入海洋。
     * 返回网格坐标 result 的 2D 列表 ，其中 result[i] = [ri, ci] 表示雨水从单元格 (ri, ci) 流动 既可流向太平洋也可流向大西洋 。
     * tips:
     * m == heights.length
     * n == heights[r].length
     * 1 <= m, n <= 200
     * 0 <= heights[r][c] <= 10^5
     * @param: heights
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/16 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        List<List<Integer>> result;
        result = method_01(heights);
        return result;
    }


    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    /**
     * @Description:
     * 输入: heights = [
     * [1,2,2,3,5],
     * [3,2,3,4,4],
     * [2,4,5,3,1],
     * [6,7,1,4,5],
     * [5,1,1,2,4]]
     * 输出: [[0,4],[1,3],[1,4],[2,2],[3,0],[3,1],[4,0]]
     * 1. 创建一个二维数组，用于记录每个单元格的状态，0 表示未访问，1 表示可流向太平洋，2 表示可流向大西洋, 3 既可流向太平洋又可流向大西洋
     * 2. Fail: 113/114
     * 3. 修改思路 int[][] pacific 记录太平洋, int[][] atlantic 记录大西洋
     * AC: 4ms/46.73MB
     * @param: heights
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2025/12/16 10:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[][] heights) {
        List<List<Integer>> ans = new ArrayList<>();
        int m = heights.length;
        int n = heights[0].length;
        // 创建二维数组记录太平洋的状态
        boolean[][] pacific = new boolean[m][n];
        // 创建二维数组记录大西洋的状态
        boolean[][] atlantic = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            // 第一列
            dfs(heights, i, 0, pacific);
        }
        for (int j = 0; j < n; j++) {
            // 第一行
            dfs(heights, 0, j, pacific);
        }
        for (int i = 0; i < n; i++) {
            // 最后一行
            dfs(heights, m - 1, i, atlantic);
        }
        for (int j = 0; j < m; j++) {
            // 最后一列
            dfs(heights, j, n - 1, atlantic);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(i);
                    list.add(j);
                    ans.add(list);
                }
            }
        }
        return ans;
    }

    private void dfs(int[][] heights, int i, int j, boolean[][] ocean) {
        if (ocean[i][j]) {
            return;
        }
        ocean[i][j] = true;

        for (int[] dir : dirs) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (inArea(heights, newI, newJ) && heights[newI][newJ] >= heights[i][j]) {
                dfs(heights, newI, newJ, ocean);
            }
        }
    }

    private boolean inArea(int[][] heights, int i, int j) {
        return i >= 0 && i < heights.length && j >= 0 && j < heights[0].length;
    }
}
