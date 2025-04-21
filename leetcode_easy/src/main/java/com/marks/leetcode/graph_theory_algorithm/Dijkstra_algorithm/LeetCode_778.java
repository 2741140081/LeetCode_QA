package com.marks.leetcode.graph_theory_algorithm.Dijkstra_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/3 15:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_778 {
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int n;

    /**
     * @Description: [
     * <p></>在一个 n x n 的整数矩阵 grid 中，每一个方格的值 grid[i][j] 表示位置 (i, j) 的平台高度。
     *
     * <p>当开始下雨时，在时间为 t 时，水池中的水位为 t 。你可以从一个平台游向四周相邻的任意一个平台，但是前提是此时水位必须同时淹没这两个平台。
     * <p>假定你可以瞬间移动无限距离，也就是默认在方格内部游动是不耗时的。当然，在你游泳的时候你必须待在坐标方格里面。
     *
     * <p>你从坐标方格的左上平台 (0，0) 出发。返回 你到达坐标方格的右下平台 (n-1, n-1) 所需的最少时间 。
     * <p>tips:
     * n == grid.length
     * n == grid[i].length
     * 1 <= n <= 50
     * 0 <= grid[i][j] < n^2
     * grid[i][j] 中每个值 均无重复
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int swimInWater(int[][] grid) {
//        int result = method_01(grid);
        int result = method_02(grid);
        return result;
    }

    /**
     * @Description: [
     * 查看官解后用Dijkstra_algorithm 优先队列解决
     * AC:15ms/43.58MB
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] grid) {
        n = grid.length;
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        boolean[][] visited = new boolean[n][n];
        int ans = 0;
        queue.offer(new int[] {grid[0][0], 0, 0});
        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int value = curr_node[0];
            int x = curr_node[1];
            int y = curr_node[2];
            if (visited[x][y]) {
                continue;
            }
            visited[x][y] = true;
            if (x == n - 1 && y == n - 1) {
                break;
            }
            ans = Math.max(ans, value);
            for (int[] dir : dirs) {
                int next_x = x + dir[0];
                int next_y = y + dir[1];
                if (inArea(next_x, next_y)) {
                    if (!visited[next_x][next_y]) {
                        queue.offer(new int[]{grid[next_x][next_y], next_x, next_y});
                    }
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 还行吧, 不是用的 Dijkstra_algorithm, 而是使用二分法(相当于枚举t, 判断在t时间下, 是否可达, 是否可达用的BFS)
     * AC:11ms/44.16MB
     * 想不到怎么Dijkstra_algorithm 来解决, 好像没有什么头绪, 看看官解怎么做
     * ]
     * @param grid
     * @return int
     * @author marks
     * @CreateDate: 2025/1/3 15:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        n = grid.length;
        int left = Math.max(grid[0][0], grid[n - 1][n - 1]);
        int right = 0;
        for (int i = 0; i < n; i++) {
            right = Math.max(right, Arrays.stream(grid[i]).max().getAsInt());
        }
        while (right - left > 1) {
            int mid = (right - left) / 2 + left;
            boolean flag = checkIsReach(grid, mid);
            if (flag) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        while (right > 0) {
            if (!checkIsReach(grid, right - 1)) {
                return right;
            }
            right--;
        }
        return -1;
    }

    private boolean checkIsReach(int[][] grid, int t) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        if (grid[0][0] <= t) {
            queue.offer(new int[]{0, 0});
            visited[0][0] = true;
        }
        while (!queue.isEmpty()) {
            int[] curr_node = queue.poll();
            int x = curr_node[0];
            int y = curr_node[1];
            if (visited[n - 1][n - 1]) {
                return true;
            }
            for (int[] dir : dirs) {
                int next_x = x + dir[0];
                int next_y = y + dir[1];
                if (inArea(next_x, next_y) && grid[next_x][next_y] <= t) {
                    if (!visited[next_x][next_y]) {
                        queue.offer(new int[] {next_x, next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }
        }
        return false;
    }

    private boolean inArea(int i, int j) {
        if (i >= 0 && j >= 0 && i < n && j < n) {
            return true;
        }
        return false;
    }
}
