package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_36 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/27 15:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_36 {

    /**
     * @Description:
     * 请你判断一个 9 x 9 的数独是否有效。只需要 根据以下规则 ，验证已经填入的数字是否有效即可。
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     *
     * 注意：
     *
     * 一个有效的数独（部分已被填充）不一定是可解的。
     * 只需要根据以上规则，验证已经填入的数字是否有效即可。
     * 空白格用 '.' 表示。
     * @param: board
     * @return boolean
     * @author marks
     * @CreateDate: 2026/04/27 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isValidSudoku(char[][] board) {
        boolean result;
        result = method_01(board);
        return result;
    }

    /**
     * @Description:
     * 1. 遍历board，判断 board[i][j] 是否满足要求
     * 2. board[i][j] 是数字时, 验证数字是否满足要求
     * 3. board[i][j] 是 . 时, 跳过
     * AC: 1ms/45.87MB
     * @param: board
     * @return boolean
     * @author marks
     * @CreateDate: 2026/04/27 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(char[][] board) {
        int n = board.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != '.') {
                    if (isValid(board, i, j)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean isValid(char[][] board, int i, int j) {
        for (int k = 0; k < 9; k++) {
            if (k != j && board[i][k] == board[i][j]) {
                return true;
            }
            if (k != i && board[k][j] == board[i][j]) {
                return true;
            }
        }
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                int x = 3 * (i / 3) + m;
                int y = 3 * (j / 3) + n;
                if (x != i && y != j && board[x][y] == board[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

}
