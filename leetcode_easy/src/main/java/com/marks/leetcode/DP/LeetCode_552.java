package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_552 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/9 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_552 {

    /**
     * @Description:
     * 可以用字符串表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
     * 'A'：Absent，缺勤
     * 'L'：Late，迟到
     * 'P'：Present，到场
     * 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
     *
     * 按 总出勤 计，学生缺勤（'A'）严格 少于两天。
     * 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
     * 给你一个整数 n ，表示出勤记录的长度（次数）。请你返回记录长度为 n 时，可能获得出勤奖励的记录情况 数量 。
     * 答案可能很大，所以返回对 10^9 + 7 取余 的结果。
     *
     * tips:
     * 1 <= n <= 10^5
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/09 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int checkRecord(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 1. 字符串中不能有两个'A', 并且连续的 L 次数小于 3
     * 2. 状态转移方程: 先定义参数 int[][][] dp = new int[n][2][3]; i 表示当前字符的位置, j 表示前 i 个字符中 'A' 的个数, k 表示结尾连续 L 的个数
     * 3. 假设当前 位于 i 处, 如果 i = 'A', 则转移 dp[i][1][0] += dp[i - 1][0][0~2]; 只能从 A = 0 转移过来, 并且转移后结尾L个数变成0
     * 4. 假设当前 i = 'L', 则转移 dp[i][0][k + 1] += dp[i - 1][0~1][0~1]; 允许从 A = 0/1, L = 0~1 转移过来, 允许结尾L个数变成 k + 1, k 的取值为 0 ~ 1
     * 5. i 只与 i - 1存在关联, 滚动数组优化空间复杂度
     * 6. 初始化dp[0][0][0] = 1;
     * 7. 注意清空当前层数据, 防止受到历史数据的污染
     * AC: 76ms/41.32MB
     * @param: n
     * @return int
     * @author marks
     * @CreateDate: 2026/04/09 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        int[][][] dp = new int[2][2][3];
        int MOD = 1000000007;
        dp[0][0][0] = 1;
        int curr = 0;
        int prev;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            prev = 1 - curr;

            // 清空当前层的状态
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[curr][j][k] = 0;
                }
            }

            // 当前 i = 'A'
            for (int k = 0; k < 3; k++) {
                dp[curr][1][0] = (dp[curr][1][0] + dp[prev][0][k]) % MOD;
            }
            // 当前 i = 'L'
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    dp[curr][j][k + 1] = (dp[curr][j][k + 1] + dp[prev][j][k]) % MOD;
                }
            }
            // 当前 i = 'P'
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    dp[curr][j][0] = (dp[curr][j][0] + dp[prev][j][k]) % MOD;
                }
            }
        }
        int ans = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 3; k++) {
                ans = (ans + dp[curr][j][k]) % MOD;
            }
        }
        return ans;
    }

}
