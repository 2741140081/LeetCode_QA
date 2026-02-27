package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_8_2 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/27 14:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_8_2 {

    /**
     * @Description:
     * 设想有个机器人坐在一个网格的左上角，网格 r 行 c 列。机器人只能向下或向右移动，但不能走到一些被禁止的网格（有障碍物）。
     * 设计一种算法，寻找机器人从左上角移动到右下角的路径。
     * 网格中的障碍物和空位置分别用 1 和 0 来表示。
     * 返回一条可行的路径，路径由经过的网格的行号和列号组成。左上角为 0 行 0 列。如果没有可行的路径，返回空数组。
     * @param: obstacleGrid
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/02/27 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> pathWithObstacles(int[][] obstacleGrid) {
        List<List<Integer>> result;
        result = method_01(obstacleGrid);
        result = method_02(obstacleGrid);
        return result;
    }

    /**
     * @Description:
     * 1. 思考怎么用记忆化搜索
     * @param: obstacleGrid
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/02/27 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_02(int[][] obstacleGrid) {

        return null;
    }

    /**
     * @Description:
     * E1:
     * 输入：[[0,0,0],[0,1,0],[0,0,0]]
     * 输出：[[0,0],[0,1],[0,2],[1,2],[2,2]]
     * 1. 要求是找到一条可行的路径
     * 2. 深度优先搜索, 从(0, 0) 出发， 找到一条可行路径
     * dfs 还是超时!!!
     * 通过测试用例: 44/46
     * 添加visited数组, 防止重复访问
     * AC: 1ms/46.19MB
     * @param: obstacleGrid
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/02/27 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int m;
    private int n;
    private List<List<Integer>> ans;
    private boolean findPath;
    private int[][] dirs = new int[][]{{0, 1}, {1, 0}};
    private boolean[][] visited;

    private List<List<Integer>> method_01(int[][] obstacleGrid) {
        m = obstacleGrid.length;
        n = obstacleGrid[0].length;
        // 判断终点是否有障碍物
        ans = new ArrayList<>();
        findPath = false;
        visited = new boolean[m][n];
        if (obstacleGrid[m - 1][n - 1] == 1 || obstacleGrid[0][0] == 1) {
            return ans;
        }
        // 执行dfs
        List<List<Integer>> path = new ArrayList<>();
        path.add(List.of(0, 0));
        visited[0][0] = true;
        dfs(0, 0, obstacleGrid, path);
        return ans;
    }

    private void dfs(int i, int j, int[][] obstacleGrid, List<List<Integer>> path) {
        if (findPath) {
            return;
        }
        if (i == m - 1 && j == n - 1) {
            path.add(List.of(i, j));
            findPath = true;
            ans = new ArrayList<>(path);
            return;
        }

        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI < 0 || nextI >= m || nextJ < 0 || nextJ >= n || obstacleGrid[nextI][nextJ] == 1 || visited[nextI][nextJ]) {
                continue;
            }
            path.add(List.of(nextI, nextJ));
            visited[nextI][nextJ] = true;
            dfs(nextI, nextJ, obstacleGrid, path);
            path.remove(path.size() - 1);
        }
    }
}
