package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/20 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1626 {
    /**
     * @Description: [
     * 假设你是球队的经理。对于即将到来的锦标赛，你想组合一支总体得分最高的球队。球队的得分是球队中所有球员的分数 总和 。
     *
     * 然而，球队中的矛盾会限制球员的发挥，所以必须选出一支 没有矛盾 的球队。
     * 如果一名年龄较小球员的分数 严格大于 一名年龄较大的球员，则存在矛盾。同龄球员之间不会发生矛盾。
     *
     * 给你两个列表 scores 和 ages，其中每组 scores[i] 和 ages[i] 表示第 i 名球员的分数和年龄。请你返回 所有可能的无矛盾球队中得分最高那支的分数 。
     * ]
     * @param scores
     * @param ages
     * @return int
     * @author marks
     * @CreateDate: 2024/8/20 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int bestTeamScore(int[] scores, int[] ages) {
        int result = 0;
//        result = method_01(scores, ages);
        result = method_02(scores, ages);
        return result;
    }

    /**
     * @Description: [官方题解
     * 动态规划:
     * ]
     * @param scores
     * @param ages
     * @return int
     * @author marks
     * @CreateDate: 2024/8/20 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] scores, int[] ages) {
        int n = scores.length;
        int[][] people = new int[n][2];
        for (int i = 0; i < n; ++i) {
            people[i] = new int[]{scores[i], ages[i]};
        }
        Arrays.sort(people, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
        int[] dp = new int[n];
        int res = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i - 1; j >= 0; --j) {
                if (people[j][1] <= people[i][1]) {
                    dp[i] = Math.max(dp[i], dp[j]);
                }
            }
            dp[i] += people[i][0];
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    /**
     * @Description: [
     * 思路：肯定是使用dp来求解
     * 案例E1:
     * 输入：scores = [1,2,3,5], ages = [8,9,10,1]
     * 输出：6
     * 解释：最佳的选择是前 3 名球员。
     * 结论：错误，需要先对s[] 和 a[] 进行排序
     * ]
     * @param scores
     * @param ages
     * @return int
     * @author marks
     * @CreateDate: 2024/8/20 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] scores, int[] ages) {
        int n = scores.length;
        int[] dp = new int[n];

        for (int i = 0; i < n; i++) {
            // 初始化
            dp[i] = scores[i];
            for (int j = 0; j < i; j++) {
                if ((scores[i] > scores[j] && ages[i] > ages[j]) || (scores[i] < scores[j] && ages[i] < ages[j])) {
                    dp[i] = Math.max(dp[i], dp[j] + scores[i]);
                } else if (ages[i] == ages[j]) {
                    // 年龄相同时
                    dp[i] = Math.max(dp[i], dp[j] + scores[i]);
                } else if (scores[i] == scores[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + scores[i]);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
