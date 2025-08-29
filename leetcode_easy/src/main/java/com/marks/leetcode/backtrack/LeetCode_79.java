package com.marks.leetcode.backtrack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/29 11:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_79 {


    /**
     * @Description:
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。
     *
     * tips:
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board 和 word 仅由大小写英文字母组成
     * @param board
     * @param word
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/29 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean exist(char[][] board, String word) {
        boolean result;
        result = method_01(board, word);
        return result;
    }

    /**
     * @Description:
     * 输入：board = [['A','B','C','E'],['S','F','C','S'],['A','D','E','E']], word = "ABCCED"
     * 输出：true
     * 1. 递归 + 回溯, 递归的起始点是 word[0], 从board[i][j] == word[0] 开始递归
     * 2. 由于不能重复访问单元格, 所以在访问完单元格后, 将board[i][j] 置为 '#', char temp = board[i][j], 回溯时恢复 board[i][j] = temp
     * 3. 使用 int index 记录当前 word 的索引, 递归结束的条件是 index == word.length(), 提前剪枝的条件是 board[i][j] 的 next != word[index]
     * 4. 剪枝还有一种情况是, ans = true, 即当前已经找到了word 存在的路径, 不需要找下一条路径
     * just do it!
     * AC: 148ms/40.66MB
     * @param board
     * @param word
     * @return boolean
     * @author marks
     * @CreateDate: 2025/8/29 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    private boolean ans;
    private int n;
    private boolean method_01(char[][] board, String word) {
        ans = false;
        int index = 0;
        char[] array = word.toCharArray();
        n = array.length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == array[0]) {
                    board[i][j] = '#';
                    backtrack(board, array, index + 1, i, j);
                    board[i][j] = array[0];
                }
            }
        }
        return ans;
    }

    private void backtrack(char[][] board, char[] array, int index, int i, int j) {
        if (ans) {
            return;
        }
        if (index == n) {
            ans = true;
            return;
        }

        for (int[] dir : dirs) {
            int nextI = i + dir[0];
            int nextJ = j + dir[1];
            if (nextI >= 0 && nextI < board.length && nextJ >= 0 && nextJ < board[0].length && board[nextI][nextJ] == array[index]) {
                char temp = board[nextI][nextJ];
                board[nextI][nextJ] = '#';
                backtrack(board, array, index + 1, nextI, nextJ);
                board[nextI][nextJ] = temp;
            }
        }
    }

}
