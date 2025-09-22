package com.marks.leetcode.bitwise_operation;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/19 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2527 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。
     * 三个下标 i ，j 和 k 的 有效值 定义为 ((nums[i] | nums[j]) & nums[k]) 。
     * 一个数组的 异或美丽值 是数组中所有满足 0 <= i, j, k < n  的三元组 (i, j, k) 的 有效值 的异或结果。
     * 请你返回 nums 的异或美丽值。
     *
     * 注意：
     *
     * val1 | val2 是 val1 和 val2 的按位或。
     * val1 & val2 是 val1 和 val2 的按位与。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/9/19 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int xorBeauty(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. t1 = (nums[i1] | nums[j1]) & nums[k1], t2 = (nums[i2] | nums[j2]) & nums[k2]
     * 2. t1 ^ t2 => (nums[i1] | nums[j1]) & nums[k1]) ^ ((nums[i2] | nums[j2]) & nums[k2])
     * 3. 直接暴力计算吧
     * need todo
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/9/19 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0;
        for (int x : nums) ans ^= x;
        return ans;
    }

}
