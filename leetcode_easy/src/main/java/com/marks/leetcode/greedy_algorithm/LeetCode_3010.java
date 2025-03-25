package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 18:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3010 {
    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 。
     *
     * 一个数组的 代价 是它的 第一个 元素。比方说，[1,2,3] 的代价是 1 ，[3,4,1] 的代价是 3 。
     *
     * 你需要将 nums 分成 3 个 连续且没有交集 的子数组。
     *
     * 请你返回这些子数组的 最小 代价 总和 。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 18:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumCost(int[] nums) {
        int result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * AC: 2ms/43.21MB
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 18:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = nums[0];
        nums[0] = 51; //
        Arrays.sort(nums);
        ans += nums[0];
        ans += nums[1];
        return ans;
    }
}
