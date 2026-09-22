package com.marks.leetcode.array_medium;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1701 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/22 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1701 {

    /**
     * @Description:
     * 有一个餐厅，只有一位厨师。你有一个顾客数组 customers ，其中 customers[i] = [arrivali, timei] ：
     * arrivali 是第 i 位顾客到达的时间，到达时间按 非递减 顺序排列。
     * timei 是给第 i 位顾客做菜需要的时间。
     * 当一位顾客到达时，他将他的订单给厨师，厨师一旦空闲的时候就开始做这位顾客的菜。
     * 每位顾客会一直等待到厨师完成他的订单。厨师同时只能做一个人的订单。
     * 厨师会严格按照 订单给他的顺序 做菜。
     * 请你返回所有顾客需要等待的 平均 时间。
     * 与标准答案误差在 10-5 范围以内，都视为正确结果。
     * @param: customers
     * @return double
     * @author marks
     * @CreateDate: 2026/09/22 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double averageWaitingTime(int[][] customers) {
        double result;
        result = method_01(customers);
        return result;
    }

    /**
     * @Description:
     * 1. 由于时间是一个递增(非严格递减), 所以可以采用队列存储等待的顾客
     * 2. 用 int time 来表示当前时间, 初始值为 customers[0][0]，表示第一个顾客到达的时间,
     * 更新下一个时间为 time += customers[i][1], 表示做菜的时间.
     * 3. long sumTime = 0; 表示所有顾客等待时间的总和, 当从队列中取出一个顾客时,
     * sumTime += time - customers[i][0]; 表示等待时间的总和
     * 4. 那么也就不需要队列, 直接遍历数组即可, 并且当 customers[i][0] > time 时, 表示当前顾客到达的时间大于当前时间,
     * 此时需要更新时间 time = customers[i][0] + customers[i][1]; 然后继续处理下一个顾客
     * 5. 理解错误等待的定义, 等待时间同样包括做菜的时间, 因此需要将做菜的时间加入等待时间的计算中
     * AC: 2ms/93.17MB
     * @param: customers
     * @return double
     * @author marks
     * @CreateDate: 2026/09/22 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[][] customers) {
        int n = customers.length;
        int time = 0;
        long sumTime = 0;
        for (int[] customer : customers) {
            if (customer[0] >= time) {
                time = customer[0] + customer[1];
            } else {
                sumTime += time - customer[0]; // 此时顾客在等待
                time += customer[1];
            }
            sumTime += customer[1]; // 顾客等待做菜的时间
        }

        return (double) sumTime / n;
    }

}
