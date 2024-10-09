package com.marks.leetcode.other_linear_dp;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2140 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的二维整数数组 questions ，其中 questions[i] = [pointsi, brainpoweri] 。
     *
     * 这个数组表示一场考试里的一系列题目，你需要 按顺序 （也就是从问题 0 开始依次解决），针对每个问题选择 解决 或者 跳过 操作。
     * 解决问题 i 将让你 获得  pointsi 的分数，但是你将 无法 解决接下来的 brainpoweri 个问题（即只能跳过接下来的 brainpoweri 个问题）。
     * 如果你跳过问题 i ，你可以对下一个问题决定使用哪种操作。
     *
     * 比方说，给你 questions = [[3, 2], [4, 3], [4, 4], [2, 5]] ：
     * 如果问题 0 被解决了， 那么你可以获得 3 分，但你不能解决问题 1 和 2 。
     * 如果你跳过问题 0 ，且解决问题 1 ，你将获得 4 分但是不能解决问题 2 和 3 。
     * 请你返回这场考试里你能获得的 最高 分数。
     *
     * tips:
     * 1 <= questions.length <= 10^5
     * questions[i].length == 2
     * 1 <= pointsi, brainpoweri <= 10^5
     * ]
     * @param questions
     * @return long
     * @author marks
     * @CreateDate: 2024/10/9 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long mostPoints(int[][] questions) {
        long result = 0;
        result = method_01(questions);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：questions = [[3,2],[4,3],[4,4],[2,5]]
     * 输出：5
     * 解释：解决问题 0 和 3 得到最高分。
     * - 解决问题 0 ：获得 3 分，但接下来 2 个问题都不能解决。
     * - 不能解决问题 1 和 2
     * - 解决问题 3 ：获得 2 分
     * 总得分为：3 + 2 = 5 。没有别的办法获得 5 分或者多于 5 分。
     *
     * AC:6ms/105.67MB
     * ]
     * @param questions 
     * @return long
     * @author marks
     * @CreateDate: 2024/10/9 11:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n + 1];

        for (int i = 0; i < n; i++) {
            int point = questions[i][0];
            int skip = questions[i][1];
            if (i + skip + 1 < n) {
                dp[i + skip + 1] = Math.max(dp[i + skip + 1], dp[i] + point);
            }else {
                dp[n] = Math.max(dp[n], dp[i] + point);
            }
            dp[i + 1] = Math.max(dp[i + 1], dp[i]);
        }
        return dp[n];
    }
}
