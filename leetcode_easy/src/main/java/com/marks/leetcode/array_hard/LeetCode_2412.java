package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2412 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2412 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的二维整数数组 transactions，其中transactions[i] = [costi, cashbacki] 。
     * 数组描述了若干笔交易。其中每笔交易必须以 某种顺序 恰好完成一次。
     * 在任意一个时刻，你有一定数目的钱 money ，为了完成交易 i ，money >= costi 这个条件必须为真。
     * 执行交易后，你的钱数 money 变成 money - costi + cashbacki 。
     * 请你返回 任意一种 交易顺序下，你都能完成所有交易的最少钱数 money 是多少。
     *
     * tips:
     * 1 <= transactions.length <= 10^5
     * transactions[i].length == 2
     * 0 <= costi, cashbacki <= 10^9
     * @param: transactions
     * @return long
     * @author marks
     * @CreateDate: 2026/09/08 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumMoney(int[][] transactions) {
        long result;
        result = method_01(transactions);
        return result;
    }

    /**
     * @Description:
     * 1. 思路, 先处理那些会使得 money 变小的交易, cost_i > cashback_i, long initMoney = 0;
     * 并且对 transactions 进行分组, 将 cost_i <= cashback_i 的交易分组, 将 cost_i > cashback_i 的交易分组
     * 2. 然后对分组后的交易进行 cost_i 的降序排序
     * 3. 需要优先处理 cost_i > cashback_i 的交易, 处理第 i 个交易, long spend = 0;
     * 4. 如果 cost_i > initMoney - spend, 则将 initMoney 更新为 cost_i + spend, 此时可以成功执行该交易
     * spend += (cost_i - cashback_i), 然后继续处理下一个
     * 5. 剩下的需要处理 cost_i <= cashback_i 的交易, 此时只需要找到该分组中最大的 max_cost_i 即可, 然后判断
     * initMoney - spend 与 max_cost_i 的大小关系, 如果 initMoney - spend >= max_cost_i, 则可以成功执行所有交易,
     * 否则更新 initMoney = max_cost_i + spend, 此时已经处理完成
     * AC: 17ms/126.74MB
     * @param: transactions
     * @return long
     * @author marks
     * @CreateDate: 2026/09/08 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] transactions) {
        List<int[]> group = new ArrayList<>(); // 存储 cost_i > cashback_i 的交易
        long maxCost = 0; // 记录分组 cost_i >= cashback_i 中的最大值 max_cost_i
        for (int[] transaction : transactions) {
            if (transaction[0] > transaction[1]) {
                group.add(transaction);
            } else {
                maxCost = Math.max(maxCost, transaction[0]);
            }
        }
        // 对分组进行 cashback_i 的升序排序, 优先处理返回更小的
        group.sort(Comparator.comparingInt(a -> a[1]));
        long initMoney = 0;
        long spend = 0;
        for (int[] transaction : group) {
            if (transaction[0] > initMoney - spend) {
                initMoney = transaction[0] + spend;
            }
            spend += (transaction[0] - transaction[1]);
        }

        if (initMoney - spend >= maxCost) {
            return initMoney;
        } else {
            return maxCost + spend;
        }
    }

}
