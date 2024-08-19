package com.marks.leetcode.classic_linear_DP.LIS;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/16 17:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1671 {
    /**
     * @Description: [
     * 我们定义 arr 是 山形数组 当且仅当它满足：
     *
     * arr.length >= 3
     * 存在某个下标 i （从 0 开始） 满足 0 < i < arr.length - 1 且：
     * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
     * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
     * 给你整数数组 nums​ ，请你返回将 nums 变成 山形状数组 的​ 最少 删除次数。
     *
     * tips:
     * 3 <= nums.length <= 1000
     * 1 <= nums[i] <= 10^9
     * 题目保证 nums 删除一些元素后一定能得到山形数组。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/16 17:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumMountainRemovals(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [2,1,1,5,6,2,3,1]
     * 输出：3
     * 解释：一种方法是将下标为 0，1 和 5 的元素删除，剩余元素为 [1,5,6,3,1] ，是山形数组。
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/16 17:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] dp_asc = new int[n];
        int[] dp_desc = new int[n];
        dp_desc[n - 1] = 1;
        for (int i = 0; i < n; i++) {
            // 设置nums[i] 为山顶
            dp_asc[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp_asc[i] = Math.max(dp_asc[i], dp_asc[j] + 1);
                }
            }

        }
        for (int i = n - 1; i >= 0; i--) {
            dp_desc[i] = 1;
            for (int j = n - 1; j > i; j--) {
                if (nums[i] > nums[j]) {
                    dp_desc[i] = Math.max(dp_desc[i], dp_desc[j] + 1);
                }
            }
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            if (dp_asc[i] > 1 && dp_desc[i] > 1) {
                ans = Math.max(ans, dp_asc[i] + dp_desc[i] - 1);
            }
        }

        return n - ans;

    }
}
