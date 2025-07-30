package com.marks.leetcode.daily_question;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/21 11:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_37 {
    
    /**
     * @Description:
     * 编写一个程序，通过填充空格来解决数独问题。
     *
     * 数独的解法需 遵循如下规则：
     *
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。（请参考示例图）
     * 数独部分空格内已填入了数字，空白格用 '.' 表示。
     * @param board
     * @return void
     * @author marks
     * @CreateDate: 2025/7/21 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void solveSudoku(char[][] board) {
        method_01(board);
        
    }

    
    /**
     * @Description:
     * 1. 刚刚查看 AI 的解答, 使用回溯法解决, 自己尝试做一遍，能否解决
     * 2. 每一行没有重复数字, 每一列没有重复数字, 3x3的实线单元格内没有重复数字
     * 3. 如果存在重复, 将这个点重置为 '.' 即空白, 回溯上一个
     *
     * AC: 216ms/40.11MB
     * @param board 
     * @return void
     * @author marks
     * @CreateDate: 2025/7/21 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void method_01(char[][] board) {
        recall(board);
    }

    private boolean recall(char[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // 处理空白
                if (board[row][col] == '.') {
                    for (int i = 1; i <= 9; i++) {
                        char num = (char) (i + '0');
                        if (isValid(board, row, col, num)) {
                            // 如果当前符合
                            board[row][col] = num;
                            if (recall(board)) {
                                return true;
                            }
                            // 重置为空白
                            board[row][col] = '.';
                        }
                    }
                    // 无解
                    return false;
                }
            }
        }
        // 完成数独填写
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char num) {
        // 检查每 行\列 是否与 num 重复
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num || board[i][col] == num ) {
                return false;
            }
        }

        // 需要对 3x3 九宫格, row / 3 = {0,1,2}
//        int baseRow = (row / 3) * 3;
//        int baseCol = (col / 3) * 3;

        int baseRow = row - row % 3;
        int baseCol = col - col % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[baseRow + i][baseCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
