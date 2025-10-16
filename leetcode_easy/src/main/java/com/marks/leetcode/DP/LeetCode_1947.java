package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/15 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1947 {

    /**
     * @Description:
     * 有一份由 n 个问题组成的调查问卷，每个问题的答案要么是 0（no，否），要么是 1（yes，是）。
     *
     * 这份调查问卷被分发给 m 名学生和 m 名导师，学生和导师的编号都是从 0 到 m - 1 。
     * 学生的答案用一个二维整数数组 students 表示，其中 students[i] 是一个整数数组，包含第 i 名学生对调查问卷给出的答案（下标从 0 开始）。
     * 导师的答案用一个二维整数数组 mentors 表示，其中 mentors[j] 是一个整数数组，包含第 j 名导师对调查问卷给出的答案（下标从 0 开始）。
     *
     * 每个学生都会被分配给 一名 导师，而每位导师也会分配到 一名 学生。配对的学生与导师之间的兼容性评分等于学生和导师答案相同的次数。
     *
     * 例如，学生答案为[1, 0, 1] 而导师答案为 [0, 0, 1] ，那么他们的兼容性评分为 2 ，因为只有第二个和第三个答案相同。
     * 请你找出最优的学生与导师的配对方案，以 最大程度上 提高 兼容性评分和 。
     *
     * 给你 students 和 mentors ，返回可以得到的 最大兼容性评分和 。
     *
     * tips:
     * m == students.length == mentors.length
     * n == students[i].length == mentors[j].length
     * 1 <= m, n <= 8
     * students[i][k] 为 0 或 1
     * mentors[j][k] 为 0 或 1
     * @param students 
     * @param mentors
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        int result;
        result = method_01(students, mentors);
        return result;
    }

    /**
     * @Description:
     * 输入：students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
     * 输出：8
     * 101, 001 => x = Integer.bitCount(s[i] ^ m[i]); score = n - x;
     * 1. 这个真的有状态转移方程吗? 完全想不到,
     * 2. 110, 101, 001; 100, 001, 110;
     * 3. 数据量非常小, 可以直接枚举
     * AC: 39ms/40.72MB
     * @param students 
     * @param mentors 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/15 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] students, int[][] mentors) {
        int n = students[0].length;
        int m = students.length;
        int[] s = new int[m];
        int[] t = new int[m];
        for (int i = 0; i < m; i++) {
            int result = 0;
            for (int j = 0; j < n; j++) {
                result = (result << 1) | students[i][j];
            }
            s[i] = result;
            result = 0;
            for (int j = 0; j < n; j++) {
                result = (result << 1) | mentors[i][j];
            }
            t[i] = result;
        }
        boolean[] visited = new boolean[m]; // 记录访问过的导师
        backTracking(s, t, visited, 0, 0);

        return m * n - score;
    }

    private int score = Integer.MAX_VALUE;

    private void backTracking(int[] s, int[] t, boolean[] visited, int index, int sum) {
        if (index == s.length) {
            score = Math.min(score, sum);
            return;
        }
        int curr = s[index];
        for (int i = 0; i < t.length; i++) {
            int temp = Integer.bitCount(curr ^ t[i]);
            if (!visited[i]) {
                visited[i] = true;
                backTracking(s, t, visited, index + 1, sum + temp);
                visited[i] = false;
            }
        }
    }

}
