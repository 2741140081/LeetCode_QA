package com.marks.leetcode.grid_bfs;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/13 10:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1926 {
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private int[][] pre;
    /**
     * @Description: [
     * 给你一个 m x n 的迷宫矩阵 maze （下标从 0 开始），矩阵中有空格子（用 '.' 表示）和墙（用 '+' 表示）。
     * 同时给你迷宫的入口 entrance ，用 entrance = [entrancerow, entrancecol] 表示你一开始所在格子的行和列。
     *
     * 每一步操作，你可以往 上，下，左 或者 右 移动一个格子。你不能进入墙所在的格子，你也不能离开迷宫。
     * 你的目标是找到离 entrance 最近 的出口。出口 的含义是 maze 边界 上的 空格子。entrance 格子 不算 出口。
     *
     * 请你返回从 entrance 到最近出口的最短路径的 步数 ，如果不存在这样的路径，请你返回 -1 。
     *
     * tips:
     * maze.length == m
     * maze[i].length == n
     * 1 <= m, n <= 100
     * maze[i][j] 要么是 '.' ，要么是 '+' 。
     * entrance.length == 2
     * 0 <= entrancerow < m
     * 0 <= entrancecol < n
     * entrance 一定是空格子。
     * ]
     * @param maze 
     * @param entrance
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 10:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int nearestExit(char[][] maze, int[] entrance) {
        int result;
        result = method_01(maze, entrance);
        return result;
    }

    /**
     * @Description: [
     * BFS标准模板题, BFS使用队列来进行FIFO, 可以保证找到答案的最小值,
     * 但是为什么会超时!!!
     * 156/194MB
     * 查看题解的评论后知道原因:
     * 在入队孩子以后，再将孩子标记 visited。可以避免超时。
     * 原因是，同一个孙子/女，可能被多个女/儿子 重复加入到队列中去。 导致做无用功。
     * [0, 1, 1]
     * [1, 2, 2]
     * [1, 2, 2]
     * 会重复添加到队列中, 导致超时, 需要添加一个额外数组, 标记已添加队列的元素, 防止重复添加
     * OK 修改后可以了
     * AC:4ms/44.77MB
     * ]
     * @param maze
     * @param entrance
     * @return int
     * @author marks
     * @CreateDate: 2024/12/13 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[][] maze, int[] entrance) {
        Deque<int[]> deque = new LinkedList<>();
        pre = new int[maze.length][maze[0].length];
        int ans = -1;
        deque.addFirst(new int[]{entrance[0], entrance[1], 0});
        pre[entrance[0]][entrance[1]] = 1; // 标记已遍历

        while (!deque.isEmpty()) {
            int[] array = deque.pollLast();
            int i = array[0];
            int j = array[1];
            int deep = array[2];
            // deep > 0 保证entrance[] 不会成为出口
            if (checkIsBorder(i, j, maze) && deep > 0) {
                ans = deep;
                return ans;
            }

            maze[i][j] = '+'; // 标记并且封印
            for (int[] dir : dirs) {
                int next_i = i + dir[0];
                int next_j = j + dir[1];

                if (inArea(next_i, next_j, maze) && pre[next_i][next_j] == 0 && maze[next_i][next_j] != '+') {
                    deque.addFirst(new int[]{next_i, next_j, deep + 1});
                    pre[next_i][next_j] = 1; // 标记已添加队列的元素, 防止重复添加
                }
            }
        }
        return ans;
    }

    private boolean checkIsBorder(int i, int j, char[][] maze) {
        if (i == 0 || j == 0 || i == maze.length - 1 || j == maze[0].length - 1) {
            return true;
        }
        return false;
    }

    private boolean inArea(int next_i, int next_j, char[][] maze) {
        if (next_i >= 0 && next_j >= 0 && next_i < maze.length && next_j < maze[0].length) {
            return true;
        }
        return false;
    }
}
