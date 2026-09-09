package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2448 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 10:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2448 {

    /**
     * @Description:
     * 给你两个下标从 0 开始的数组 nums 和 cost ，分别包含 n 个 正 整数。
     * 你可以执行下面操作 任意 次：
     * 将 nums 中 任意 元素增加或者减小 1 。
     * 对第 i 个元素执行一次操作的开销是 cost[i] 。
     * 请你返回使 nums 中所有元素 相等 的 最少 总开销。
     *
     * tips:
     * n == nums.length == cost.length
     * 1 <= n <= 10^5
     * 1 <= nums[i], cost[i] <= 10^6
     * 测试用例确保输出不超过 2^53-1。
     * @param: nums
     * @param: cost
     * @return long
     * @author marks
     * @CreateDate: 2026/09/08 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minCost(int[] nums, int[] cost) {
        long result;
        result = method_01(nums, cost);
        return result;
    }

    /**
     * @Description:
     * 1. 处理 i, 假设 i 与前 i 个元素不同, 前 i 个元素值已经相等为 preNum,
     * 并且前 i 个元素降低1的 开销为 preCost, 后 i 个元素提升1的开销是 sumCost - preCost
     * 2. 还需要记录已经提高的次数 raiseCount
     * AC: 17ms/62.72MB
     * @param: nums
     * @param: cost
     * @return long
     * @author marks
     * @CreateDate: 2026/09/08 10:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int[] cost) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        long sumCost = 0;
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = cost[i];
            sumCost += cost[i];
        }
        // 对 nums[i] 进行降序排序
        Arrays.sort(arr, (a, b) -> b[0] - a[0]);
        long preCost = 0;
        int preNum = arr[0][0];
        int raiseCount = 0;
        long ans = 0; // 总开销
        for (int i = 0; i < n; i++) {
            int num = arr[i][0];
            int currCost = arr[i][1];
            if (num + raiseCount != preNum) {
                if (preCost >= sumCost - preCost) {
                    // 需要提高后面的数
                    int sub = preNum - (num + raiseCount);
                    ans += (long) sub * (sumCost - preCost);
                    raiseCount += sub;
                } else {
                    // 将前 i 个数降低到 num + raiseCount
                    int sub = preNum - (num + raiseCount);
                    ans +=  (long) sub * preCost;
                    preNum = num + raiseCount; // 更新 preNum
                }
            }
            preCost += currCost;
        }

        return ans;
    }

}
