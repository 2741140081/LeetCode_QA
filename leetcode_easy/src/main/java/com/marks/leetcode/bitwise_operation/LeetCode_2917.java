package com.marks.leetcode.bitwise_operation;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/11 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2917 {


    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k 。让我们通过扩展标准的按位或来介绍 K-or 操作。
     * 在 K-or 操作中，如果在 nums 中，至少存在 k 个元素的第 i 位值为 1 ，那么 K-or 中的第 i 位的值是 1 。
     *
     * 返回 nums 的 K-or 值。
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findKOr(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }


    /**
     * @Description:
     * 输入：nums = [7,12,9,8,9,15], k = 4
     * 输出：9
     * 1. 找到nums数组的最大值max, max的最大位次是 n,
     * 2. for 0 ~ n, nums[i] & (1 << i) == 1, int count, count ++, if count >= k, char[i] = 1;
     * 0100 & 0111
     *
     * AC: 3ms/42.42MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/9/11 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int count = 0;
            for (int num : nums) {
                if ((num & (1 << i)) != 0) {
                    count++;
                }
            }
            if (count >= k) {
                ans |= (1 << i);
            }
        }
        return ans;
    }

}
