package com.marks.leetcode.daily_question;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/11 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_59 {
    private int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private int n;

    /**
     * @Description:
     * 给你一个正整数 n ，生成一个包含 1 到 n^2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     * @param n
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/2/11 11:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] generateMatrix(int n) {
        int[][] result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 简单模拟
     * AC: 0ms/40.59MB
     * @param n
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/2/11 11:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int n) {
        this.n = n;
        int[][] ans = new int[n][n];
        int x = 0;
        int y = 0;
        int flag = 0;
        int index = 1;
        dfs(ans, x, y, flag, index);
        return ans;
    }

    private void dfs(int[][] ans, int x, int y, int flag, int index) {
        if (index > n * n) {
            return;
        }
        ans[x][y] = index;
        int next_x = x + dirs[flag][0];
        int next_y = y + dirs[flag][1];
        if (inArea(next_x, next_y) && ans[next_x][next_y] == 0) {
            dfs(ans, next_x, next_y, flag, index + 1);
        } else {
            flag = (flag + 1) % 4;
            next_x = x + dirs[flag][0];
            next_y = y + dirs[flag][1];
            dfs(ans, next_x, next_y, flag, index + 1);
        }
    }

    private boolean inArea(int i, int j) {
        if (i >= 0 && j >= 0 && i < n && j < n) {
            return true;
        }
        return false;
    }
}
