package com.marks.leetcode.data_structure.prefix_sum;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3026 {
    /**
     * @Description:
     * 给你一个长度为 n 的数组 nums 和一个 正 整数 k 。
     *
     * 如果 nums 的一个子数组中，第一个元素和最后一个元素 差的绝对值恰好 为 k ，我们称这个子数组为 好 的。
     * 换句话说，如果子数组 nums[i..j] 满足 |nums[i] - nums[j]| == k ，那么它是一个好子数组。
     *
     * 请你返回 nums 中 好 子数组的 最大 和，如果没有好子数组，返回 0 。
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/1/14 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumSubarraySum(int[] nums, int k) {
        long result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 分成多种情况讨论
     * Math.abs(nums[i] - nums[j]) = k
     * 1. nums[i] > nums[j] => nums[i] = nums[j] + k
     * 2. nums[i] < nums[j] => nums[i] = nums[j] - k
     * E1: nums = [-1,3,2,4,5], k = 3
     * 假设 nums[j] = -1,
     *
     * AC:72ms/56.04MB
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2025/1/14 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        Map<Integer, Long> map = new HashMap<>();
        long ans = Long.MIN_VALUE;
        long sum = 0L;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int key_one = nums[i] + k;
            int key_two = nums[i] - k;
            if (map.containsKey(key_one)) {
                ans = Math.max(ans, (sum - map.get(key_one) + key_one));
            }
            if (map.containsKey(key_two)) {
                ans = Math.max(ans, (sum - map.get(key_two) + key_two));
            }
            long temp = sum;
            if (map.containsKey(nums[i])) {
                temp = Math.min(temp, map.get(nums[i]));
            }
            map.put(nums[i], temp);
        }
        return ans == Long.MIN_VALUE ? 0L : ans;
    }
}
