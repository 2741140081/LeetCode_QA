package com.marks.leetcode.partition_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/25 17:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2430 {
    /**
     * @Description: [
     * 给你一个仅由小写英文字母组成的字符串 s 。在一步操作中，你可以：
     *
     * 删除 整个字符串 s ，或者
     * 对于满足 1 <= i <= s.length / 2 的任意 i ，如果 s 中的 前 i 个字母和接下来的 i 个字母 相等 ，删除 前 i 个字母。
     * 例如，如果 s = "ababc" ，那么在一步操作中，你可以删除 s 的前两个字母得到 "abc" ，因为 s 的前两个字母和接下来的两个字母都等于 "ab" 。
     *
     * 返回删除 s 所需的最大操作数。
     * tips；
     * 1 <= s.length <= 4000
     * s 仅由小写英文字母组成
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/25 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int deleteString(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：s = "aaabaab"
     * 输出：4
     * 解释：
     * - 删除第一个字母（"a"），因为它和接下来的字母相等。现在，s = "aabaab"。
     * - 删除前 3 个字母（"aab"），因为它们和接下来 3 个字母相等。现在，s = "aab"。
     * - 删除第一个字母（"a"），因为它和接下来的字母相等。现在，s = "ab"。
     * - 删除全部字母。
     * 一共用了 4 步操作，所以返回 4 。可以证明 4 是所需的最大操作数。
     * dp[0] = 1
     * 前面已经取得最大的操作数dp[i]
     * s[i + 1] == s[i + 2] dp[i + 1] = dp[i] + 1
     * else dp[i + 1] = dp[i]
     * s.sub(i+1, i+3) equals s.sub(i+3, i+5) dp[i + len] = dp[i + 2] = dp[i] + 1
     * Math.max
     * AC:303ms/193.96MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/25 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int[][] pre = new int[n + 1][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (s.charAt(j) == s.charAt(i)) {
                    pre[i][j] = pre[i + 1][j + 1] + 1;
                }
            }
        }
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; i + 2 * j <= n; j++) {
                if (pre[i][i + j] >= j) {
                    dp[i] = Math.max(dp[i], dp[i + j]);
                }
            }
            dp[i]++;
        }
        return dp[0];
    }
}
