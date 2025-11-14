package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_808 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/12 17:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_808 {

    /**
     * @Description:
     * 你有两种汤，A 和 B，每种初始为 n 毫升。
     * 在每一轮中，会随机选择以下四种操作中的一种，每种操作的概率为 0.25，且与之前的所有轮次 无关：
     * 从汤 A 取 100 毫升，从汤 B 取 0 毫升
     * 从汤 A 取 75 毫升，从汤 B 取 25 毫升
     * 从汤 A 取 50 毫升，从汤 B 取 50 毫升
     * 从汤 A 取 25 毫升，从汤 B 取 75 毫升
     * 注意：
     *
     * 不存在从汤 A 取 0 ml 和从汤 B 取 100 ml 的操作。
     * 汤 A 和 B 在每次操作中同时被取出。
     * 如果一次操作要求你取出比剩余的汤更多的量，请取出该汤剩余的所有部分。
     * 操作过程在任何回合中任一汤被取完后立即停止。
     *
     * 返回汤 A 在 B 前取完的概率，加上两种汤在 同一回合 取完概率的一半。返回值在正确答案 10^-5 的范围内将被认为是正确的。
     * tips: 0 <= n <= 10^9
     * @param: n
     * @return double
     * @author marks
     * @CreateDate: 2025/11/12 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double soupServings(int n) {
        double result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * n = 50, 那么汤A在B之前去完的概率是 0.5, 加上A和B在同一回合去完的概率的一半 0.25 / 2 = 0.125
     * 结果: 0.625
     * int[][] dp = new int[n][n];
     * @param: n
     * @return double
     * @author marks
     * @CreateDate: 2025/11/12 17:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private double[][] memo;
    private double method_01(int n) {
        n = (int) Math.ceil((double) n / 25);
        if (n >= 179) {
            return 1.0;
        }
        memo = new double[n + 1][n + 1];
        return dfs(n, n);
    }

    public double dfs(int a, int b) {
        if (a <= 0 && b <= 0) {
            return 0.5;
        } else if (a <= 0) {
            return 1;
        } else if (b <= 0) {
            return 0;
        }
        if (memo[a][b] == 0) {
            memo[a][b] = 0.25 * (dfs(a - 4, b) + dfs(a - 3, b - 1) + dfs(a - 2, b - 2) + dfs(a - 1, b - 3));
        }
        return memo[a][b];
    }

}
