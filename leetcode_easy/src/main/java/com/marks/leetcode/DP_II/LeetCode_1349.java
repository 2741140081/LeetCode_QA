package com.marks.leetcode.DP_II;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1349 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/17 10:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1349 {

    /**
     * @Description:
     * 给你一个 m * n 的矩阵 seats 表示教室中的座位分布。如果座位是坏的（不可用），就用 '#' 表示；否则，用 '.' 表示。
     *
     * 学生可以看到左侧、右侧、左上、右上这四个方向上紧邻他的学生的答卷，但是看不到直接坐在他前面或者后面的学生的答卷。
     * 请你计算并返回该考场可以容纳的同时参加考试且无法作弊的 最大 学生人数。
     *
     * 学生必须坐在状况良好的座位上。
     *
     * tips:
     * seats 只包含字符 '.' 和'#'
     * m == seats.length
     * n == seats[i].length
     * 1 <= m <= 8
     * 1 <= n <= 8
     * @param: seats
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxStudents(char[][] seats) {
        int result;
        result = method_01(seats);
        return result;
    }

    Map<Integer, Integer> memo = new HashMap<Integer, Integer>();

    /**
     * @Description:
     * 1. 由于数据范围限制, 可以使用状态压缩来处理每一行的情况
     * @param: seats
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int method_01(char[][] seats) {
        int m = seats.length, n = seats[0].length;
        int mx = 0;
        for (int i = 0; i < 1 << n; i++) {
            mx = Math.max(mx, dp(seats, m - 1, i));
        }
        return mx;
    }

    /**
     * @Description:
     * # 代码解释：动态规划解决教室座位安排问题
     * ## 实现原理
     * 1. **状态表示**：
     *    - 使用`row`和`status`表示当前状态
     *    - `row`表示当前处理的行号
     *    - `status`是一个二进制数，表示当前行的座位安排情况（1表示有学生，0表示空位）
     *
     * 2. **记忆化搜索**：
     *    - 使用`memo`哈希表存储已经计算过的状态，避免重复计算
     *    - 状态的键值由`key = (row << n) + status`生成，其中`n`是座位列数
     *
     * 3. **合法性检查**：
     *    - `isSingleRowCompliant()`检查当前行的座位安排是否合法（如学生之间不能相邻）
     *    - `isCrossRowsCompliant()`检查当前行与上一行的座位安排是否合法（如前后排学生不能直接相邻）
     *
     * 4. **递归计算**：
     *    - 如果当前行是第一行(row==0)，直接返回当前行可以安排的学生数
     *    - 否则，遍历所有可能的上一行状态，找出满足条件的最大学生数
     *
     * ## 用途
     *
     * 这段代码通常用于解决类似"教室座位安排"或"电影院座位安排"的问题，其中：
     * - 需要在给定的座位矩阵中安排尽可能多的学生/观众
     * - 座位安排需要满足特定的规则（如学生之间不能相邻，前后排学生不能直接相邻等）
     *
     * ## 注意事项
     *
     * 1. **状态空间**：
     *    - 状态空间大小为`m * 2^n`（m行n列），当n较大时状态空间会指数级增长
     *
     * 2. **边界条件**：
     *    - 需要正确处理第一行(row==0)的情况
     *    - 需要处理不合法状态，返回`Integer.MIN_VALUE`表示不可行
     *
     * 3. **位运算**：
     *    - 使用位运算表示状态，需要熟悉二进制操作
     *    - `Integer.bitCount(status)`用于计算二进制中1的个数，即当前行安排的学生数
     *
     * 4. **递归深度**：
     *    - 递归深度等于行数，对于大量行可能导致栈溢出
     *
     * @param: seats
     * @param: row
     * @param: status
     * @return int
     * @author marks
     * @CreateDate: 2026/07/21 15:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int dp(char[][] seats, int row, int status) {
        int n = seats[0].length;
        int key = (row << n) + status;
        if (!memo.containsKey(key)) {
            if (!isSingleRowCompliant(seats, status, n, row)) {
                memo.put(key, Integer.MIN_VALUE);
                return Integer.MIN_VALUE;
            }
            int students = Integer.bitCount(status);
            if (row == 0) {
                memo.put(key, students);
                return students;
            }
            int mx = 0;
            for (int upperRowStatus = 0; upperRowStatus < 1 << n; upperRowStatus++) {
                if (isCrossRowsCompliant(status, upperRowStatus, n)) {
                    mx = Math.max(mx, dp(seats, row - 1, upperRowStatus));
                }
            }
            memo.put(key, students + mx);
        }
        return memo.get(key);
    }

    public boolean isSingleRowCompliant(char[][] seats, int status, int n, int row) {
        for (int j = 0; j < n; j++) {
            if (((status >> j) & 1) == 1) {
                if (seats[row][j] == '#') {
                    return false;
                }
                if (j > 0 && ((status >> (j - 1)) & 1) == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isCrossRowsCompliant(int status, int upperRowStatus, int n) {
        for (int j = 0; j < n; j++) {
            if (((status >> j) & 1) == 1) {
                if (j > 0 && ((upperRowStatus >> (j - 1)) & 1) == 1) {
                    return false;
                }
                if (j < n - 1 && ((upperRowStatus >> (j + 1)) & 1) == 1) {
                    return false;
                }
            }
        }
        return true;
    }

}
