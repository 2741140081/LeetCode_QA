package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_456 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/11 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_456 {

    /**
     * @Description:
     * 给你一个整数数组 nums ，数组中共有 n 个整数。132 模式的子序列 由三个整数
     * nums[i]、nums[j] 和 nums[k] 组成，并同时满足：i < j < k 和 nums[i] < nums[k] < nums[j] 。
     * 如果 nums 中存在 132 模式的子序列 ，返回 true ；否则，返回 false 。
     *
     * tips:
     * n == nums.length
     * 1 <= n <= 2 * 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/11 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean find132pattern(int[] nums) {
        boolean result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 单调栈 + 枚举 1
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/11 16:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        // 创建单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(nums[n - 1]);
        int maxK = Integer.MIN_VALUE;
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] < maxK) {
                return true;
            }
            while (!stack.isEmpty() && stack.peek() < nums[i]) {
                // 最大的 2
                maxK = stack.pop();
            }
            if (nums[i] > maxK) {
                stack.push(nums[i]);
            }
        }
        return false;
    }

    /**
     * @Description:
     * 1. 没思路, 看看题解
     * 2. 枚举3, 使用有序集合
     * AC: 237ms/108.19MB
     * @param: nums
     * @return boolean
     * @author marks
     * @CreateDate: 2026/05/11 15:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        // add 2 ~ n to map
        for (int i = 2; i < n; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        // 枚举 中间的 3
        int min = nums[0];
        for (int j = 1; j < n - 1; j++) {
            int num = nums[j];
            if (num > min) {
                // 判断 2 是否存在, 找到大于 min 的最小值, 即 2 的最小值
                Integer k = map.ceilingKey(min + 1);
                if (k != null && k < num) {
                    return true;
                }
            }
            // 更新 min, 并且更新 map
            min = Math.min(min, nums[j]);
            map.put(nums[j + 1], map.getOrDefault(nums[j + 1], 0) - 1);
            if (map.get(nums[j + 1]) == 0) {
                map.remove(nums[j + 1]);
            }
        }
        return false;
    }

}
