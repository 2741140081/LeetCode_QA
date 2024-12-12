package com.marks.leetcode.gird_dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/6 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_200 {
    /**
     * @Description: [
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     *
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     *
     * 此外，你可以假设该网格的四条边均被水包围。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 300
     * grid[i][j] 的值为 '0' 或 '1'
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/6 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numIslands(char[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: [
     * 查看题解后明白dfs的套路,
     * 1.
     * 如果是二叉树, dfs(left.next); dfs(right.next)
     * 如果是网格, dfs(i +/- 1, j +/- 1), 往4个方向遍历
     *
     * 2.
     * 及时标记网格中已经遍历过的网格, 例如grid[i][j] = 2, 这种
     *
     * 3. 不管任何情况, 先跳过去, 如果越界, 再返回
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/6 17:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[][] grid) {
        int ans = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    ans++;
                    dfs(grid, i, j);
                }
            }
        }
        return ans;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (!inArea(grid, i, j)) {
            return;
        }
        if (grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '2'; // 标记为该网格已经遍历
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }

    private boolean inArea(char[][] grid, int i, int j) {
        if (i >= 0 && i < grid.length && j >= 0 && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
