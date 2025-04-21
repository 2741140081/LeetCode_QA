package com.marks.leetcode.data_structure.common_enum_technique;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 17:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_219 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，满足 nums[i] == nums[j] 且 abs(i - j) <= k 。
     * 如果存在，返回 true ；否则，返回 false 。
     * ]
     * @param nums 
     * @param k 
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/7 17:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        boolean result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description: left 和 right 组成大小为2k的滑动窗口, map中存储元素的值和数量, 如果数量 > 1, return true;
     * AC: 28ms/56.18MB
     * @param nums
     * @param k
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/7 17:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] nums, int k) {
        int right = 0;
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if(i - right > k) {
                map.remove(nums[right++]);
            }
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            if (map.get(nums[i]) >= 2) {
                return true;
            }
        }
        return false;
    }
}
