package com.marks.leetcode.daily_question;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/11 17:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2012 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。对于每个下标 i（1 <= i <= nums.length - 2），nums[i] 的 美丽值 等于：
     *
     * 2，对于所有 0 <= j < i 且 i < k <= nums.length - 1 ，满足 nums[j] < nums[i] < nums[k]
     * 1，如果满足 nums[i - 1] < nums[i] < nums[i + 1] ，且不满足前面的条件
     * 0，如果上述条件全部不满足
     * 返回符合 1 <= i <= nums.length - 2 的所有 nums[i] 的 美丽值的总和 。
     *
     * tips:
     * 3 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/3/11 17:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int sumOfBeauties(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 如果要满足条件1 [0, j] < i < [k, n - 1]
     * 不可能整体遍历, 那就是 O(n ^ 2)的时间复杂度
     * 主要是关注与条件1
     * 可以做一个预处理 preMax[] preMin[]
     * 即 对于i, 如果preMax[0, j] < nums[i] 满足条件, 并且 nums[i] < min[k, n - 1]
     *
     * AC: 7ms/55.75MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/11 17:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] preMax = new int[n];
        int[] preMin = new int[n];
        preMax[0] = nums[0];
        preMin[n - 1] = nums[n - 1];
        for (int i = 1; i < n; i++) {
            preMax[i] = Math.max(nums[i], preMax[i - 1]);
        }
        for (int i = n - 2; i >= 0; i--) {
            preMin[i] = Math.min(nums[i], preMin[i + 1]);
        }
        int ans = 0;
        for (int i = 1; i <= n - 2; i++) {
            int num = nums[i];
            if (num > preMax[i - 1] && num < preMin[i + 1]) {
                ans += 2;
            } else if (num > nums[i - 1] && num < nums[i + 1]) {
                ans += 1;
            }
        }
        return ans;
    }
}
