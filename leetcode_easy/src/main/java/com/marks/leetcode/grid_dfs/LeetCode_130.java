package com.marks.leetcode.grid_dfs;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/10 9:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_130 {
    private int m;
    private int n;

    private boolean flag = true; // 标记位, 是否可以到达边界, true: 无法到达, false: 可以到达
    private int[][] pre; // 额外标记数组, 标记board元素'O'是否已经访问过了, 0表示未访问, 1表示已访问
    /**
     * @Description: [
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' 组成，捕获 所有 被围绕的区域：
     *
     * 连接：一个单元格与水平或垂直方向上相邻的单元格连接。
     * 区域：连接所有 'O' 的单元格来形成一个区域。
     * 围绕：如果您可以用 'X' 单元格 连接这个区域，并且区域中没有任何单元格位于 board 边缘，则该区域被 'X' 单元格围绕。
     * 通过将输入矩阵 board 中的所有 'O' 替换为 'X' 来 捕获被围绕的区域。
     * ]
     * @param board
     * @return void
     * @author marks
     * @CreateDate: 2024/12/10 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void solve(char[][] board) {
        method_01(board);
        method_02(board);
    }

    /**
     * @Description: [
     * 查看官方题解后解答
     * AC:1ms/44.67MB
     * ]
     * @param board
     * @return void
     * @author marks
     * @CreateDate: 2024/12/10 10:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_02(char[][] board) {
        m = board.length;
        n = board[0].length;
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') {
                dfs(board, i, 0);
            }
            if (board[i][n - 1] == 'O') {
                dfs(board, i, n - 1);
            }
        }

        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') {
                dfs(board, 0, j);
            }
            if (board[m - 1][j] == 'O') {
                dfs(board, m - 1, j);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void dfs(char[][] board, int i, int j) {
        if (!inArea(board, i, j) || board[i][j] != 'O') {
            return;
        }
        board[i][j] = 'A'; // 标记
        dfs(board, i + 1, j);
        dfs(board, i - 1, j);
        dfs(board, i, j + 1);
        dfs(board, i, j - 1);
    }

    /**
     * @Description: [
     * DFS标准模板题
     * AC:6ms/49.06MB
     *
     * 为什么其他人提交是 1ms, 看看官方题解
     * 看懂了, 官方题解是先从边界开始遍历最大面积
     * 即(i,0)首列, (i, n - 1)尾列, i++, (0, j)首行, (n - 1, j)尾行, j++;
     * 通过这些边界的节点遍历最大面积, 并且将这些'O' 修改为 'A', 方便后续替换和标记是否已经访问过了
     * 然后遍历整个board, 将'A' 修改为'O', 将‘O’ 修改为'X'
     * 所以这个方法不需要额外list存储坐标值, 查看method_02
     * ]
     * @param board
     * @return void
     * @author marks
     * @CreateDate: 2024/12/10 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(char[][] board) {
        m = board.length;
        n = board[0].length;
        pre = new int[m][n];
        List<List<int[]>> ans = new ArrayList<>();
        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (board[i][j] == 'O' && pre[i][j] == 0) {
                    List<int[]> list = new ArrayList<>();
                    flag = true;
                    dfsMaxArea(board, i, j, list);
                    if (flag) {
                        ans.add(list);
                    }
                }
            }
        }
        
        // 修改board元素
        for (List<int[]> item : ans) {
            for (int i = 0; i < item.size(); i++) {
                int[] coordinate = item.get(i);
                board[coordinate[0]][coordinate[1]] = 'X';
            }
        }
    }

    private void dfsMaxArea(char[][] board, int i, int j, List<int[]> list) {
        if (!inArea(board, i, j)) {
            flag = false;
            return;
        }

        if (board[i][j] == 'X' || pre[i][j] != 0) {
            return;
        }

        if (board[i][j] == 'O' && pre[i][j] == 0) {
            list.add(new int[]{i, j});
            pre[i][j] = 1; //标记已经访问
            dfsMaxArea(board, i + 1, j, list);
            dfsMaxArea(board, i - 1, j, list);
            dfsMaxArea(board, i, j + 1, list);
            dfsMaxArea(board, i, j - 1, list);
        }
    }

    private boolean inArea(char[][] board, int i, int j) {
        if (i >= 0 && j >= 0 && i < m && j < n) {
            return true;
        }
        return false;
    }
}
