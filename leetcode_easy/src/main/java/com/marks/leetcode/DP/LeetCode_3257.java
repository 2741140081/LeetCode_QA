package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3357 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/30 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3257 {

    /***
     * @Description:
     * 给你一个 m x n 的二维整数数组 board ，它表示一个国际象棋棋盘，其中 board[i][j] 表示格子 (i, j) 的 价值 。
     * 处于 同一行 或者 同一列 车会互相 攻击 。你需要在棋盘上放三个车，确保它们两两之间都 无法互相攻击 。
     * 请你返回满足上述条件下，三个车所在格子 值 之和 最大 为多少。
     * tips:
     * 3 <= m == board.length <= 500
     * 3 <= n == board[i].length <= 500
     * -10^9 <= board[i][j] <= 10^9
     * @param: board
     * @return long
     * @author marks
     * @CreateDate: 2025/10/30 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumValueSum(int[][] board) {
        long result;
        result = method_01(board);
        return result;
    }

    private long ans = Long.MIN_VALUE;
    private long maxValue = Integer.MIN_VALUE;
    /***
     * @Description:
     * 1. 3个车分别用c1, c2, c3表示, 直接使用回溯法枚举所有结果
     * 2. 推算时间复杂度, 500^6 = 10^12 时间复杂度非常高, 需要优化
     * 可行, 但是超时!!! 815 / 857
     * 3. 改进, 将int[][] board 中每行的最大的3个值保存到List<int[]> 中
     * 可行, 但是仍然超时!!! 837 / 857
     * need todo
     * @param: board
     * @return long
     * @author marks
     * @CreateDate: 2025/10/30 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] board) {
        Set<Integer> columns = new HashSet<>();
        Set<Integer> rows = new HashSet<>();
        int m = board.length;
        List<int[]>[] lists = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            lists[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            List<int[]> list = new ArrayList<>();
            for (int j = 0; j < board[0].length; j++) {
                maxValue = Math.max(maxValue, board[i][j]);
                list.add(new int[]{board[i][j], i, j});
            }
            list.sort((a, b) -> b[0] - a[0]);
            while (list.size() > 3) {
                list.remove(list.size() - 1);
            }
            lists[i] = list;
        }
        backTrack(lists, 0, 0, columns, rows);
        return ans;
    }

    private void backTrack(List<int[]>[] lists, int index, long sum, Set<Integer> columns, Set<Integer> rows) {
        if (index == 3) {
            ans = Math.max(ans, sum);
            return;
        }
        for (List<int[]> list : lists) {
            for (int j = 0; j < 3; j++) {
                int[] currNode = list.get(j);
                int col = currNode[1];
                int row = currNode[2];
                if (!columns.contains(col) && !rows.contains(row) && (sum + (3 - index) * maxValue > ans)) {
                    columns.add(col);
                    rows.add(row);
                    backTrack(lists, index + 1, sum + currNode[0], columns, rows);
                    columns.remove(col);
                    rows.remove(row);
                }
            }
        }
    }

}
