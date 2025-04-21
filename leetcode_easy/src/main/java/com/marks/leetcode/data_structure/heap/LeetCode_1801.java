package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/8 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1801 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 给你一个二维整数数组 orders ，其中每个 orders[i] = [pricei, amounti, orderTypei] 表示有 amounti 笔类型为 orderTypei 、价格为 pricei 的订单。
     * 订单类型 orderTypei 可以分为两种：
     * 0 表示这是一批采购订单 buy
     * 1 表示这是一批销售订单 sell
     * 注意，orders[i] 表示一批共计 amounti 笔的独立订单，这些订单的价格和类型相同。对于所有有效的 i ，由 orders[i] 表示的所有订单提交时间均早于 orders[i+1] 表示的所有订单。
     *
     * 存在由未执行订单组成的 积压订单 。积压订单最初是空的。提交订单时，会发生以下情况：
     *
     * 如果该订单是一笔采购订单 buy ，则可以查看积压订单中价格 最低 的销售订单 sell 。
     * 如果该销售订单 sell 的价格 低于或等于 当前采购订单 buy 的价格，则匹配并执行这两笔订单，并将销售订单 sell 从积压订单中删除。否则，采购订单 buy 将会添加到积压订单中。
     *
     * 反之亦然，如果该订单是一笔销售订单 sell ，则可以查看积压订单中价格 最高 的采购订单 buy 。
     * 如果该采购订单 buy 的价格 高于或等于 当前销售订单 sell 的价格，则匹配并执行这两笔订单，并将采购订单 buy 从积压订单中删除。否则，销售订单 sell 将会添加到积压订单中。
     *
     * 输入所有订单后，返回积压订单中的 订单总数 。由于数字可能很大，所以需要返回对 109 + 7 取余的结果。
     * @param orders
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getNumberOfBacklogOrders(int[][] orders) {
        int result;
        result = method_01(orders);
        return result;
    }

    /**
     * @Description:
     * AC: 53ms/81.82MB
     * @param orders
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 14:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] orders) {
        long ans = 0;
        PriorityQueue<int[]> buy = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        PriorityQueue<int[]> sell = new PriorityQueue<>(Comparator.comparingInt(o -> o[0]));
        for (int[] order : orders) {
            int price = order[0];
            int count = order[1];
            int type = order[2];
            if (type == 0) {
                // buy
                while (!sell.isEmpty() && sell.peek()[0] <= price && count > 0) {
                    int[] curr_node = sell.poll();
                    if (count > curr_node[1]) {
                        count -= curr_node[1];
                    } else if (count < curr_node[1]) {
                        curr_node[1] = curr_node[1] - count;
                        sell.offer(new int[]{curr_node[0], curr_node[1]});
                        count = -1;
                    } else {
                        count = 0;
                    }
                }
                if (count > 0) {
                    buy.offer(new int[]{price, count});
                }
            } else {
                // sell
                while (!buy.isEmpty() && buy.peek()[0] >= price && count > 0) {
                    int[] curr_node = buy.poll();
                    if (count > curr_node[1]) {
                        count -= curr_node[1];
                    } else if (count < curr_node[1]) {
                        curr_node[1] = curr_node[1] - count;
                        buy.offer(new int[]{curr_node[0], curr_node[1]});
                        count = -1;
                    } else {
                        count = 0;
                    }
                }
                if (count > 0) {
                    sell.offer(new int[]{price, count});
                }
            }
        }
        while (!buy.isEmpty()) {
            int count = buy.poll()[1];
            ans = (ans + count) % MOD;
        }
        while (!sell.isEmpty()) {
            int count = sell.poll()[1];
            ans = (ans + count) % MOD;
        }
        return (int) ans;
    }
}
