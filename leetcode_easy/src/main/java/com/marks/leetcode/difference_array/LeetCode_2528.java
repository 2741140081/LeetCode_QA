package com.marks.leetcode.difference_array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2528 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/12 9:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2528 {

    /**
     * @Description:
     * 给你一个下标从 0 开始长度为 n 的整数数组 stations ，其中 stations[i] 表示第 i 座城市的供电站数目。
     * 每个供电站可以在一定 范围 内给所有城市提供电力。换句话说，如果给定的范围是 r ，
     * 在城市 i 处的供电站可以给所有满足 |i - j| <= r 且 0 <= i, j <= n - 1 的城市 j 供电。
     * |x| 表示 x 的 绝对值 。比方说，|7 - 5| = 2 ，|3 - 10| = 7 。
     * 一座城市的 电量 是所有能给它供电的供电站数目。
     * 政府批准了可以额外建造 k 座供电站，你需要决定这些供电站分别应该建在哪里，这些供电站与已经存在的供电站有相同的供电范围。
     * 给你两个整数 r 和 k ，如果以最优策略建造额外的发电站，返回所有城市中，最小电量的最大值是多少。
     * 这 k 座供电站可以建在多个城市。
     *
     * tips:
     * n == stations.length
     * 1 <= n <= 10^5
     * 0 <= stations[i] <= 10^5
     * 0 <= r <= n - 1
     * 0 <= k <= 10^9
     * @param: stations
     * @param: r
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/12 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxPower(int[] stations, int r, int k) {
        long result;
        result = method_01(stations, r, k);
        return result;
    }

    /**
     * @Description:
     * 1. 先分析不添加额外k座供电站的情况, 那么可以通过差分数组的方式, 得到每一座城市的电量值
     * 2. 如果给每一座城市电量都增加1, 至少需要多少座额外的发电站? 并且 k 座额外供电站能提供的总电量为 k * (1 + 2 * r)
     * AC: 52ms/83.88MB
     * @param: stations
     * @param: r
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/06/12 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] stations, int r, int k) {
        int n = stations.length;
        long[] cnt = new long[n + 1];
        for (int i = 0; i < n; i++) {
            int left = Math.max(0, i - r);
            int right = Math.min(n - 1, i + r);
            cnt[left] += stations[i];
            cnt[right + 1] -= stations[i];
        }
        long lo = Arrays.stream(stations).min().getAsInt();
        long hi = Arrays.stream(stations).asLongStream().sum() + k;
        long res = 0;

        // 通过二分查找找到最小的电量值
        while (lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            if (check(cnt, mid, r, k)) {
                res = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return res;
    }

    private boolean check(long[] cnt, long val, int r, int k) {
        int n = cnt.length - 1;
        long[] diff = cnt.clone();
        long sum = 0;
        long remaining = k;

        for (int i = 0; i < n; i++) {
            sum += diff[i];
            if (sum < val) {
                long add = val - sum;
                if (remaining < add) {
                    return false;
                }
                remaining -= add;
                int end = Math.min(n, i + 2 * r + 1);
                diff[end] -= add;
                sum += add;
            }
        }
        return true;
    }

}
