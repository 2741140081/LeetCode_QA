package com.marks.leetcode.double_pointer.single_sequence;

import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/4 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2824 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始长度为 n 的整数数组 nums 和一个整数 target ，
     * 请你返回满足 0 <= i < j < n 且 nums[i] + nums[j] < target 的下标对 (i, j) 的数目。
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/4 16:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPairs(List<Integer> nums, int target) {
        int result;
        result = method_01(nums, target);
        return result;
    }

    /**
     * @Description: [
     *
     * AC:3ms/41.30MB
     * ]
     * @param nums
     * @param target
     * @return int
     * @author marks
     * @CreateDate: 2024/11/4 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> nums, int target) {
        int n = nums.size();
        Collections.sort(nums);
        int ans = 0;

        for (int left = 0; left < n - 1; left++) {
            int temp = target - nums.get(left);
            int right = left + 1;
            while (right < n && temp > nums.get(right)) {
                ans++;
                right++;
                if (right < n && nums.get(right) >= temp) {
                    break;
                }
            }
        }
        return ans;
    }
}
