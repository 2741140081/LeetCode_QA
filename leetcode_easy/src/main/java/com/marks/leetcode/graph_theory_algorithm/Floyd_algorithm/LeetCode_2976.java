package com.marks.leetcode.graph_theory_algorithm.Floyd_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/6 17:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2976 {
    /**
     * @Description: [
     * 给你两个下标从 0 开始的字符串 source 和 target ，它们的长度均为 n 并且由 小写 英文字母组成。
     *
     * 另给你两个下标从 0 开始的字符数组 original 和 changed ，以及一个整数数组 cost ，其中 cost[i] 代表将字符 original[i] 更改为字符 changed[i] 的成本。
     *
     * 你从字符串 source 开始。在一次操作中，如果 存在 任意 下标 j 满足 cost[j] == z  、original[j] == x 以及 changed[j] == y 。你就可以选择字符串中的一个字符 x 并以 z 的成本将其更改为字符 y 。
     *
     * 返回将字符串 source 转换为字符串 target 所需的 最小 成本。如果不可能完成转换，则返回 -1 。
     *
     * 注意，可能存在下标 i 、j 使得 original[j] == original[i] 且 changed[j] == changed[i] 。
     * 1 <= source.length == target.length <= 10^5
     * source、target 均由小写英文字母组成
     * 1 <= cost.length== original.length == changed.length <= 2000
     * original[i]、changed[i] 是小写英文字母
     * 1 <= cost[i] <= 10^6
     * original[i] != changed[i]
     * ]
     * @param source 
     * @param target 
     * @param original 
     * @param changed 
     * @param cost 
     * @return long
     * @author marks
     * @CreateDate: 2025/1/6 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        long result;
        result = method_01(source, target, original, changed, cost);
        return result;
    }

    /**
     * @Description: [
     * E1:source = "abcd", target = "acbe", original = ["a","b","c","c","e","d"], changed = ["b","c","b","e","b","e"], cost = [2,5,5,1,2,20]
     * Result:28
     *
     * AC:23ms/44.83MB
     * ]
     * @param source
     * @param target
     * @param original
     * @param changed
     * @param cost
     * @return long
     * @author marks
     * @CreateDate: 2025/1/6 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(String source, String target, char[] original, char[] changed, int[] cost) {
        final int INF = Integer.MAX_VALUE / 2;
        int[][] dp = new int[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dp[i], INF);
        }
        int n = original.length;
        for (int i = 0; i < n; i++) {
            int x = original[i] - 'a';
            int y = changed[i] - 'a';
            int value = cost[i];
            dp[x][y] = Math.min(dp[x][y], value);
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }
        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            int x = source.charAt(i) - 'a';
            int y = target.charAt(i) - 'a';
            if (x != y) {
                if (dp[x][y] == INF) {
                    return -1;
                } else {
                    ans += dp[x][y];
                }
            }
        }
        return ans;
    }
}
