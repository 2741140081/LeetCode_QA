package com.marks.leetcode.array_hard;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2258 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/19 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2258 {

    /**
     * @Description:
     * 给你一个下标从 0 开始大小为 m x n 的二维整数数组 grid ，它表示一个网格图。每个格子为下面 3 个值之一：
     * 0 表示草地。
     * 1 表示着火的格子。
     * 2 表示一座墙，你跟火都不能通过这个格子。
     * 一开始你在最左上角的格子 (0, 0) ，你想要到达最右下角的安全屋格子 (m - 1, n - 1) 。每一分钟，你可以移动到 相邻 的草地格子。每次你移动 之后 ，着火的格子会扩散到所有不是墙的 相邻 格子。
     * 请你返回你在初始位置可以停留的 最多 分钟数，且停留完这段时间后你还能安全到达安全屋。如果无法实现，请你返回 -1 。如果不管你在初始位置停留多久，你 总是 能到达安全屋，请你返回 10^9 。
     * 注意，如果你到达安全屋后，火马上到了安全屋，这视为你能够安全到达安全屋。
     * 如果两个格子有共同边，那么它们为 相邻 格子。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 300
     * 4 <= m * n <= 2 * 10^4
     * grid[i][j] 是 0 ，1 或者 2 。
     * grid[0][0] == grid[m - 1][n - 1] == 0
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/08/19 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumMinutes(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    private int[][] memo; // 记忆化着火的最小时间
    private int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int m;
    private int n;
    private final int INF = Integer.MAX_VALUE / 2;
    /**
     * @Description:
     * AC: 17ms/47.3MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/08/19 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        m = grid.length;
        n = grid[0].length;
        // 构建队列
        Queue<int[]> queue = new ArrayDeque<>();
        memo = new int[m][n];
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
            Arrays.fill(dist[i], INF);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    memo[i][j] = 0;
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 2) {
                    memo[i][j] = -2;
                }
            }
        }

        int time = 1;
        // BFS, 构建火堆到达 (i, j) 的最少时间
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] p = queue.poll();
                for (int[] dir : dirs) {
                    int nextX = p[0] + dir[0];
                    int nextY = p[1] + dir[1];
                    if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && memo[nextX][nextY] == -1) {
                        memo[nextX][nextY] = time;
                        queue.offer(new int[]{nextX, nextY});
                    }
                }
            }
            time++;
        }
        // 判断 -1, 即能否到达 target
        int ans = -1;
        if (!BFS(0)) {
            return ans;
        }
        // 处理 10^9, 在 memo 中找到一条为 -1 的路径, 能够从 (0, 0) 到达 (m - 1, n - 1)
        boolean[][] visited = new boolean[m][n];
        if (memo[0][0] == -1 && memo[m - 1][n - 1] == -1) {
            queue.offer(new int[]{0, 0});
            visited[0][0] = true;
        }
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int x = p[0], y = p[1];
            if (x == m - 1 && y == n - 1) {
                break;
            }
            for (int[] dir : dirs) {
                int nextX = x + dir[0];
                int nextY = y + dir[1];
                if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n
                        && memo[nextX][nextY] == -1 && !visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    queue.offer(new int[]{nextX, nextY});
                }
            }
        }

        if (visited[m - 1][n - 1]) {
            // 返回 10^9
            return (int) 1e9;
        }
        // 使用二分法查找最大停留时间, 任意一个火堆只需要 m * n 可以到达任一点
        int left = 0, right = m * n;
        ans = 0;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (BFS(mid)) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }


        return ans;
    }

    private boolean BFS(int wait) {
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new ArrayDeque<>();
        if (memo[0][0] == -1 || wait < memo[0][0]) {
            queue.offer(new int[]{0, 0});
            visited[0][0] = true;
        }
        int time = wait + 1;
        while (!queue.isEmpty() && !visited[m - 1][n - 1]) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] p = queue.poll();
                int x = p[0], y = p[1];
                if (x == m - 1 && y == n - 1) {
                    break;
                }

                for (int[] dir : dirs) {
                    int nextX = x + dir[0];
                    int nextY = y + dir[1];
                    if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n) {
                        if((memo[nextX][nextY] > time || memo[nextX][nextY] == -1) && !visited[nextX][nextY]) {
                            visited[nextX][nextY] = true;
                            queue.offer(new int[]{nextX, nextY});
                        } else if (memo[nextX][nextY] == time && nextX == m - 1 && nextY == n - 1) { // 火和人同时到达安全屋, 则视为 true
                            return true;
                        }
                    }
                }
            }
            time++;
        }

        return visited[m - 1][n - 1];
    }

}
