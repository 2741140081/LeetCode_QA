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
     * 1. 选择 0 号 和 1 号工, 需要的最少金额: 1 : 2;
     * 2. 假设 选择 k 位工人, 分别是 k0 ~ k-1; 然后质量总和是 kSum, 并且 ki 的最低工资是 kWage; 那么需要满足规则: ki / kSum >= kWage
     * ki >= kWage * kSum ki / kWage >= kSum
     * 3. [1/7, 2/5, 1/6] 然后从中选择 k 个最小的值, [1/7, 1/6] 然后以 1/7 作为基准,105.00000
     * @param: quality
     * @param: wage
     * @param: k
     * @return double
     * @author marks
     * @CreateDate: 2026/05/11 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[] quality, int[] wage, int k) {
        int n = quality.length;
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        Arrays.sort(index, (a, b) -> quality[b] * wage[a] - quality[a] * wage[b]);



        return 0;
    }
}
