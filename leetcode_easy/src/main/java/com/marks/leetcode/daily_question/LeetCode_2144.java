package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2144 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 11:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2144 {

    /**
     * @Description:
     * 一家商店正在打折销售糖果。每购买 两个 糖果，商店会 免费 送一个糖果。
     * 免费送的糖果唯一的限制是：它的价格需要小于等于购买的两个糖果价格的 较小值 。
     * 比方说，总共有 4 个糖果，价格分别为 1 ，2 ，3 和 4 ，一位顾客买了价格为 2 和 3 的糖果，那么他可以免费获得价格为 1 的糖果，但不能获得价格为 4 的糖果。
     * 给你一个下标从 0 开始的整数数组 cost ，其中 cost[i] 表示第 i 个糖果的价格，请你返回获得 所有 糖果的 最小 总开销。
     *
     * tips:
     * 1 <= cost.length <= 100
     * 1 <= cost[i] <= 100
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumCost(int[] cost) {
        int result;
        result = method_01(cost);
        return result;
    }

    /**
     * @Description:
     * 1. 直接排序, 然后从后往前取数，取数时，int cnt 记录当前已经取了多少个数, 如果数为 cnt % 3 == 0, 则表示当前cost为0，则跳过
     * AC: 5ms/43.64MB
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 11:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] cost) {
        Arrays.sort(cost);
        int ans = 0;
        int n = cost.length;
        int cnt = 0;
        for (int i = n - 1; i >= 0; i--) {
            cnt = (cnt + 1) % 3;
            ans += cost[i] * (cnt == 0 ? 0 : 1);
        }

        return ans;
    }

}
