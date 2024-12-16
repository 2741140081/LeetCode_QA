package com.marks.leetcode.grid_bfs;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/13 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1162 {
    private int n;
    private int[][] pre;
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    /**
     * @Description: [
     * 你现在手里有一份大小为 n x n 的 网格 grid，上面的每个 单元格 都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地。
     *
     * 请你找出一个海洋单元格，这个海洋单元格到离它最近的陆地单元格的距离是最大的，并返回该距离。如果网格上只有陆地或者海洋，请返回 -1。
     *
     * 我们这里说的距离是「曼哈顿距离」（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个单元格之间的距离是 |x0 - x1| + |y0 - y1| 。
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDistance(int[][] grid) {
        int result;
        result = method_01(grid);
        result = method_02(grid);
        return result;
    }

    /**
     * @Description: [
     * 通过method_01, 想到另外一个方法, 通过每一个grid[i][j] = 1, 来更新整个距离
     * 额外一个空间int[][] distance, distance[i][j] = Math.max(distance[i][j], deep)
     * 这种感觉也是O(n^4)的时间复杂度, 放弃
     * 查看官解
     *
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] grid) {
        final int INF = 1000000;
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        int n = grid.length;
        int[][] d = new int[n][n];
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(new Comparator<int[]>() {
            public int compare(int[] status1, int[] status2) {
                return status1[0] - status2[0];
            }
        });

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                d[i][j] = INF;
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 1) {
                    d[i][j] = 0;
                    queue.offer(new int[]{0, i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] f = queue.poll();
            for (int i = 0; i < 4; ++i) {
                int nx = f[1] + dx[i], ny = f[2] + dy[i];
                if (!(nx >= 0 && nx < n && ny >= 0 && ny < n)) {
                    continue;
                }
                if (f[0] + 1 < d[nx][ny]) {
                    d[nx][ny] = f[0] + 1;
                    queue.offer(new int[]{d[nx][ny], nx, ny});
                }
            }
        }

        int ans = -1;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0) {
                    ans = Math.max(ans, d[i][j]);
                }
            }
        }

        return ans == INF ? -1 : ans;
    }

    /**
     * @Description: [
     * 暴力超时!!! 中等题也能超时!!!
     * 36/38
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        n = grid.length;
        int ans = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    pre = new int[n][n];
                    int len = bfsMaxLen(i, j, grid);
                    ans = Math.max(ans, len);
                    if (len == -1) {
                        return ans;
                    }
                }
            }
        }
        return ans;
    }

    private int bfsMaxLen(int i, int j, int[][] grid) {
        int len = -1;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j, 0});
        pre[i][j] = 1;
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_i = array[0];
            int curr_j = array[1];
            int deep = array[2];
            if (grid[curr_i][curr_j] == 1) {
                len = deep;
                return len;
            }

            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (inArea(next_i, next_j, grid) && pre[next_i][next_j] == 0) {
                    queue.offer(new int[]{next_i, next_j, deep + 1});
                    pre[next_i][next_j] = 1; // 标记已添加队列的元素, 防止重复添加
                }
            }
        }
        return len;
    }

    private boolean inArea(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid.length) {
            return true;
        }
        return false;
    }

}
