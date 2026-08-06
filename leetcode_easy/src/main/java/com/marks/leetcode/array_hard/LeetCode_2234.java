package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2234 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/6 11:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2234 {

    /**
     * @Description:
     * Alice 是 n 个花园的园丁，她想通过种花，最大化她所有花园的总美丽值。
     * 给你一个下标从 0 开始大小为 n 的整数数组 flowers ，其中 flowers[i] 是第 i 个花园里已经种的花的数目。
     * 已经种了的花 不能 移走。同时给你 newFlowers ，表示 Alice 额外可以种花的 最大数目 。
     * 同时给你的还有整数 target ，full 和 partial 。
     * 如果一个花园有 至少 target 朵花，那么这个花园称为 完善的 ，花园的 总美丽值 为以下分数之 和 ：
     * 完善 花园数目乘以 full.
     * 剩余 不完善 花园里，花的 最少数目 乘以 partial 。如果没有不完善花园，那么这一部分的值为 0 。
     * 请你返回 Alice 种最多 newFlowers 朵花以后，能得到的 最大 总美丽值。
     *
     * tips:
     * 1 <= flowers.length <= 10^5
     * 1 <= flowers[i], target <= 10^5
     * 1 <= newFlowers <= 10^10
     * 1 <= full, partial <= 10^5
     * @param: flowers
     * @param: newFlowers
     * @param: target
     * @param: full
     * @param: partial
     * @return long
     * @author marks
     * @CreateDate: 2026/08/06 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumBeauty(int[] flowers, long newFlowers, int target, int full, int partial) {
        long result;
        result = method_01(flowers, newFlowers, target, full, partial);
        return result;
    }

    /**
     * @Description:
     * 1. 对 flowers 进行升序排序, 需要通过二分法得到花的最少数目值 [min ~ target - 1], 然后确定完善的花园数量[left, right],
     * 这个可以使用后缀和来处理, 即完善后 i 个花园所需要的最少 newFlowers 数量
     * 2. 如果需要将 [0 ~ j] 这 j 个花园的最小数目给提示至 mid 所需要的最小 newFlowers 数目, 这个可以使用二分法 + 前缀和处理,
     * 首先通过二分法找到下标 j, flowers[j] < mid && flowers[j + 1] >= mid, 然后前缀和 prevSum[j] 为前 j 个花园中花的总数,
     * 如果 newFlowers < prevSum[j] 则不成立, 即无法构成最小数目, 返回 false.
     * 3. newFlowers >= prevSum[j], 此时的最小数目是 mid, 贡献度是 mid * partial, 然后剩余的 newFlowers -= prevSum[j],
     * 然后通过后缀和 + 二分查找的方法, 得到剩余的花的数目能够构成最多 k 个 完善花园, 即 suffixSum[k] <= newFlowers && k > j,
     * j 和 k 不能存在交集, 此时的贡献度是 (n - 1 - k) * full, 总的贡献度 int beatifyScore = mid * partial + (n - 1 - k) * full,
     * 然后取最大值返回即可.
     * AC: 290ms/80.6MB
     * @param: flowers
     * @param: newFlowers
     * @param: target
     * @param: full
     * @param: partial
     * @return long
     * @author marks
     * @CreateDate: 2026/08/06 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] prevSum; // 前缀和数组, prevSum[i] = Sum(flowers[i]),i 属于 [0 ~ i - 1]
    private long[] suffixSum; // 后缀和数组, 将 [i, n - 1] 个花园构成完善花园的最少需要的 newFlowers 数量
    private long method_01(int[] flowers, long newFlowers, int target, int full, int partial) {
        int n = flowers.length;
        prevSum = new long[n + 1];
        // 对 flowers 进行升序排序
        Arrays.sort(flowers);
        for (int i = 1; i <= n; i++) {
            prevSum[i] = prevSum[i - 1] + flowers[i - 1];
        }
        // 构建后缀和
        suffixSum = new long[n + 1];
        // 执行倒序遍历
        for (int i = n - 1; i >= 0; i--) {
            int sub = Math.max(0, target - flowers[i]); // 需要添加最多 sub 朵 newFlowers 可以使得花园 i 变成完善花园
            suffixSum[i] = suffixSum[i + 1] + sub;
        }
        // 通过二分法得到花的最少数目值, 不完善状态
        int left = flowers[0], right = target - 1;
        if (left >= target) {
            // 所有花园都是完善的
            return (long) n * full;
        }

        long ans = 0;
        if (suffixSum[0] <= newFlowers) { // 所有花园都是完善情况下的分数
            ans = (long) n * full;
        }
        // 执行枚举, 因为二分法是在一个有序情况下才能使用, 但是当前分数变化有两个变量, 所以不是有序的, 此时采用枚举的方法
        while (left <= right) {
            long currScore = check(flowers, newFlowers, full, partial, left);
            if (currScore > 0) {
                ans = Math.max(ans, currScore);
                left++;
            } else {
                break;
            }
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 在最小值是 left 情况下, 让剩余的花继续最大化完善花园的数量
     * 2. 每次 check() 方法的时间复杂度是 O(2 * log n)
     * @param: flowers
     * @param: newFlowers
     * @param: full
     * @param: partial
     * @param: low
     * @return long
     * @author marks
     * @CreateDate: 2026/08/06 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long check(int[] flowers, long newFlowers, int full, int partial, int low) {
        // 通过二分查找, 找到 flowers 中第一个大于等于 low 的下标 idx
        int n = flowers.length;
        int left = 0, right = n - 1;
        int idx = n;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            if (flowers[mid] >= low) {
                idx = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // 得到前缀和 [0 ~ idx - 1]
        long pSum = prevSum[idx];
        // 剩余的 newFlowers 数量
        long remainingFlowers = newFlowers - ((long) low * idx - pSum);
        if (remainingFlowers < 0) {
            // newFlowers 数量无法满足将花园最小值补全为 low
            return -1L;
        }
        // 再次执行二分查找, 在后缀和中, 找到 newFlowers 的下标 k, 要求 k >= 1,
        // 至少需要保留一个花园数为 low, 其它为 low 的花园 可以继续添加花至 target
        left = 1;
        right = n - 1;
        int fullIdx = n;
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            // 需要将 [mid ~ n - 1] 的花园变得完善, 需要 suffixSum[mid], 则 [0 ~ mid - 1] 需要最小值 low
            // 由于 mid 与 idx 之间可能存在交集, 取较小的值, 将[0 ~ min - 1] 的花园的提升至 low
            int min = Math.min(mid, idx);
            long sum = ((long) low * min - prevSum[min]);

            if (sum + suffixSum[mid] <= newFlowers) {
                fullIdx = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        // 计算此时的美丽值
        long s1 = (long) partial * low;
        long s2 = (long) full * (n - fullIdx);

        return s1 + s2;
    }

}
