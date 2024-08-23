package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称: 俄罗斯套娃信封问题, 二维数组LIS </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_354 {
    /**
     * @Description: [
     * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
     *
     * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
     *
     * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
     *
     * 注意：不允许旋转信封。
     *
     * tips:
     * 1 <= envelopes.length <= 10^5
     * envelopes[i].length == 2
     * 1 <= wi, hi <= 10^5
     * ]
     * @param envelopes
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 9:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxEnvelopes(int[][] envelopes) {
        int result = 0;
        result = method_01(envelopes);
        return result;
    }

    /**
     * @Description: [
     * envelopes = [[5,4],[6,4],[6,7],[2,3]]
     *
     * ]
     * @param envelopes
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 9:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] envelopes) {
        int n = envelopes.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (envelopes[i][0] > envelopes[j][0] && envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }
}
