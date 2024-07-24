package com.marks.leetcode.gridDP;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LeetCode_2328 {
    public final int MOD = (int) 1e9 + 7;
    // 二维数组行
    private int m;
    // 二维数组列
    private int n;
    // 上、下、左、右移动
    public int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * @Description: [给你一个 m x n 的整数网格图 grid ，你可以从一个格子移动到 4 个方向相邻的任意一个格子。</p>
     * 请你返回在网格图中从 任意 格子出发，达到 任意 格子，且路径中的数字是 严格递增 的路径数目。</p>
     * 由于答案可能会很大，请将结果对 10^9 + 7 取余 后返回。</p>
     * 如果两条路径中访问过的格子不是完全相同的，那么它们视为两条不同的路径。]</p>
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/7/24 15:33</p>
     * @UpdateInfo: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPaths(int[][] grid) {
        int result = 0;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description: <p>[具体实现方式一:</p>
     * E1: 输入：grid = [[1,1],[3,4]]<p>
     * 输出：8<p>
     * 解释：严格递增路径包括：<p>
     * 长度为 1 的路径：[1]，[1]，[3]，[4] 。<p>
     * 长度为 2 的路径：[1 -> 3]，[1 -> 4]，[3 -> 4] 。<p>
     * 长度为 3 的路径：[1 -> 3 -> 4] 。<p>
     * <p>路径数目为 4 + 3 + 1 = 8 。]</p>
     * 思路:
     * 假设我用memo[m][n] 存储当前节点的递增路径数目(错误)
     * 正确因该是memo[m][n]存储出度
     * 判断下一个节点是否是递增节点, 是否memo[next_i][next_j] != 0;
     * memo[i, j]++
     * memo[i, j] +
     * 好像可以使用dfs + 拓扑排序队列来解决
     * 删除入度为1
     *
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/7/24 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        int[][] inDegrees = new int[m][n];
        // 我用dp存储递增路径数目, 并且将其初始化为1
        long[][] dp = new long[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i], 1);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : dirs) {
                    int row = i + dir[0];
                    int col = j + dir[1];
                    // 判断是否越界 以及 是否符合条件
                    if (row >= 0 && col >= 0 && row < m && col < n && grid[i][j] > grid[row][col]){
                        inDegrees[i][j]++;
                    }
                }
            }
        }
        // 将所有出度为0的节点添加到队列中, 值是当前元素的坐标
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (inDegrees[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int x = cell[0];
                int y = cell[1];
                for (int[] dir : dirs) {
                    int row = x + dir[0];
                    int col = y + dir[1];
                    // 判断是否越界 以及 是否符合条件
                    if (row >= 0 && col >= 0 && row < m && col < n && grid[row][col] > grid[x][y]){
                        inDegrees[row][col]--;
                        dp[row][col] = (dp[row][col] + dp[x][y]) % MOD;
                        if (inDegrees[row][col] == 0) {
                            queue.offer(new int[]{row, col});
                        }
                    }
                }
            }
        }
        long res = 0;
        for (int i = 0; i < dp.length; i++) {
            long sum = Arrays.stream(dp[i]).sum() % MOD;
            res = (res + sum) % MOD;
        }
        return (int) res;
    }
}
