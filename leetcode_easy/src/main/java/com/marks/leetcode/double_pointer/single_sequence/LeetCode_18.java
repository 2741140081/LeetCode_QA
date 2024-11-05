package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/5 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_18 {
    /**
     * @Description: [
     *
     * tips:
     * 1 <= nums.length <= 200
     * -10^9 <= nums[i] <= 10^9
     * -10^9 <= target <= 10^9
     * ]
     * @param nums
     * @param target
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2024/11/5 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result;
        result = method_01(nums, target);
        return result;
    }

    private List<List<Integer>> method_01(int[] nums, int target) {
        int n = nums.length;
        Arrays.sort(nums);
        return null;
    }
}
