package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_956 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/14 14:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_956 {

    /**
     * @Description:
     * 你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
     * 你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
     * 返回 广告牌的最大可能安装高度 。如果没法安装广告牌，请返回 0 。
     *
     * tips:
     * 0 <= rods.length <= 20
     * 1 <= rods[i] <= 1000
     * sum(rods[i]) <= 5000
     * @param: rods
     * @return int
     * @author marks
     * @CreateDate: 2026/04/14 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int tallestBillboard(int[] rods) {
        int result;
        result = method_01(rods);
        result = method_02(rods);
        return result;
    }

    private int method_02(int[] rods) {
        // total 为 sum(rods[i])
        int total = 0;
        for (int rod : rods) {
            total += rod;
        }
        int n = rods.length;
        // dp[i][j] 表示第 i 根钢筋, 差值为 j 时, 两个刚支架的高度之和的最大值, 初始值为 -∞,
        int[][] dp = new int[n + 1][total + 1];
        Arrays.fill(dp[0], Integer.MIN_VALUE);
        // dp[0][0] = 0 表示当两个钢支架都为空时, 长度之和为 0
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            // 处理第 i 根钢筋
            int rod = rods[i - 1];
            // 对差值进行遍历, 差值的可能性时 [0 ~ total]
            for (int j = 0; j <= total; j++) {
                // 忽略第 i 根钢筋, dp[i][j] = dp[i - 1][j]
                dp[i][j] = dp[i - 1][j];
                // p1: 将 i 放在短的支架一侧, 即前一个差值时 j + rod -> j, 缩小了 rod 差值, 并且总长度增加 rod
                // p2: 将 i 放在长的支架一侧, 即前个差值为 |j - rod| -> j, j 有可能可能扩大或者缩小, 并且总长度增加 rod
                int p1 = j + rod, p2 = Math.abs(j - rod);
                // p1 必须要小于 total, 并且前一个 dp[i - 1][p1] 已经赋值
                if (p1 <= total && dp[i - 1][p1] != Integer.MIN_VALUE) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][p1] + rod);
                }
                if (dp[i - 1][p2] != Integer.MIN_VALUE) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][p2] + rod);
                }
            }
        }
        // 由于记录的时两个刚支架的长度之和, 所以结果除以2之后返回
        return dp[n][0] / 2;
    }

    /**
     * @Description:
     * 1. 使用动态规划, dp[i][j] = k, 其中 i 表示 第 i 根钢筋, j 表示 当前的差值, k 表示 公共高度
     * 2. 对于 第 i 根钢筋, 有3种方案, temp = rods[i]
     * 2.1 忽略第 i 根钢筋, dp[i][j] = dp[i - 1][j]
     * 2.2 将第 i 根钢筋添加到短的一侧, 并且添加后仍然是短的, 即 j < rods[i], 那么此时的会缩小差值, 并且增加公共长度
     * 得到 dp[i][j] = Math.max(dp[i][j], dp[i - 1][j + temp] + temp), 要求是 j > temp
     * 2.3 将 i 根钢筋添加到短的一侧, 并且添加后的长度超过了长的一侧, 此时的差值 j, 那么之前的差值是 temp - j, 增加的公共长度是 temp - j
     * 得到此时的 dp[i][j] = Math.max(dp[i][j], dp[i - 1][temp - j] + (temp - j)), 要求是 j <= temp
     * 2.4 将 i 添加到长的一侧, 则差值会增大, 且公共长度保持不变 dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - temp])
     * 3. 由于 i 仅与 i - 1 存在关联, 所以使用滚动数组优化空间复杂度
     * 4. 对 2.3 进行再分析, 假设短的是 x1, 长的是 x2, int s1 = x2 - x1. 添加后长度超过 x2, 此时的 j = (x1 + temp) - x2;
     * x2 - x1 = temp - j
     * 5. 只有两种情况, j + rod 和 |j - rod|, 由于需要从有效prev 进行转移到当前curr, 并且当前差值时 j, 那么前一个差值时 j + rod 或者
     * |j - rod| 两种情况, 并且总长度增加 rod.
     * AC: 16ms/44.06MB
     * @param: rods
     * @return int
     * @author marks
     * @CreateDate: 2026/04/14 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] rods) {
        int n = rods.length;
        int INF = Integer.MIN_VALUE / 2;
        int sum = Arrays.stream(rods).sum();
        int[][] dp = new int[2][sum + 1];
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], INF);
        }
        dp[0][0] = 0;
        int prev;
        int curr = 0;
        for (int i = 1; i <= n; i++) {
            curr = i % 2;
            prev = 1 - curr;
            int rod = rods[i - 1];
            for (int j = 0; j <= sum; j++) {
                // 忽略第 i 根钢筋
                dp[curr][j] = dp[prev][j];
                // 将 i 添加到短的侧
                if (j + rod <= sum && dp[prev][j + rod] != INF) {
                    dp[curr][j] = Math.max(dp[curr][j], dp[prev][j + rod] + rod);
                }
                int abs = Math.abs(j - rod);
                // 将 i 添加到长的侧
                if (dp[prev][abs] != INF) { // j > rod, 防止从负数的非法转移
                    dp[curr][j] = Math.max(dp[curr][j], dp[prev][abs] + rod);
                }
            }
        }

        return dp[curr][0] / 2;
    }

}
