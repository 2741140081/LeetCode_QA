package com.marks.leetcode.DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/13 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2400 {

    /**
     * @Description:
     * 给你两个 正 整数 startPos 和 endPos 。最初，你站在 无限 数轴上位置 startPos 处。在一步移动中，你可以向左或者向右移动一个位置。
     *
     * 给你一个正整数 k ，返回从 startPos 出发、恰好 移动 k 步并到达 endPos 的 不同 方法数目。由于答案可能会很大，返回对 10^9 + 7 取余 的结果。
     *
     * 如果所执行移动的顺序不完全相同，则认为两种方法不同。
     *
     * 注意：数轴包含负整数。
     * tips:
     * 1 <= startPos, endPos, k <= 1000
     * @param startPos 
     * @param endPos 
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfWays(int startPos, int endPos, int k) {
        int result;
        result = method_01(startPos, endPos, k);
        return result;
    }

    private final int MOD = 1000000007;

    
    /**
     * @Description:
     * 状态转移方程 dp[k][i] = dp[k - 1][i + 1] + dp[k - 1][i - 1]; (向左移动 i + 1, 向右移动 i - 1)
     * AC: 84ms/89.39MB
     * @param startPos 
     * @param endPos 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/13 16:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int startPos, int endPos, int k) {
        long[][] dp = new long[k + 1][3005]; // 将原点设置为(1000) 为 0 点 999 相当于 -1, 1001 相当于 1
        startPos += 1000;
        endPos += 1000;
        dp[1][startPos - 1] = 1;
        dp[1][startPos + 1] = 1;
        for (int i = 2; i <= k; i++) {
            for (int j = startPos - k; j <= startPos + k; j++) {
                dp[i][j] = (dp[i - 1][j + 1] + dp[i - 1][j - 1]) % MOD;
            }
        }
        return (int) dp[k][endPos];
    }

}
