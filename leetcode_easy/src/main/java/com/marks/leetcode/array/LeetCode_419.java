package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_419 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/6 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_419 {

    /**
     * @Description:
     * 给你一个大小为 m x n 的矩阵 board 表示棋盘，其中，每个单元格可以是一艘战舰 'X' 或者是一个空位 '.' ，返回在棋盘 board 上放置的 舰队 的数量。
     *
     * 舰队 只能水平或者垂直放置在 board 上。换句话说，舰队只能按 1 x k（1 行，k 列）或 k x 1（k 行，1 列）的形状放置，其中 k 可以是任意大小。
     * 两个舰队之间至少有一个水平或垂直的空格分隔 （即没有相邻的舰队）。
     * @param: board
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countBattleships(char[][] board) {
        int result;
        result = method_01(board);
        return result;
    }

    /**
     * @Description:
     * 1. 假设当前位于格子 (i, j)，如果 board[i][j] == 'X'，并且左侧和上方的格子都是 '.'，则当前格子为战舰的起始格子。 ans++
     * AC: 1ms/44.68MB
     * @param: board
     * @return int
     * @author marks
     * @CreateDate: 2026/05/06 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[][] board) {
        int ans = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'X' && (i == 0 || board[i - 1][j] == '.') && (j == 0 || board[i][j - 1] == '.')) {
                    ans++;
                }
            }
        }

        return ans;
    }

}
