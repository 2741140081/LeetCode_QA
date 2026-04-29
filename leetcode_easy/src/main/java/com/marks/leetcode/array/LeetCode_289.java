package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_289 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/28 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_289 {

    /**
     * @Description:
     * 根据 百度百科 ， 生命游戏 ，简称为 生命 ，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
     *
     * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。
     * 每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
     *
     * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
     * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
     * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
     * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
     * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是 同时 发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。
     *
     * 给定当前 board 的状态，更新 board 到下一个状态。
     *
     * 注意 你不需要返回任何东西。
     * @param: board
     * @return void
     * @author marks
     * @CreateDate: 2026/04/28 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void gameOfLife(int[][] board) {
        method_01(board);
        method_02(board);
    }

    /**
     * @Description:
     * 1. 活细胞死亡, 将值修改为 -1, 死亡的细胞复活, 将值改为 2
     * 2. 最后统一更新 -1 和 2
     * @param: board
     * @return void
     * @author marks
     * @CreateDate: 2026/04/28 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_02(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                for (int[] dir : dirs) {
                    int newI = i + dir[0];
                    int newJ = j + dir[1];
                    if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
                        continue;
                    }
                    if (board[newI][newJ] == 1 || board[newI][newJ] == -1) {
                        count++;
                    }
                }
                if (board[i][j] == 1) {
                    if (count < 2 || count > 3) {
                        board[i][j] = -1;
                    }
                } else {
                    if (count == 3) {
                        board[i][j] = 2;
                    }
                }
            }
        }
        // 更新
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == -1) {
                    board[i][j] = 0;
                } else if (board[i][j] == 2) {
                    board[i][j] = 1;
                }
            }
        }
    }


    /**
     * @Description:
     * 1. 我需要在原数组上进行修改
     * 2. 假设我使用贡献法, 遍历整个数组, 对于 (i, j), 他会影响自身周围的8个细胞, 给周围细胞添加上 board[i][j] 值
     * 3. 我需要一个额外的数组来保存贡献值, 并且只有活细胞才有贡献值
     * AC: 0ms/42.47MB
     * @param: board
     * @return void
     * @author marks
     * @CreateDate: 2026/04/28 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(int[][] board) {
        int m = board.length;
        int n = board[0].length;
        int[][] contribution = new int[m][n];
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] dir : dirs) {
                    int newI = i + dir[0];
                    int newJ = j + dir[1];
                    if (newI < 0 || newI >= m || newJ < 0 || newJ >= n) {
                        continue;
                    }
                    if (board[newI][newJ] == 1) {
                        contribution[i][j]++;
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 1) {
                    if (contribution[i][j] < 2 || contribution[i][j] > 3) {
                        board[i][j] = 0;
                    }
                } else {
                    if (contribution[i][j] == 3) {
                        board[i][j] = 1;
                    }
                }
            }
        }
    }

}
