package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3971 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3971 {

    /**
     * @Description:
     * 给你两个整数数组 value 和 decay，以及一个整数 m。
     * value[i] 表示下标 i 的初始价值。
     * decay[i] 表示每次选择下标 i 后，该下标的价值会减少的数值。
     * 你可以多次 选择 任意下标。所有下标的总选择次数不得超过 m。
     * 如果重复选择下标 i，第 t 次（从 1 开始计数）获得的价值为 value[i] - decay[i] * (t - 1)。
     * 返回你能够获得的 最大 总价值。由于答案可能很大，请返回其对 10^9 + 7 取模后的结果。
     *
     * tips:
     * 1 <= value.length == decay.length <= 10^5
     * 1 <= value[i], decay[i] <= 10^9
     * 1 <= m <= 10^9
     * @param: value
     * @param: decay
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/09/08 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxTotalValue(int[] value, int[] decay, int m) {
        int result;
        result = method_01(value, decay, m);
        return result;
    }

    /**
     * @Description:
     * 1. 共计可以选择 m 次, 不一定选满
     * 2. 经过 x 次选择后, 此时数组的 value 最大值是 y1
     * 3. 可以通过二分查找确定最大的 value 值, 查找范围是 [max, 0], max = Math.max(value[i])
     * 4. 假设 mid 是最大 value 值, 那么可以计算出选择次数, 以及得到的总价值
     * Error: 517/561
     * todo
     * @param: value
     * @param: decay
     * @param: m
     * @return int
     * @author marks
     * @CreateDate: 2026/09/08 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] value, int[] decay, int m) {
        int n = value.length;
        int MOD = 1_000_000_007;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = value[i];
            arr[i][1] = decay[i];
        }
        Arrays.sort(arr, (a, b) -> b[0] - a[0]); // Sort in descending order of value
        int left = 0, right = arr[0][0];
        long ans = check(arr, 0, m);

        while (left <= right) {
            int mid = left + (right - left) / 2;
            long sumValue = check(arr, mid, m);
            if (sumValue >= ans) {
                ans = sumValue;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return (int) (ans % MOD);
    }

    private long check(int[][] arr, int mid, int m) {
        long totalValue = 0;
        for (int[] item : arr) {
            if (item[0] < mid || m <= 0) {
                break;
            }
            int value = item[0];
            int decay = item[1];
            // 将 value 降低至 > mid 能获得的最大价值
            int t = Math.min((value - mid) / decay + 1, m);
            // 判断能否降低 t 次
            m -= t;
            // 计算总价值, (首项 + 末项) * t / 2
            totalValue += (long) t * (value + (value - (long) decay * (t - 1))) / 2;
        }
        return totalValue;
    }

}
