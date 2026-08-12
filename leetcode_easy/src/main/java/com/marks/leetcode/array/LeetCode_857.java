package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_857 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 14:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_857 {

    /**
     * @Description:
     * 有 n 名工人。 给定两个数组 quality 和 wage ，其中，quality[i] 表示第 i 名工人的工作质量，其最低期望工资为 wage[i] 。
     * 现在我们想雇佣 k 名工人组成一个 工资组。在雇佣 一组 k 名工人时，我们必须按照下述规则向他们支付工资：
     * 对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
     * 工资组中的每名工人至少应当得到他们的最低期望工资。
     * 给定整数 k ，返回 组成满足上述条件的付费群体所需的最小金额 。与实际答案误差相差在 10-5 以内的答案将被接受。
     *
     * tips:
     * n == quality.length == wage.length
     * 1 <= k <= n <= 10^4
     * 1 <= quality[i], wage[i] <= 10^4
     * @param: quality
     * @param: wage
     * @param: k
     * @return double
     * @author marks
     * @CreateDate: 2026/05/11 14:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        double result;
        result = method_01(quality, wage, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： quality = [10,20,5], wage = [70,50,30], k = 2
     * 输出： 105.00000
     * 1. 动态规划被排除, 因为时间复杂度是 O(n^2) 会超时, 感觉合适的方式是使用排序 + 队列的方式
     * 2. 假设将[a, b] [c, d] 组成一个队伍, 总工资是 a/(a + c) = b/sum => sum = b * (a +c) / a,
     * 然后判断 b 是否符合要求 c/(a + c) * sum => c1 = b * c / a >= d, 则符合要求 a1 = a * d / c
     * @param: quality
     * @param: wage
     * @param: k
     * @return double
     * @author marks
     * @CreateDate: 2026/05/11 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[] quality, int[] wage, int k) {
        // 获取工人数组长度
        int n = quality.length;
        // 创建索引数组，用于后续排序
        Integer[] h = new Integer[n];
        for (int i = 0; i < n; i++) {
            h[i] = i;
        }
        // 根据性价比（工资/质量）对索引进行排序
        Arrays.sort(h, (a, b) -> {
            return quality[b] * wage[a] - quality[a] * wage[b];
        });
        // 初始化结果变量为一个很大的值
        double res = 1e9;
        // 总质量变量，用于计算总工资
        double totalq = 0.0;
        // 使用最大堆来维护当前选中的k-1个工人中质量最小的
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        // 遍历前k-1个工人，将其质量加入总质量，并加入堆中
        for (int i = 0; i < k - 1; i++) {
            totalq += quality[h[i]];
            pq.offer(quality[h[i]]);
        }
        // 从第k-1个工人开始遍历，每次尝试将当前工人加入队伍
        for (int i = k - 1; i < n; i++) {
            int idx = h[i];
            // 将当前工人的质量加入总质量
            totalq += quality[idx];
            // 将当前工人的质量加入堆中
            pq.offer(quality[idx]);
            // 计算当前队伍的总工资
            double totalc = ((double) wage[idx] / quality[idx]) * totalq;
            // 更新最小总工资
            res = Math.min(res, totalc);
            // 移除堆中质量最大的工人，保持堆的大小为k-1
            totalq -= pq.poll();
        }
        return res;
    }
}
