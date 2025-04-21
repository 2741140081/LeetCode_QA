package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/2 14:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2333 {
    /**
     * @Description:
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，长度为 n 。
     * 数组 nums1 和 nums2 的 差值平方和 定义为所有满足 0 <= i < n 的 (nums1[i] - nums2[i])^2 之和。
     *
     * 同时给你两个正整数 k1 和 k2 。你可以将 nums1 中的任意元素 +1 或者 -1 至多 k1 次。类似的，你可以将 nums2 中的任意元素 +1 或者 -1 至多 k2 次。
     *
     * 请你返回修改数组 nums1 至多 k1 次且修改数组 nums2 至多 k2 次后的最小 差值平方和 。
     *
     * 注意：你可以将数组中的元素变成 负 整数。
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 0 <= nums1[i], nums2[i] <= 10^5
     * 0 <= k1, k2 <= 10^9
     * @param nums1
     * @param nums2
     * @param k1
     * @param k2
     * @return long
     * @author marks
     * @CreateDate: 2025/4/2 14:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long result;
        result = method_01(nums1, nums2, k1, k2);
        return result;
    }

    /**
     * @Description:
     * 假设 差值 p = Math.abs(nums1[i] - nums2[i]), 差值平方 即为 p^2
     * 1. nums1[i] > nums2[i], 需要将 (nums1[i] - k1) <= (nums2[i] + k2)
     * Math.max((num1[i] - k1) - (nums[2] + k2), 0)
     * 2. nums1[i] < nums2[i], 需要将 (nums1[i] + k1) >= (nums2[i] - k2)
     * Math.max((nums2[i] - k2) - (nums1[i] + k1), 0)
     *
     * 以上理解有问题, 即对整个nums1[] 只能修改最多 k1 次, 对nums2[] 只能修改最多 k2 次
     * k = 5, k = 5
     * [2, 5, 9]
     * [2, 4, 0] = 4 + 16
     *
     * [2, 2, 2] = 4 + 4 + 4
     *
     * [2, 5, 5] => k =
     *
     * [2, 5, 7] =
     * [2, 4, 8]
     *
     * nums1 = [1,4,10,12], nums2 = [5,8,6,9], k1 = 1, k2 = 1
     * pre = [4, 4, 4, 3] = 9 * 3 + 16 = 27 + 16 = 43
     *
     * 另外我认为 k1 和 k2 是等价的, 也就是 kSum = k1 + k2;
     *
     * AC: 86ms/61.96MB
     * @param nums1
     * @param nums2
     * @param k1
     * @param k2
     * @return long
     * @author marks
     * @CreateDate: 2025/4/2 14:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums1, int[] nums2, int k1, int k2) {
        long ans = 0;
        int n = nums1.length;
        long sum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int pre = Math.abs(nums1[i] - nums2[i]);
            sum += pre;
            map.merge(pre, 1, Integer::sum);
        }
        int k = k1 + k2;
        if (k >= sum) {
            return ans;
        }

        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            queue.add(new int[]{entry.getKey(), entry.getValue()});
        }

        while (k > 0 && !queue.isEmpty()) {
            int[] poll = queue.poll();
            int value = poll[0], count = poll[1];
            if (k >= count) {
                if (!queue.isEmpty() && value == queue.peek()[0] + 1) {
                    queue.peek()[1] += count;
                } else {
                    queue.add(new int[]{value - 1, count});
                }
                k -= count;
            } else {
                int sub = count - k;
                queue.add(new int[]{value - 1, k});
                queue.add(new int[]{value, sub}); // 这些无法减1
                break;
            }
        }
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            long value = poll[0], count = poll[1];
            ans += (value * value) * count;
        }
        return ans;
    }
}
