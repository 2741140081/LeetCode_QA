package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/2 10:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2141 {
    /**
     * @Description:
     * 你有 n 台电脑。
     * 给你整数 n 和一个下标从 0 开始的整数数组 batteries ，其中第 i 个电池可以让一台电脑 运行 batteries[i] 分钟。
     * 你想使用这些电池让 全部 n 台电脑 同时 运行。
     *
     * 一开始，你可以给每台电脑连接 至多一个电池 。然后在任意整数时刻，你都可以将一台电脑与它的电池断开连接，并连接另一个电池，你可以进行这个操作 任意次 。
     * 新连接的电池可以是一个全新的电池，也可以是别的电脑用过的电池。断开连接和连接新的电池不会花费任何时间。
     *
     * 注意，你不能给电池充电。
     *
     * 请你返回你可以让 n 台电脑同时运行的 最长 分钟数。
     *
     * tips:
     * 1 <= n <= batteries.length <= 10^5
     * 1 <= batteries[i] <= 10^9
     * @param n
     * @param batteries
     * @return long
     * @author marks
     * @CreateDate: 2025/4/2 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxRunTime(int n, int[] batteries) {
        long result = method_01(n, batteries);
        return result;
    }

    /**
     * @Description:
     * 默认一个电池在任意时刻不能同时给多台电脑供电
     *
     * 1. 肯定每次使用的是b[i] 最大的 n 个电池
     * 2. 每次使用完成后需要重排序, 然后再次取 n 个 b[i]
     * 但是以上模拟的这种暴力解法肯定超时!
     *
     * 查看题解:
     * AC: 28ms/59.51MB
     * @param n
     * @param batteries
     * @return long
     * @author marks
     * @CreateDate: 2025/4/2 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[] batteries) {
        Arrays.sort(batteries);
        long sum = 0;
        for (int battery : batteries) {
            sum += battery;
        }

        for (int i = batteries.length - 1; i >= 0; i--) {
            if (batteries[i] > sum / n) {
                sum -= batteries[i];
                n--;
            } else {
                return sum / n;
            }
        }
        return sum;
    }
}
