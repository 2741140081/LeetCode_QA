package com.marks.leetcode.DP_II;

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

    /**
     * @Description:
     * 1. 假设位于 (i, j) 位置处的座位是好的, 如果要在当前座位安排一个学生, 那么只能从 上方 或者左侧间隔1个 dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 2]) + 1
     * 2. 当前座位是好的, 但是不安排学生, dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j], dp[i - 1][j - 1]) 三者取最大值
     * 3. 是不是需要一个额外数组记录坐标点是否安排学生, 将dp 进行修改 int[][][] dp = new int[m][n][2]; 0 表示不安排学生, 1 表示安排学生, 并且当座位是坏的, 那么无法安排学生
     * 4. todo
     * @param: seats
     * @return int
     * @author marks
     * @CreateDate: 2026/04/17 10:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(char[][] seats) {

        return 0;
    }

}
