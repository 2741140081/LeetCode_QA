package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1641 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/19 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1641 {

    /**
     * @Description:
     * 给你一个整数 n，请返回长度为 n 、仅由元音 (a, e, i, o, u) 组成且按 字典序排列 的字符串数量。
     *
     * 字符串 s 按 字典序排列 需要满足：对于所有有效的 i，s[i] 在字母表中的位置总是与 s[i+1] 相同或在 s[i+1] 之前。
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countVowelStrings(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * int[][] dp = new int[n][5]
     * dp[i][k] = Math.sum(dp[i - 1][k ~ 0])
     * AC: 0ms/41.29MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2025/11/19 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[][] dp = new int[n][5];
        // 初始化 dp[0]
        for (int i = 0; i < 5; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = j; k >= 0; k--) {
                    dp[i][j] += dp[i - 1][k];
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < 5; i++) {
            ans += dp[n - 1][i];
        }
        return ans;
    }

}
