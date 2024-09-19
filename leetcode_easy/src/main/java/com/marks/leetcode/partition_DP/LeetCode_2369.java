package com.marks.leetcode.partition_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/18 16:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2369 {
    /**
     * @Description: [
     * 给你一个下标从 0 开始的整数数组 nums ，你必须将数组划分为一个或多个 连续 子数组。
     *
     * 如果获得的这些子数组中每个都能满足下述条件 之一 ，则可以称其为数组的一种 有效 划分：
     *
     * 子数组 恰 由 2 个相等元素组成，例如，子数组 [2,2] 。
     * 子数组 恰 由 3 个相等元素组成，例如，子数组 [4,4,4] 。
     * 子数组 恰 由 3 个连续递增元素组成，并且相邻元素之间的差值为 1 。例如，子数组 [3,4,5] ，但是子数组 [1,3,5] 不符合要求。
     * 如果数组 至少 存在一种有效划分，返回 true ，否则，返回 false 。
     *
     * tips:
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * ]
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2024/9/18 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean validPartition(int[] nums) {
        boolean result = false;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * AC:6ms/57.50MB
     * 看到一个有趣的东西 "忒修斯之船"
     * 忒修斯之船（The Ship of Theseus），最为古老的思想实验之一。最早出自普鲁塔克的记载。
     * 它描述的是一艘可以在海上航行几百年的船，归功于不间断的维修和替换部件。只要一块木板腐烂了，它就会被替换掉，以此类推，
     * 直到所有的功能部件都不是最开始的那些了。问题是，最终产生的这艘船是否还是原来的那艘忒修斯之船，还是一艘完全不同的船？
     * 如果不是原来的船，那么在什么时候它不再是原来的船了？
     * ]
     * @param nums
     * @return boolean
     * @author marks
     * @CreateDate: 2024/9/18 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private boolean method_01(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            if (i >= 2) {
                dp[i] = dp[i - 2] && validTwo(nums[i - 2], nums[i - 1]);
            }
            if (i >= 3) {
                dp[i] = dp[i] || (dp[i - 3] && validThree(nums[i - 3], nums[i - 2], nums[i - 1]));
            }
        }
        return dp[n];
    }

    private boolean validTwo(int num1, int num2) {
        return num1 == num2;
    }

    private boolean validThree(int num1, int num2, int num3) {
        return (num1 == num2 && num2 == num3) || (num1 + 1 == num2 && num2 + 1 == num3);
    }


}
