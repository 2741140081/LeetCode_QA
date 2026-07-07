package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_909 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/6 16:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_909 {

    /**
     * @Description:
     * 给你一个大小为 n x n 的整数矩阵 board ，方格按从 1 到 n2 编号，编号遵循 转行交替方式 ，
     * 从左下角开始 （即，从 board[n - 1][0] 开始）的每一行改变方向。
     * 你一开始位于棋盘上的方格  1。每一回合，玩家需要从当前方格 curr 开始出发，按下述要求前进：
     * 选定目标方格 next ，目标方格的编号在范围 [curr + 1, min(curr + 6, n^2)] 。
     * 该选择模拟了掷 六面体骰子 的情景，无论棋盘大小如何，玩家最多只能有 6 个目的地。
     * 传送玩家：如果目标方格 next 处存在蛇或梯子，那么玩家会传送到蛇或梯子的目的地。否则，玩家传送到目标方格 next 。
     * 当玩家到达编号 n^2 的方格时，游戏结束。
     * 如果 board[r][c] != -1 ，位于 r 行 c 列的棋盘格中可能存在 “蛇” 或 “梯子”。
     * 那个蛇或梯子的目的地将会是 board[r][c]。编号为 1 和 n^2 的方格不是任何蛇或梯子的起点。
     * 注意，玩家在每次掷骰的前进过程中最多只能爬过蛇或梯子一次：就算目的地是另一条蛇或梯子的起点，玩家也 不能 继续移动。
     * 举个例子，假设棋盘是 [[-1,4],[-1,3]] ，第一次移动，玩家的目标方格是 2 。
     * 那么这个玩家将会顺着梯子到达方格 3 ，但 不能 顺着方格 3 上的梯子前往方格 4 。
     * （简单来说，类似飞行棋，玩家掷出骰子点数后移动对应格数，遇到单向的路径（即梯子或蛇）可以直接跳到路径的终点，但如果多个路径首尾相连，也不能连续跳多个路径）
     * 返回达到编号为 n^2 的方格所需的最少掷骰次数，如果不可能，则返回 -1。
     * tips:
     * n == board.length == board[i].length
     * 2 <= n <= 20
     * board[i][j] 的值是 -1 或在范围 [1, n^2] 内
     * 编号为 1 和 n^2 的方格上没有蛇或梯子
     * @param: board
     * @return int
     * @author marks
     * @CreateDate: 2026/07/06 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int snakesAndLadders(int[][] board) {
        int result;
        result = method_01(board);
        return result;
    }

    /**
     * @Description:
     * 1. 单源最短路径问题, 使用 Dijkstra算法, 构建一个二维数组 int[][] dist = new int[n][n]
     * 2. 构建一个 List<int[]> list = new ArrayList<>(); 类似与路径 1 ~ n^2, 将所有路径按照顺序添加到 list 中,
     * 方便后续执行掷色子的操作
     * AC: 13ms/45.82MB
     * @param: board
     * @return int
     * @author marks
     * @CreateDate: 2026/07/06 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] board) {
        int INF = Integer.MAX_VALUE / 2;
        int n = board.length;
        // 构建路径 List
        List<int[]> list = new ArrayList<>();
        // 从左下角开始遍历
        int col = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (col % 2 == 0) {
                // 从 0 ~ n - 1 开始遍历
                for (int j = 0; j < n; j++) {
                    list.add(new int[]{i, j});
                }
            } else {
                // 从 n - 1 ~ 0 遍历
                for (int j = n - 1; j >= 0; j--) {
                    list.add(new int[]{i, j});
                }
            }
            col++;
        }
        int m = list.size();
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[n - 1][0] = 0;
        // 创建优先队列
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.offer(new int[]{0, 0}); // {spend, idx}, 其中 idx 表示在 list 中的索引
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int spend = curr[0], idx = curr[1];
            // 判断是否是终点
            if (idx == m - 1) {
                return spend;
            }
            // 执行掷色子 1 ~ 6
            for (int k = 1; k <= 6; k++) {
                int nextIdx = idx + k;
                if (nextIdx >= list.size()) { // 越界
                    break;
                }
                int nextI = list.get(nextIdx)[0], nextJ = list.get(nextIdx)[1];
                if (board[nextI][nextJ] != -1) {
                    // 蛇或梯子
                    nextIdx = board[nextI][nextJ] - 1;
                    // 更新 nextI nextJ
                    nextI = list.get(nextIdx)[0];
                    nextJ = list.get(nextIdx)[1];
                    if (dist[nextI][nextJ] > spend + 1) {
                        dist[nextI][nextJ] = spend + 1;
                        queue.offer(new int[]{spend + 1, nextIdx});
                    }
                } else {
                    if (dist[nextI][nextJ] > spend + 1) {
                        dist[nextI][nextJ] = spend + 1;
                        queue.offer(new int[]{spend + 1, nextIdx});
                    }
                }
            }
        }

        return -1;
    }

}
