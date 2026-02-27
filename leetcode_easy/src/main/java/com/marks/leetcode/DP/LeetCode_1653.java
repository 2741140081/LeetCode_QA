package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1653 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/27 9:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1653 {

    /**
     * @Description:
     * 给你一个字符串 s ，它仅包含字符 'a' 和 'b'。
     * 你可以删除 s 中任意数目的字符，使得 s 平衡 。
     * 当不存在下标对 (i,j) 满足 i < j ，且 s[i] = 'b' 的同时 s[j]= 'a' ，此时认为 s 是 平衡 的。
     *
     * 请你返回使 s 平衡 的 最少 删除次数。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumDeletions(String s) {
        int result;
        result = method_01(s);
        result = method_02(s);
        return result;
    }

    /**
     * @Description:
     * 1. 滚动数组优化空间复杂度
     * AC: 28ms/46.82MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 10:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int[][] dp = new int[2][2];
        int curr = 0;
        int prev = 0;
        // 初始化dp[0]
        dp[0][0] = chars[0] == 'a' ? 0 : 1;
        dp[0][1] = chars[0] == 'b' ? 0 : 1;
        for (int i = 1; i < n; i++) {
            curr = i % 2;
            prev = 1 - curr;
            if (chars[i] == 'a') {
                dp[curr][0] = dp[prev][0];
                dp[curr][1] = dp[prev][1] + 1;
            } else {
                dp[curr][0] = dp[prev][0] + 1;
                dp[curr][1] = Math.min(dp[prev][0], dp[prev][1]);
            }
        }
        return Math.min(dp[curr][0], dp[curr][1]);
    }

    /**
     * @Description:
     * 1. 前缀和, 分别统计[n - 1, 0] 集合中'a' 的个数 suffix_countA[], [0, n - 1] 集合中'b' 的个数 prev_countB[]
     * 2. 现在进行遍历, 当前 位于 i 处, 要使得 s 平衡, bCount = prev_countB[i - 1], 后面有 aCount = suffix_countA[i + 1]
     * 3. 删除 bCount 个 'b', 并且删除 aCount 个 'a', 删除次数 sum[i] = bCount + aCount
     * 4. 应该直接进行动态规划, int[][] dp = new int[n][2]; dp[i][0] 表示 以 'a' 结尾需要删除的次数, dp[i][1] 表示 以 'b' 结尾需要删除的次数
     * 5. s[i], 假设 s[i] = 'a'; dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1] + 1); 不对 dp[i][0] = dp[i - 1][0]; dp[i][1] = dp[i - 1][1] + 1;
     * 6. s[i], 假设 s[i] = 'b'; dp[i][0] = dp[i - 1][0] + 1; dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]);
     * AC: 97ms/61.27MB
     * 7. 由于 dp[i] 只与 dp[i - 1] 有关, 可以用滚动数组进行优化
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 9:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        int[][] dp = new int[n][2];
        // 初始化 dp[0]
        dp[0][0] = chars[0] == 'a' ? 0 : 1;
        dp[0][1] = chars[0] == 'b' ? 0 : 1;
        for (int i = 1; i < n; i++) {
            if (chars[i] == 'a') {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] + 1;
            } else {
                dp[i][0] = dp[i - 1][0] + 1;
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]);
            }
        }
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }

}
