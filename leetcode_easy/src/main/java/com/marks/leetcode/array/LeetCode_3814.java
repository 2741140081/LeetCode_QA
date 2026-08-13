package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3814 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/1 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3814 {

    /**
     * @Description:
     * 给你两个长度为 n 的整数数组 costs 和 capacity，
     * 其中 costs[i] 表示第 i 台机器的购买成本，capacity[i] 表示其性能容量。
     * 同时，给定一个整数 budget。
     * 你可以选择 最多两台不同的机器，使得所选机器的 总成本 严格小于 budget。
     * 返回可以实现的 最大总容量。
     *
     * tips:
     * 1 <= n == costs.length == capacity.length <= 10^5
     * 1 <= costs[i], capacity[i] <= 10^5
     * 1 <= budget <= 2 * 10^5
     * @param: costs
     * @param: capacity
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCapacity(int[] costs, int[] capacity, int budget) {
        int result;
        result = method_01(costs, capacity, budget);
        return result;
    }

    /**
     * @Description:
     * 1. 将两个数组合并成一个二维数组, 然后根据 arr[i][0] 进行排序.
     * 2. 排序之后的顺序进行遍历 得到 int[] maxCapacity 记录[0 ~ n - 1] 的最大容量, 相当于花费 cost[i] 能得到的最大容量.
     * 3. 由于需要使用不同的机器, 所以在处理 i 时, 需要在[0 ~ i - 1] 的区间找 maxCapacity[j], 防止拿取重复机器导致错误, 当然也可以只拿取当前一台机器.
     * AC: 145ms/176.19MB
     * @param: costs
     * @param: capacity
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2026/07/01 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] costs, int[] capacity, int budget) {
        int n = costs.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = costs[i];
            arr[i][1] = capacity[i];
        }
        // 排序
        Arrays.sort(arr, (o1, o2) -> o1[0] - o2[0]);
        int[] maxCapacity = new int[n];
        maxCapacity[0] = arr[0][1];
        for (int i = 1; i < n; i++) {
            maxCapacity[i] = Math.max(maxCapacity[i - 1], arr[i][1]);
        }

        int ans = 0;

        // 考虑只选一台机器的情况
        for (int i = 0; i < n; i++) {
            if (arr[i][0] < budget) {
                ans = Math.max(ans, arr[i][1]);
            }
        }

        // 考虑选两台不同机器的情况
        for (int i = 1; i < n; i++) {
            int remainingBudget = budget - arr[i][0];
            if (remainingBudget <= 0) {
                continue;
            }

            // 二分查找：在 [0, i-1] 中找到最大的 j，使得 arr[j][0] < remainingBudget
            int left = 0, right = i - 1;
            int bestJ = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (arr[mid][0] < remainingBudget) {
                    bestJ = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            // 如果找到了合法的 j，更新答案
            if (bestJ != -1) {
                ans = Math.max(ans, maxCapacity[bestJ] + arr[i][1]);
            }
        }

        return ans;
    }

}
