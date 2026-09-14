package com.marks.leetcode.array_medium;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1865. 找出和为指定值的下标对 </p>
 * <p>描述:
 * 给你两个整数数组 nums1 和 nums2 ，请你实现一个支持下述两类查询的数据结构：
 * 累加 ，将一个正整数加到 nums2 中指定下标对应元素上。
 * 计数 ，统计满足 nums1[i] + nums2[j] 等于指定值的下标对 (i, j) 数目（0 <= i < nums1.length 且 0 <= j < nums2.length）。
 * 实现 FindSumPairs 类：
 * FindSumPairs(int[] nums1, int[] nums2) 使用整数数组 nums1 和 nums2 初始化 FindSumPairs 对象。
 * void add(int index, int val) 将 val 加到 nums2[index] 上，即，执行 nums2[index] += val 。
 * int count(int tot) 返回满足 nums1[i] + nums2[j] == tot 的下标对 (i, j) 数目。
 * </p>
 *
 * tips:
 * 1 <= nums1.length <= 1000
 * 1 <= nums2.length <= 10^5
 * 1 <= nums1[i] <= 10^9
 * 1 <= nums2[i] <= 10^5
 * 0 <= index < nums2.length
 * 1 <= val <= 10^5
 * 1 <= tot <= 10^9
 * 最多调用 add 和 count 函数各 1000 次
 * @author marks
 * @version v1.0
 * @date 2026/9/10 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class FindSumPairs {
    private Map<Integer, Integer> map1;
    private Map<Integer, Integer> map2;
    private int[] nums;
    /**
     * @Description:
     * 1. 使用 Map 分别存储 nums1 和 nums2 中的元素及其出现的次数
     * AC: 187ms/107.06MB
     * @param: nums1
     * @param: nums2
     * @return
     * @author marks
     * @CreateDate: 2026/09/10 17:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public FindSumPairs(int[] nums1, int[] nums2) {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        nums = nums2;
        for (int num : nums1) {
            map1.merge(num, 1, Integer::sum);
        }
        for (int num : nums2) {
            map2.merge(num, 1, Integer::sum);
        }
    }

    public void add(int index, int val) {
        // 获取当前 nums2[index] 的值
        int curr = nums[index];
        // 更新map2
        map2.merge(curr, -1, Integer::sum);
        if (map2.get(curr) == 0) {
            map2.remove(curr);
        }
        nums[index] += val;
        map2.merge(nums[index], 1, Integer::sum);
    }

    public int count(int tot) {
        int ans = 0;
        // 遍历 map1
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            int num1 = entry.getKey();
            int count1 = entry.getValue();
            // 计算 nums2 中需要的值
            int need = tot - num1;
            // 在 map2 中获取 need 的出现次数
            int count2 = map2.getOrDefault(need, 0);
            // 累加结果
            ans += count1 * count2;
        }
        return ans;
    }

}
