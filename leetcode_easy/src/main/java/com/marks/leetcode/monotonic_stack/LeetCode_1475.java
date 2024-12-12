package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 9:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1475 {
    /**
     * @Description: [
     * 给你一个数组 prices ，其中 prices[i] 是商店里第 i 件商品的价格。
     *
     * 商店里正在进行促销活动，如果你要买第 i 件商品，那么你可以得到与 prices[j] 相等的折扣，
     * 其中 j 是满足 j > i 且 prices[j] <= prices[i] 的 最小下标 ，如果没有满足条件的 j ，你将没有任何折扣。
     *
     * 请你返回一个数组，数组中第 i 个元素是折扣后你购买商品 i 最终需要支付的价格。
     *
     * tips:
     * 1 <= prices.length <= 500
     * 1 <= prices[i] <= 10^3
     * ]
     * @param prices 
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] finalPrices(int[] prices) {
        int[] result;
        result = method_01(prices);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：prices = [8,4,6,2,3]
     * 输出：[4,2,4,2,3]
     * 解释：
     * 商品 0 的价格为 price[0]=8 ，你将得到 prices[1]=4 的折扣，所以最终价格为 8 - 4 = 4 。
     * 商品 1 的价格为 price[1]=4 ，你将得到 prices[3]=2 的折扣，所以最终价格为 4 - 2 = 2 。
     * 商品 2 的价格为 price[2]=6 ，你将得到 prices[3]=2 的折扣，所以最终价格为 6 - 2 = 4 。
     * 商品 3 和 4 都没有折扣。
     *
     * 即找到prices[i] 右侧第一个小于等于的商品价格prices[j]
     *
     * AC:3ms/43.51MB
     * ]
     * @param prices
     * @return int[]
     * @author marks
     * @CreateDate: 2024/11/28 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] prices) {
        int n = prices.length;
        int[] ans = new int[n];
        // 单调栈
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            ans[i] = prices[i];
            int discount = prices[i];
            while (!stack.isEmpty() && prices[stack.peek()] >= discount) {
                Integer preIndex = stack.poll();
                ans[preIndex] = prices[preIndex] - discount;
            }
            stack.push(i);
        }
        return ans;
    }
}
