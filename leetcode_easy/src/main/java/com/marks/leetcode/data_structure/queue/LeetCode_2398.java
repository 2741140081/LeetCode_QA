package com.marks.leetcode.data_structure.queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2398 {
    /**
     * @Description:
     * 你有 n 个机器人，给你两个下标从 0 开始的整数数组 chargeTimes 和 runningCosts ，两者长度都为 n 。
     * 第 i 个机器人充电时间为 chargeTimes[i] 单位时间，花费 runningCosts[i] 单位时间运行。再给你一个整数 budget 。
     *
     * 运行 k 个机器人 总开销 是 max(chargeTimes) + k * sum(runningCosts) ，其中 max(chargeTimes) 是这 k 个机器人中最大充电时间，
     * sum(runningCosts) 是这 k 个机器人的运行时间之和。
     *
     * 请你返回在 不超过 budget 的前提下，你 最多 可以 连续 运行的机器人数目为多少。
     * @param chargeTimes
     * @param runningCosts
     * @param budget
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int result;
        result = method_01(chargeTimes, runningCosts, budget);
        return result;
    }

    /**
     * @Description:
     * 1. 找到滑动窗口中的最大值 chargeTimes[i], 单调递增的双端队列
     * 2. 滑动窗口的和sum, 注意为long 类型
     * 3. 已知条件和前提: max(chargeTimes) + k * sum(runningCosts) <= budget
     * AC:23ms/55.07MB
     * @param chargeTimes
     * @param runningCosts
     * @param budget
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] chargeTimes, int[] runningCosts, long budget) {
        int n = chargeTimes.length;
        int ans = 0, left = 0, right = 0;
        long sum = 0;
        Deque<Integer> queMax = new LinkedList<>(); // 单调递增队列, 找最大值

        while (right < n) {
            int num = chargeTimes[right];
            while (!queMax.isEmpty() && queMax.peekLast() < num) {
                queMax.pollLast();
            }
            queMax.offerLast(num);
            sum += runningCosts[right];
            while (!queMax.isEmpty() && (queMax.peekFirst() + (right - left + 1) * sum) > budget) {
                sum -= runningCosts[left];
                if (queMax.peekFirst() == chargeTimes[left]) {
                    queMax.pollFirst();
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }
}
