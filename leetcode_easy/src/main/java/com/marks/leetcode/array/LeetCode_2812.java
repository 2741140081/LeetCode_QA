package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2812 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/30 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2812 {

    /**
     * @Description:
     * 给你一个下标从 0 开始、大小为 n x n 的二维矩阵 grid ，其中 (r, c) 表示：
     * 如果 grid[r][c] = 1 ，则表示一个存在小偷的单元格
     * 如果 grid[r][c] = 0 ，则表示一个空单元格
     * 你最开始位于单元格 (0, 0) 。在一步移动中，你可以移动到矩阵中的任一相邻单元格，包括存在小偷的单元格。
     * 矩阵中路径的 安全系数 定义为：从路径中任一单元格到矩阵中任一小偷所在单元格的 最小 曼哈顿距离。
     * 返回所有通向单元格 (n - 1, n - 1) 的路径中的 最大安全系数 。
     * 单元格 (r, c) 的某个 相邻 单元格，是指在矩阵中存在的 (r, c + 1)、(r, c - 1)、(r + 1, c) 和 (r - 1, c) 之一。
     * 两个单元格 (a, b) 和 (x, y) 之间的 曼哈顿距离 等于 | a - x | + | b - y | ，其中 |val| 表示 val 的绝对值。
     *
     * tips:
     * 1 <= grid.length == n <= 400
     * grid[i].length == n
     * grid[i][j] 为 0 或 1
     * grid 至少存在一个小偷
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/06/30 11:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumSafenessFactor(List<List<Integer>> grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 安全系数的定义是路径中任一一个单元格到任一一个小偷所在单元个的最小曼哈顿距离
     * 2. 能否根据所有的小偷坐标, 通过 bfs 的方式求出一个 int[][] dist 数组, dist[i][j] = k, 表示(i, j) 到 任一一个小偷最小的曼哈顿距离为 k
     * 3. 然后再 dist[][] 数组中找到一条路径 从 (0, 0) 到 (n - 1, n - 1), 要求改路径上面每个单元格的安全系数都是最大的
     * 超时: 通过的测试用例 1035/1036. 想不到更好的处理方法, 查看题解.
     * 4. 超时主要原因是构建 dist 数组时使用的是单源BFS, 需要使用多源BFS, 修改代码
     * AC: 153ms/118.38MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/06/30 11:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private int method_01(List<List<Integer>> grid) {
        int n = grid.size();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }
        // 创建队列, 存储小偷坐标
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid.get(i).get(j) == 1) {
                    // 当前位置是小偷
                    dist[i][j] = 0;
                    queue.offer(new int[] {i, j});
                }
            }
        }
        // 执行多源BFS, 更新 dist 数组
        executeBFS(dist, queue);
        // 根据 dist 数组, 找到一条最大的路径, 类似于 Dijkstra 算法
        int[][] minVal = new int[n][n]; // minVal[i][j] = k, 表示从 (0, 0) 到达 (i, j) 的路径中最大的最小值为 k; 初始化设置为 -1
        for (int i = 0; i < n; i++) {
            Arrays.fill(minVal[i], -1);
        }
        // 创建优先队列, 降序对 a[2]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[2] - a[2]);
        pq.offer(new int[] {0, 0, dist[0][0]});
        minVal[0][0] = dist[0][0];
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int curr_i = curr[0], curr_j = curr[1], curr_val = curr[2];
            // 判断是否是终点
            if (curr_i == n - 1 && curr_j == n - 1) {
                return curr_val;
            }
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (next_i < 0 || next_i >= n || next_j < 0 || next_j >= n) {
                    continue;
                }
                // 到达下一个 nextVal 值
                int nextVal = Math.min(curr_val, dist[next_i][next_j]);
                if (minVal[next_i][next_j] < nextVal) {
                    minVal[next_i][next_j] = nextVal;
                    pq.offer(new int[] {next_i, next_j, nextVal});
                }
            }
        }

        return 0;
    }

    private void executeBFS(int[][] dist, Queue<int[]> queue) {
        int n = dist.length;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int curr_i = curr[0], curr_j = curr[1];
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (next_i < 0 || next_i >= n || next_j < 0 || next_j >= n || dist[next_i][next_j] != -1) {
                    continue;
                }
                int nextVal = dist[curr_i][curr_j] + 1;
                dist[next_i][next_j] = nextVal;
                queue.offer(new int[] {next_i, next_j});
            }
        }
    }

}
