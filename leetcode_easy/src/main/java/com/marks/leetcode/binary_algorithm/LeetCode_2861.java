package com.marks.leetcode.binary_algorithm;

import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/27 10:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2861 {
    /**
     * @Description: [
     * 假设你是一家合金制造公司的老板，你的公司使用多种金属来制造合金。现在共有 n 种不同类型的金属可以使用，并且你可以使用 k 台机器来制造合金。
     * 每台机器都需要特定数量的每种金属来创建合金。
     *
     * 对于第 i 台机器而言，创建合金需要 composition[i][j] 份 j 类型金属。
     * 最初，你拥有 stock[i] 份 i 类型金属，而每购入一份 i 类型金属需要花费 cost[i] 的金钱。
     *
     * 给你整数 n、k、budget，下标从 1 开始的二维数组 composition，两个下标从 1 开始的数组 stock 和 cost，
     * 请你在预算不超过 budget 金钱的前提下，最大化 公司制造合金的数量。
     *
     * 所有合金都需要由同一台机器制造。
     *
     * 返回公司可以制造的最大合金数。
     * tips:
     * 1 <= n, k <= 100
     * 0 <= budget <= 10^8
     * composition.length == k
     * composition[i].length == n
     * 1 <= composition[i][j] <= 100
     * stock.length == cost.length == n
     * 0 <= stock[i] <= 10^8
     * 1 <= cost[i] <= 100
     * ]
     * @param n
     * @param k
     * @param budget
     * @param composition
     * @param stock
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int result;
        result = method_01(n, k, budget, composition, stock, cost);
        return result;
    }

    /**
     * @Description: [
     * 官方题解:
     * AC:51ms/44.21MB
     * ]
     * @param n
     * @param k
     * @param budget
     * @param composition
     * @param stock
     * @param cost
     * @return int
     * @author marks
     * @CreateDate: 2024/11/27 10:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int left = 1, right = 200000000, ans = 0;
        while (left <= right) {
            int mid = (left + right) / 2;
            boolean valid = false;
            for (int i = 0; i < k; ++i) {
                long spend = 0;
                for (int j = 0; j < n; ++j) {
                    spend += Math.max((long) composition.get(i).get(j) * mid - stock.get(j), 0) * cost.get(j);
                }
                if (spend <= budget) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }
}
