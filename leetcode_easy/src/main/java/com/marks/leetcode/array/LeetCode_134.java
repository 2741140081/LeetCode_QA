package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_134 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/26 10:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_134 {

    /**
     * @Description:
     * 在一条环路上有 n 个加油站，其中第 i 个加油站有汽油 gas[i] 升。
     * 你有一辆油箱容量无限的的汽车，从第 i 个加油站开往第 i+1 个加油站需要消耗汽油 cost[i] 升。你从其中的一个加油站出发，开始时油箱为空。
     * 给定两个整数数组 gas 和 cost ，如果你可以按顺序绕环路行驶一周，则返回出发时加油站的编号，否则返回 -1 。如果存在解，则 保证 它是 唯一 的。
     * tips:
     * n == gas.length == cost.length
     * 1 <= n <= 10^5
     * 0 <= gas[i], cost[i] <= 10^4
     * 输入保证答案唯一。
     * @param: gas
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 10:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int result;
        result = method_01(gas, cost);
        result = method_02(gas, cost);
        return result;
    }

    /**
     * @Description:
     * int[] sub = {-2,-2,-2,3,3}
     * 1. 假设存在解, 那么如果找到一个sub 数组的子数组, 使得其累积和最大, 使用不定长的滑动窗口, 并且使用left 记录当max最大时的节点 index
     * 2. 果然是有些贪心的思想在
     * AC: 5ms/117.05MB
     * @param: gas
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 11:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] gas, int[] cost) {
        int sumGas = 0;
        int sumCost = 0;
        this.n = gas.length;
        int[] sub = new int[n]; // 差值
        for (int i = 0; i < n; i++) {
            sub[i] = gas[i] - cost[i];
            sumGas += gas[i];
            sumCost += cost[i];
        }
        if (sumGas < sumCost) {
            // 不存在解
            return -1;
        }
        int init = -1; // 记录初始位置
        int left = 0;
        int sum = 0;
        int max = 0;
        for (int i = 0; i < 2 * n; i++) {
            sum += sub[i % n];
            while (sum < 0) {
                sum -= sub[left];
                left = (left + 1) % n;
            }
            if (sum >= max) {
                max = sum;
                init = left;
            }
        }
        // check init
        sumGas = 0;
        sumCost = 0;
        for (int i = init; i < init + n; i++) {
            sumGas += gas[i % n];
            sumCost += cost[i % n];
            if (sumGas < sumCost) {
                return -1;
            }
        }
        return init;
    }

    /**
     * @Description:
     * 输入: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * 输出: 3
     * 1. 直接暴力, 先将那些能进行启动的点添加到队列中, gas[i] >= cost[i]
     * 2. 是否只能进行按顺序遍历进行行驶, 无法倒序行驶?
     * 3. 暴力超时 35/40
     * @param: gas
     * @param: cost
     * @return int
     * @author marks
     * @CreateDate: 2025/12/26 10:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int n;
    private int method_01(int[] gas, int[] cost) {
        // 是否存在解
        int sumGas = 0;
        int sumCost = 0;
        this.n = gas.length;
        for (int i = 0; i < n; i++) {
            sumGas += gas[i];
            sumCost += cost[i];
        }
        if (sumGas < sumCost) {
            // 不存在解
            return -1;
        }

        for (int i = 0; i < n; i++) {
            if (gas[i] >= cost[i]) {
                // 可以进行启动, 执行dfs
                if (dfs(gas, cost, i, 0, 0)) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean dfs(int[] gas, int[] cost, int i, int sum, int step) {
        if (sum < 0) {
            return false;
        }
        if (step == n) {
            return true;
        }

        sum = sum - cost[i] + gas[i];
        return dfs(gas, cost, (i + 1) % n, sum, step + 1);
    }

}
