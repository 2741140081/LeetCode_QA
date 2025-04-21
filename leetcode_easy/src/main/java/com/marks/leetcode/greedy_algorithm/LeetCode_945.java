package com.marks.leetcode.greedy_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 15:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_945 {
    /**
     * @Description:
     * 给你一个整数数组 nums 。每次 move 操作将会选择任意一个满足 0 <= i < nums.length 的下标 i，并将 nums[i] 递增 1。
     *
     * 返回使 nums 中的每个值都变成唯一的所需要的最少操作次数。
     *
     * 生成的测试用例保证答案在 32 位整数范围内。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minIncrementForUnique(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 只是cv了官解, 还需要自己去理解
     * need todo
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        Arrays.sort(nums);
        int ans = 0, taken = 0;

        for (int i = 1; i < nums.length; ++i) {
            if (nums[i - 1] == nums[i]) {
                taken++;
                ans -= nums[i];
            } else {
                int give = Math.min(taken, nums[i] - nums[i - 1] - 1);
                ans += give * (give + 1) / 2 + give * nums[i - 1];
                taken -= give;
            }
        }

        if (nums.length > 0) {
            ans += taken * (taken + 1) / 2 + taken * nums[nums.length - 1];
        }

        return ans;
    }
}
