package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/7 10:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2592 {
    
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。你需要将 nums 重新排列成一个新的数组 perm 。
     *
     * 定义 nums 的 伟大值 为满足 0 <= i < nums.length 且 perm[i] > nums[i] 的下标数目。
     *
     * 请你返回重新排列 nums 后的 最大 伟大值。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/4/7 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximizeGreatness(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 田忌赛马: 排序 + 前后指针
     *
     * AC: 10ms/58.26MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/7 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int left = n - 1, right = n - 1, ans = 0;
        while (left >= 0) {
            if (nums[right] > nums[left]) {
                left--;
                right--;
                ans++;
            } else {
                left--;
            }
        }
        return ans;
    }
}
