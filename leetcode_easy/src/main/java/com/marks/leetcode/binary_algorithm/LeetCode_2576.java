package com.marks.leetcode.binary_algorithm;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/25 16:37
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2576 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums 。
     *
     * 一开始，所有下标都没有被标记。你可以执行以下操作任意次：
     *
     * 选择两个 互不相同且未标记 的下标 i 和 j ，满足 2 * nums[i] <= nums[j] ，标记下标 i 和 j 。
     * 请你执行上述操作任意次，返回 nums 中最多可以标记的下标数目。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxNumOfMarkedIndices(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }
    
    /**
     * @Description: [
     * E1:
     * 输入：nums = [3,5,2,4]
     * 输出：2
     * {2, 3, 4, 5}
     * {4, 6, 8, 10}
     *
     * AC:31ms/55.28MB
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/25 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0, r = n / 2;
        while (l < r) {
            int m = l + r + 1 >> 1;
            if (check(nums, m)) {
                l = m;
            } else {
                r = m - 1;
            }
        }
        return l * 2;
    }

    private boolean check(int[] nums, int m) {
        int n = nums.length;
        for (int i = 0; i < m; i++) {
            if (nums[i] * 2 > nums[n - m + i]) {
                return false;
            }
        }
        return true;
    }
}
