package com.marks.leetcode.state_machine_DP;


import java.util.ArrayDeque;

/**
 * <p>项目名称: 买卖股票的最佳时机 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/23 17:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_121 {
    /**
     * @Description: [
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     *
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     *
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     *
     * tips:
     * 1 <= prices.length <= 10^5
     * 0 <= prices[i] <= 10^4
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int[] prices) {
        int result = 0;
//        result = method_01(prices);
//        result = method_02(prices);
        result = method_03(prices);
        return result;
    }

    /**
     * @Description: [
     * 使用单调栈来解决
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 17:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] prices) {
        int n = prices.length;
        if (n == 1) {
            return 0;
        }
        // 单调递增栈
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i <= n; i++) {
            int curValue = i == n ? 0 : prices[i];
            while (!stack.isEmpty() && stack.peek() >= curValue) {
                // push: 入栈, pop: 出栈, peek:获取栈顶元素, isEmpty: 判断栈是否为空
                int top = stack.pop();
                if (stack.isEmpty()) {
                    continue;
                }
                ans = Math.max(ans, top - stack.peekLast());
            }
            stack.push(curValue);
        }
        return ans;
    }

    /**
     * @Description: [
     * 记录前i的最小的价格, 让后在当天卖出, 判断价格是否是最低的
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 17:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] prices) {
        int n = prices.length;
        if (n == 1) {
            return 0;
        }
        int minPrice = prices[0];
        int ans = 0;
        for (int i = 1; i < n; i++) {
            int num = prices[i];
            if (minPrice > num) {
                minPrice = num;
            }else {
                ans = Math.max(ans, num - minPrice);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     * 看下O^2的时间复杂度是否可行, 反正是一个简单题
     * 果然不行， 超时！！！！ 10^5 级数的O^2的时间复杂度必然超时啊！！！
     * 官方题解 method_02()
     *
     * ]
     * @param prices
     * @return int
     * @author marks
     * @CreateDate: 2024/8/23 17:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] prices) {
        int n = prices.length;
        if (n <= 1) {
            return 0;
        }
        int ans = 0;
        for (int i = 0; i < n - 1; i++) {
            int num = prices[i];
            for (int j = i + 1; j < n; j++) {
                if (num < prices[j]) {
                    ans = Math.max(ans, prices[j] - num);
                }
            }
        }
        return ans;
    }
}
