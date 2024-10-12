package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 14:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1423 {
    /**
     * @Description: [
     * 几张卡牌 排成一行，每张卡牌都有一个对应的点数。点数由整数数组 cardPoints 给出。
     * 每次行动，你可以从行的开头或者末尾拿一张卡牌，最终你必须正好拿 k 张卡牌。
     * 你的点数就是你拿到手中的所有卡牌的点数之和。
     * 给你一个整数数组 cardPoints 和整数 k，请你返回可以获得的最大点数。
     * tips:
     * 1 <= cardPoints.length <= 10^5
     * 1 <= cardPoints[i] <= 10^4
     * 1 <= k <= cardPoints.length
     * ]
     * @param cardPoints
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScore(int[] cardPoints, int k) {
        int result = 0;
        result = method_01(cardPoints, k);
        return result;
    }
    /**
     * @Description: [
     * AC:1ms/53.82MB
     * ]
     * @param cardPoints
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int ans = 0, sum = 0;
        for (int i = 0; i < k; i++) {
            sum += cardPoints[i];
        }
        ans = sum;
        for (int i = k - 1; i >= 0; i--) {
            sum = sum - cardPoints[i] + cardPoints[n - k + i];
            ans = Math.max(ans, sum);
        }
        return ans;
    }
}
