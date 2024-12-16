package com.marks.leetcode.grid_bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/16 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_542 {
    private int[][] pre;
    private int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    /**
     * @Description: [
     * 给定一个由 0 和 1 组成的矩阵 mat ，请输出一个大小相同的矩阵，其中每一个格子是 mat 中对应位置元素到最近的 0 的距离。
     *
     * 两个相邻元素间的距离为 1 。
     *
     * tips:
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 10^4
     * 1 <= m * n <= 10^4
     * mat[i][j] is either 0 or 1.
     * mat 中至少有一个 0
     * ]
     * @param mat
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/16 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] updateMatrix(int[][] mat) {
        int[][] result;
//        result = method_01(mat);
        result = method_02(mat);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * [
     * [0,0,0],
     * [0,1,0],
     * [1,1,1]]
     * AC:14ms/46.11MB
     * ]
     * @param mat
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/16 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_02(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int[][] ans = new int[m][n];
        pre = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                    pre[i][j] = 1;
                }
            }
        }
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int i = array[0];
            int j = array[1];
            pre[i][j] = 1;
            for (int[] dir : dirs) {
                int next_i = i + dir[0];
                int next_j = j + dir[1];

                if (inArea(next_i, next_j, mat) && pre[next_i][next_j] == 0) {
                    ans[next_i][next_j] = ans[i][j] + 1;
                    queue.offer(new int[] {next_i, next_j});
                    pre[next_i][next_j] = 1;
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * 感觉直接暴力BFS会超时吧, 不管了, 先试一试
     * 果然超时!!!
     * 49/50
     * ]
     * @param mat
     * @return int[][]
     * @author marks
     * @CreateDate: 2024/12/16 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;

        int[][] ans = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != 0) {
                    pre = new int[m][n];
                    ans[i][j] = BFSMinDistance(mat, i, j);
                }
            }
        }
        return ans;
    }

    private int BFSMinDistance(int[][] mat, int i, int j) {
        int len = -1;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j, 0});
        pre[i][j] = 1;
        while (!queue.isEmpty()) {
            int[] array = queue.poll();
            int curr_i = array[0];
            int curr_j = array[1];
            int deep = array[2];
            if (mat[curr_i][curr_j] == 0) {
                len = deep;
                return len;
            }

            for (int[] dir : dirs) {
                int next_i = curr_i + dir[0];
                int next_j = curr_j + dir[1];
                if (inArea(next_i, next_j, mat) && pre[next_i][next_j] == 0) {
                    queue.offer(new int[]{next_i, next_j, deep + 1});
                    pre[next_i][next_j] = 1; // 标记已添加队列的元素, 防止重复添加
                }
            }
        }
        return len;
    }

    private boolean inArea(int i, int j, int[][] mat) {
        if (i >= 0 && j >= 0 && i < mat.length && j < mat[0].length) {
            return true;
        }
        return false;
    }
}
