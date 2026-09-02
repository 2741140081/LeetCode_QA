package com.marks.leetcode.array_medium;

import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCR_057 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/1 9:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCR_057 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和两个整数 k 和 t 。请你判断是否存在 两个不同下标 i 和 j，
     * 使得 abs(nums[i] - nums[j]) <= t ，同时又满足 abs(i - j) <= k 。
     * 如果存在则返回 true，不存在返回 false。
     * @param: nums
     * @param: k
     * @param: t
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/01 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        boolean result;
        result = method_01(nums, k, t);
        return result;
    }

    /**
     * @Description:
     * 1. 使用有序集合 TreeMap 存储元素, 相当于滑动窗口 + 有序集合
     * 2. 对于 nums[i], nums[j] = nums[i] - t, nums[i] + t, 需要从 TreeMap 中找到一个数, 区间是 [nums[i] - t, nums[i] + t],
     * 如果存在, 返回 true, 如果不存在, 则判断窗口大小 i - left + 1 > k, 如果是, 则需要收缩窗口, 即 left++,
     * AC: 54ms/49.1MB
     * @param: nums
     * @param: k
     * @param: t
     * @return boolean
     * @author marks
     * @CreateDate: 2026/09/01 9:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k, int t) {
        TreeMap<Long, Integer> treeMap = new TreeMap<>();
        int left = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // 先移除窗口外的元素
            if (i - left > k) {
                long leftVal = nums[left];
                treeMap.merge(leftVal, -1, Integer::sum);
                if (treeMap.get(leftVal) == 0) {
                    treeMap.remove(leftVal);
                }
                left++;
            }

            // 判断是否存在 nums[i] - t, nums[i] + t
            long num = nums[i];
            long s1 = num - t;
            long s2 = num + t;
            Long k1 = treeMap.ceilingKey(s1);
            if (k1 != null && k1 <= s2) {
                return true;
            }
            treeMap.merge(num, 1, Integer::sum);
        }

        return false;
    }

}
