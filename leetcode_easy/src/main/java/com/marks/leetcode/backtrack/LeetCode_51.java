package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/25 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_51 {

    
    /**
     * @Description:
     * N 皇后问题
     * 按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     *
     * 给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。
     * 每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     *
     * tips:
     * 1 <= n <= 9
     * @param n
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/8/25 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result;
        result = method_01(n); // 使用优化的位运算解法
        result = method_02(n); // 使用优化的位运算解法
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 4
     * 输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
     * 1. 创建一个二维数组，用于存储结果, 数组的行数和列数都为 n, char[][] array = new char[n][n];
     * 2. 初始化 array 数组, 填充 '.', 表示没有皇后,
     * 3. 通过回溯递归的方式, 尝试将皇后放置在每一行每一列的每一个位置, 尝试的逻辑为:
     * 4. 假设当前行为 index, 那么可以将皇后放置在当前行的[0 ~ n - 1] 个位置上, 然后写一个valid() 方法, 判断当前位置是否合法,
     * 5. just do it!
     *
     * AC: 2ms/44.28MB
     * @param n 
     * @return java.util.List<java.util.List<java.lang.String>>
     * @author marks
     * @CreateDate: 2025/8/25 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<String>> ans;
    private List<List<String>> method_01(int n) {
        char[][] array = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(array[i], '.');
        }
        ans = new ArrayList<>();
        backtrack(array, 0, n);
        return ans;
    }

    private void backtrack(char[][] array, int index, int n) {
        if (index == n) {
            List<String> list = new ArrayList<>();
            for (char[] chars : array) {
                list.add(new String(chars));
            }
            ans.add(list);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (valid(array, index, i)) {
                array[index][i] = 'Q';
                backtrack(array, index + 1, n);
                array[index][i] = '.';
            }
        }
    }

    private boolean valid(char[][] array, int i, int j) {

        // 判断当前行是否存在皇后
        for (int k = 0; k < array.length; k++) {
            if (array[i][k] == 'Q') {
                return false;
            }
        }
        // 判断当前列是否存在皇后
        for (int k = 0; k < array.length; k++) {
            if (array[k][j] == 'Q') {
                return false;
            }
        }
        // 左上对角线
        for (int k = i, l = j; k >= 0 && l >= 0; k--, l--) {
            if (array[k][l] == 'Q') {
                return false;
            }
        }
        // 右上对角线
        for (int k = i, l = j; k >= 0 && l < array.length; k--, l++) {
            if (array[k][l] == 'Q') {
                return false;
            }
        }

        return true;
    }

    /**
     * 使用位运算优化的N皇后解法
     * @param n 皇后数量
     * @return 所有解决方案
     */
    private List<List<String>> method_02(int n) {
        ans = new ArrayList<>();
        // 用位运算表示列、主对角线、副对角线的占用情况
        backtrackOptimized(n, 0, 0, 0, 0, new int[n]);
        return ans;
    }

    /**
     * 位运算优化的回溯方法
     * @param n N皇后
     * @param row 当前行
     * @param cols 列占用情况（二进制位表示）
     * @param diag1 主对角线占用情况（左上到右下）
     * @param diag2 副对角线占用情况（右上到左下）
     * @param queens 记录每行皇后所在的列索引
     */
    private void backtrackOptimized(int n, int row, int cols, int diag1, int diag2, int[] queens) {
        if (row == n) {
            // 构造结果
            List<String> solution = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                char[] chars = new char[n];
                Arrays.fill(chars, '.');
                chars[queens[i]] = 'Q';
                solution.add(new String(chars));
            }
            ans.add(solution);
            return;
        }

        // 计算当前行可以放置皇后的位置
        // (cols | diag1 | diag2) - 已被占用的位置
        // (~(cols | diag1 | diag2)) - 未被占用的位置
        // ((1 << n) - 1) & (~(cols | diag1 | diag2)) - 考虑n位的限制
        int availablePositions = ((1 << n) - 1) & (~(cols | diag1 | diag2));
        
        while (availablePositions != 0) {
            // 取出最右边的1作为当前位置
            int position = availablePositions & (-availablePositions);
            // 从availablePositions中移除当前位置
            availablePositions = availablePositions & (availablePositions - 1);
            
            // 计算position在第几列
            int col = Integer.bitCount(position - 1);
            
            // 记录当前行皇后位置
            queens[row] = col;
            
            // 递归处理下一行
            backtrackOptimized(
                n, 
                row + 1, 
                cols | position,                           // 更新列占用
                (diag1 | position) << 1,                   // 更新主对角线占用
                (diag2 | position) >> 1,                   // 更新副对角线占用
                queens
            );
        }
    }
}