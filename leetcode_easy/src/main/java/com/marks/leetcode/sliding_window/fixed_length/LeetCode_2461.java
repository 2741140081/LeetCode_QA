package com.marks.leetcode.sliding_window.fixed_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 14:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2461 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k 。请你从 nums 中满足下述条件的全部子数组中找出最大子数组和：
     *
     * 子数组的长度是 k，且
     * 子数组中的所有元素 各不相同 。
     * 返回满足题面要求的最大子数组和。如果不存在子数组满足这些条件，返回 0 。
     *
     * 子数组 是数组中一段连续非空的元素序列。
     *
     * tips:
     * 1 <= k <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/12 14:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumSubarraySum(int[] nums, int k) {
        long result = 0;
        result = method_01(nums, k);
        return result;
    }
    /**
     * @Description: [
     * AC:45ms/57.30MB
     * ]
     * @param nums
     * @param k
     * @return long
     * @author marks
     * @CreateDate: 2024/10/12 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums, int k) {
        int n = nums.length;
        long ans = 0, sum = 0;
        // 存储长度为k窗口中的元素, 如果map.size == k, 即没有重复元素
        HashMap<Integer, Integer> map = new HashMap<>();
        // 初始化一个长度为k的窗口
        for (int i = 0; i < k; i++) {
            int num = nums[i];
            sum += num;
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        ans = map.size() == k ? sum : 0;

        for (int i = k; i < n; i++) {
            int currNum = nums[i];
            int preNum = nums[i - k];
            sum = sum + currNum - preNum;
            map.put(currNum, map.getOrDefault(currNum, 0) + 1);
            if (map.get(preNum) == 1) {
                map.remove(preNum);
            }else {
                map.put(preNum, map.get(preNum) - 1);
            }
            if (map.size() == k) {
                //当map.size() = k时, 此时窗口无重复元素
                ans = Math.max(ans, sum);
            }
        }
        return ans;
    }
}
