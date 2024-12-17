package com.marks.leetcode.grid_bfs;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 9:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1293 {
    private int[][] pre;
    private int[][] dirs = {{-1, 0},  {0, -1}, {0, 1}, {1, 0}};
    /**
     * @Description: [
     * 给你一个 m * n 的网格，其中每个单元格不是 0（空）就是 1（障碍物）。每一步，您都可以在空白单元格中上、下、左、右移动。
     *
     * 如果您 最多 可以消除 k 个障碍物，请找出从左上角 (0, 0) 到右下角 (m-1, n-1) 的最短路径，并返回通过该路径所需的步数。
     * 如果找不到这样的路径，则返回 -1 。
     *
     * tips:
     * grid.length == m
     * grid[0].length == n
     * 1 <= m, n <= 40
     * 1 <= k <= m*n
     * grid[i][j] 是 0 或 1
     * grid[0][0] == grid[m-1][n-1] == 0
     * ]
     * @param grid 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/17 9:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestPath(int[][] grid, int k) {
        int result;
        result = method_01(grid, k);
        return result;
    }

    /**
     * @Description: [
     * 解答错误,
     * E1:
     * {0,1,0,0,0,1,0,0},
     * {0,1,0,1,0,1,0,1},
     * {0,0,0,1,0,0,1,0}}
     *
     * 在E1案例中, 由于标记[1, 2]节点, 导致下面那条路无法走通,
     * Result: 53/55
     * 查看题解后修改, 思路为,
     * 1. 重新添加节点到队列中, 如果某一个节点的障碍数量比当前pre[i][j] = k 的数量少, 那么更新pre[i][j], 并且将节点入队
     * 2. 修改pre[][]二维数组的初始值为 -1, 即不存在的点, 因为我需要pre[i][j] 存储障碍物的数量,
     * 所以需要-1这个不存在的数, 来判断是否遍历了该节点, 如果选择pre[i][j] 初始化为0,
     * 那么可能会有障碍物是0的节点, 无法判断是否已经遍历过该节点
     *
     * 3. 总结: 此题关键点在于, 将障碍物更少的节点, 但是已经遍历过的节点再次入队
     * AC:9ms/41.98MB
     * ]
     * @param grid
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/17 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        pre = new int[m][n];
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] != o2[0]) {
                    return o1[0] - o2[0];
                } else {
                    return o1[1] - o2[1];
                }
            }
        });
        for (int i = 0; i < m; i++) {
            Arrays.fill(pre[i], -1);// 初始化为-1, 不可能存在-1数量的障碍物, 方便判断是否节点未遍历
        }
        queue.offer(new int[] {0, 0, 0, 0}); // 起始位置, 倒数第一个0为路径长度, 第二个为经过的障碍物数量
        pre[0][0] = 0; // 标记已遍历, 且障碍物数量为0

        int ans = -1;
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int deep = array[0];
            int count = array[1];
            int curr_i = array[2];
            int curr_j = array[3];
            if (curr_i == m - 1 && curr_j == n - 1) {
                return deep;
            }
            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];

                if (inArea(next_i, next_j, grid) && count + grid[next_i][next_j] <= k) {
                    int next_count = count + grid[next_i][next_j];
                    // 未遍历的节点入队
                    if (pre[next_i][next_j] == -1) {
                        queue.offer(new int[]{deep + 1, next_count, next_i, next_j});
                        pre[next_i][next_j] = next_count;
                    }
                    // 障碍物更少的节点, 但是已经遍历过的节点再次入队
                    if (pre[next_i][next_j] > next_count) {
                        queue.offer(new int[]{deep + 1, next_count, next_i, next_j});
                        pre[next_i][next_j] = next_count;
                    }
                }
            }
        }
        return ans;
    }

    private boolean inArea(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }
}
