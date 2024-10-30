package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/30 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2537 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k ，请你返回 nums 中 好 子数组的数目。
     *
     * 一个子数组 arr 如果有 至少 k 对下标 (i, j) 满足 i < j 且 arr[i] == arr[j] ，那么称它是一个 好 子数组。
     *
     * 子数组 是原数组中一段连续 非空 的元素序列。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i], k <= 10^9
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/30 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countGood(int[] nums, int k) {
        long result = 0;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [3,1,4,3,2,2,4], k = 2
     * 输出：4
     * 解释：总共有 4 个不同的好子数组：
     * - [3,1,4,3,2,2] 有 2 对。
     * - [3,1,4,3,2,2,4] 有 3 对。
     * - [1,4,3,2,2,4] 有 2 对。
     * - [4,3,2,2,4] 有 2 对。
     *
     * 方案1: 用HashMap存储窗口中元素值及其数量, 对于这种题型, 习惯于由后往前来统计
     * AC:47ms/55.48MB
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/30 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>(); // 存储窗口中元素值及其数量
        int left = n - 1;
        long ans = 0;
        int count = 0;
        map.put(nums[left], 1);

        for (int right = n - 1; right >= 0; right--) {

            while (count < k && left > 0) {
                left--;
                /*
                对于nums[left], map中存在以下两种情况
                1.map不包含nums[left], 添加进来后, 无法组成arr[i] == arr[j], 此时count 不变
                2.map包含nums[left], 如果多加一个, 对于已存在的cnt, 能对count多加多少? 答案就是 map.get(nums[left])
                 */
                if (map.containsKey(nums[left])) {
                    count += map.get(nums[left]);
                    map.put(nums[left], map.get(nums[left]) + 1);
                }else {
                    // map不包含nums[left]
                    map.put(nums[left], 1);
                }
            }

            if (count >= k) {
                ans = (ans + (left + 1));
            }

            if (map.get(nums[right]) == 1) {
                map.remove(nums[right]);
            }else {
                // 减去一个nums[right], 对于count来说是减少了 map.get(nums[right]) - 1 的数量,
                // 例如, 假设当前有5个nums[right], 减去一个nums[right], 剩余4个nums[right]能组成6对, 即总数由10 --> 6, 减少了5 - 1
                count -= (map.get(nums[right]) - 1);
                map.put(nums[right], map.get(nums[right]) - 1);
            }

        }
        return ans;
    }
}
