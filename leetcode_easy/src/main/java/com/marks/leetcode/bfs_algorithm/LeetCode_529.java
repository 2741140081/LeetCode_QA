package com.marks.leetcode.bfs_algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_529 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/22 16:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_529 {

    /**
     * @Description:
     * 让我们一起来玩扫雷游戏！
     * 给你一个大小为 m x n 二维字符矩阵 board ，表示扫雷游戏的盘面，其中：
     * 'M' 代表一个 未挖出的 地雷，
     * 'E' 代表一个 未挖出的 空方块，
     * 'B' 代表没有相邻（上，下，左，右，和所有4个对角线）地雷的 已挖出的 空白方块，
     * 数字（'1' 到 '8'）表示有多少地雷与这块 已挖出的 方块相邻，
     * 'X' 则表示一个 已挖出的 地雷。
     * 给你一个整数数组 click ，其中 click = [clickr, clickc] 表示在所有 未挖出的 方块（'M' 或者 'E'）中的下一个点击位置（clickr 是行下标，clickc 是列下标）。
     *
     * 根据以下规则，返回相应位置被点击后对应的盘面：
     *
     * 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X' 。
     * 如果一个 没有相邻地雷 的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的 未挖出 方块都应该被递归地揭露。
     * 如果一个 至少与一个地雷相邻 的空方块（'E'）被挖出，修改它为数字（'1' 到 '8' ），表示相邻地雷的数量。
     * 如果在此次点击中，若无更多方块可被揭露，则返回盘面。
     * @param: board
     * @param: click
     * @return char[][]
     * @author marks
     * @CreateDate: 2025/12/22 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public char[][] updateBoard(char[][] board, int[] click) {
        char[][] result;
        result = method_01(board, click);
        return result;
    }

    /**
     * @Description:
     * 1. 广度优先搜索, 从click开始, 直到没有可点击的方块为止
     * 2. 相邻的定义为九宫格, 即目标块的周围8个方块
     * 3. 需要一个visited数组, 用于记录是否访问过, 防止重复访问
     * 4. 处理当前块: 如果周围8个块中有地雷, 计算地雷数量, 更新当前块为地雷数量;
     * 将非地雷块和未访问的块添加到队列中, 并标记当前块为已访问
     * 5. 对于未访问的数字块, 应该不能将其周围块添加到队列中, 只更新数字块的地雷数量
     * 6. 广度优先搜索
     * AC: 6ms/46.15MB
     * @param: board
     * @param: click
     * @return char[][]
     * @author marks
     * @CreateDate: 2025/12/22 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private char[][] method_01(char[][] board, int[] click) {
        int m = board.length;
        int n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(click);
        visited[click[0]][click[1]] = true;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            if (board[x][y] == 'M') {
                // 踩到地雷
                board[x][y] = 'X';
                return board;
            } else if (board[x][y] == 'E') {
                // 当前块为空块
                int count = 0; // 计数地雷数量
                for (int[] dir : dirs) {
                    int nx = x + dir[0];
                    int ny = y + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && board[nx][ny] == 'M') {
                        count++;
                    }
                }
                if (count > 0) {
                    // 当前块为数字块
                    board[x][y] = (char) (count + '0');
                } else {
                    // 当前块为空块
                    board[x][y] = 'B';
                    for (int[] dir : dirs) {
                        int nx = x + dir[0];
                        int ny = y + dir[1];
                        if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                            queue.offer(new int[]{nx, ny});
                        }
                    }
                }
            }
            // 不需要处理其他情况, 因为board[click[0]][click[1]] 只能是'M' 或者 'E'
        }
        return board;
    }

}
