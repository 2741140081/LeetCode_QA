package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2762 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums 。nums 的一个子数组如果满足以下条件，那么它是 不间断 的：
     *
     * i，i + 1 ，...，j  表示子数组中的下标。对于所有满足 i <= i1, i2 <= j 的下标对，都有 0 <= |nums[i1] - nums[i2]| <= 2 。
     * 请你返回 不间断 子数组的总数目。
     *
     * 子数组是一个数组中一段连续 非空 的元素序列。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/11/1 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long continuousSubarrays(int[] nums) {
        long result = 0;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description: [
     * AC:27ms/57.24MB
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/11/1 10:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] nums) {
        int n = nums.length;
        int left = 0;
        long ans = 0;
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int right = 0; right < n; right++) {
            treeMap.merge(nums[right], 1, Integer::sum);
            while (treeMap.lastKey() - treeMap.firstKey() > 2 && left <= right) {
                int temp = nums[left];
                if (treeMap.get(temp) == 1) {
                    treeMap.remove(temp);
                }else {
                    treeMap.merge(temp, -1, Integer::sum);
                }
                left++;
            }
            if (treeMap.lastKey() - treeMap.firstKey() <= 2) {
                ans += (right - left + 1);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [5,4,2,4]
     * 输出：8
     *
     * 没有很好利用窗口滑动的特性, 几乎就没有用, minValue和maxValue在窗口不断滑动过程中不断地变化,
     * 而method_01只是暴力求的minValue和maxValue, 因此时间复杂度较高
     * AC:108ms/56.55MB
     *
     * 查看题解, 使用TreeMap, 由于TreeMap自动排序, 所以只需要treeMap的最后一个元素减去第一个元素, 判断是否符合要求,
     * 无需和method_01一样对HashMap进行遍历
     * ]
     * @param nums
     * @return long
     * @author marks
     * @CreateDate: 2024/11/1 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>(); // 存储窗口中元素值(K)和数量(V)
        int left = 0;
        long ans = 0;

        for (int right = 0; right < n; right++) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
            while (!checkIsContinuous(map) && left <= right) {
                if (map.get(nums[left]) == 1) {
                    map.remove(nums[left]);
                }else {
                    map.put(nums[left], map.get(nums[left]) - 1);
                }
                left++;
            }
            if (checkIsContinuous(map)) {
                ans += (right - left + 1);
            }
        }
        return ans;
    }

    private boolean checkIsContinuous(HashMap<Integer, Integer> map) {
        int minValue = Integer.MAX_VALUE;
        int maxValue = Integer.MIN_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            minValue = Math.min(minValue, entry.getKey());
            maxValue = Math.max(maxValue, entry.getKey());
            if (Math.abs(maxValue - minValue) > 2) {
                return false;
            }
        }
        return true;
    }
}
