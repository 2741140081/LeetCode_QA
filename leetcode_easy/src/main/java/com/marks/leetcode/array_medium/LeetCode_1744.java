package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1744 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1 14:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1744 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的正整数数组 candiesCount ，其中 candiesCount[i] 表示你拥有的第 i 类糖果的数目。
     * 同时给你一个二维数组 queries ，其中 queries[i] = [favoriteTypei, favoriteDayi, dailyCapi] 。
     * 你按照如下规则进行一场游戏：
     * 你从第 0 天开始吃糖果。
     * 你在吃完 所有 第 i - 1 类糖果之前，不能 吃任何一颗第 i 类糖果。
     * 在吃完所有糖果之前，你必须每天 至少 吃 一颗 糖果。
     * 请你构建一个布尔型数组 answer ，用以给出 queries 中每一项的对应答案。此数组满足：
     * answer.length == queries.length 。answer[i] 是 queries[i] 的答案。
     * answer[i] 为 true 的条件是：在每天吃 不超过 dailyCapi 颗糖果的前提下，
     * 你可以在第 favoriteDayi 天吃到第 favoriteTypei 类糖果；否则 answer[i] 为 false 。
     * 注意，只要满足上面 3 条规则中的第二条规则，你就可以在同一天吃不同类型的糖果。
     * 请你返回得到的数组 answer 。
     *
     * tips:
     * 1 <= candiesCount.length <= 10^5
     * 1 <= candiesCount[i] <= 10^5
     * 1 <= queries.length <= 10^5
     * queries[i].length == 3
     * 0 <= favoriteTypei < candiesCount.length
     * 0 <= favoriteDayi <= 10^9
     * 1 <= dailyCapi <= 10^9
     * @param: candiesCount
     * @param: queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/09/01 14:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] canEat(int[] candiesCount, int[][] queries) {
        boolean[] result;
        result = method_01(candiesCount, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 对糖果进行前缀和处理, 得到前缀和数组 prefixSum
     * 2. 每天可以吃糖果的数量是 [1, dailyCapi] 个, 那么到达第 favoriteDayi 天时, 假设每天只吃一颗糖果,
     * 在到达 favoriteDayi 之前, 会吃掉 favoriteDayi 个糖果, 如果 prefixSum[favoriteTypei + 1] <= favoriteDayi, 则 ans[i] = false
     * 3. 如果每天吃 dailyCapi 个糖果, 在 favoriteDayi 天之前, 最多可以吃掉 favoriteDayi * dailyCapi 个糖果, 如果 prefixSum[favoriteTypei] > favoriteDayi * dailyCapi, 则 ans[i] = false
     * 4. 综合两种情况的反面情况, ans[i] = true
     * AC: 4ms/139.34MB
     * @param: candiesCount
     * @param: queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/09/01 14:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean[] method_01(int[] candiesCount, int[][] queries) {
        int n = candiesCount.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + candiesCount[i];
        }
        int m = queries.length;
        boolean[] ans = new boolean[m];
        for (int i = 0; i < m; i++) {
            int favoriteTypei = queries[i][0];
            int favoriteDayi = queries[i][1];
            int dailyCapi = queries[i][2];
            if (prefixSum[favoriteTypei + 1] > favoriteDayi && prefixSum[favoriteTypei] < (long) (favoriteDayi + 1) * dailyCapi) {
                ans[i] = true;
            }
        }

        return ans;
    }

}
