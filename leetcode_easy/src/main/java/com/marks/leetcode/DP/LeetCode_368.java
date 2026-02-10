package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_368 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 15:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_368 {

    /**
     * @Description:
     * 给你一个由 无重复 正整数组成的集合 nums ，请你找出并返回其中最大的整除子集 answer ，子集中每一元素对 (answer[i], answer[j]) 都应当满足：
     * answer[i] % answer[j] == 0 ，或
     * answer[j] % answer[i] == 0
     * 如果存在多个有效解子集，返回其中任何一个均可。
     * tips:
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 2 * 10^9
     * nums 中的所有整数 互不相同
     * @param: nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/02/10 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 好像又没有思路, 直接暴力破解, 好像不能直接暴力, 看看tips
     * 2. 直接看官方题解吧
     * a % b == 0, b % c == 0 => a % c == 0
     * AC: 16ms/44.63MB
     * @param: nums
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2026/02/10 15:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        // 1. 通过dp 找到最大子集数量以及子集的最大值
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int maxSize = 1;
        int maxVal = dp[0];
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            if (dp[i] > maxSize) {
                maxSize = dp[i];
                maxVal = nums[i];
            }
        }
        // 逆向倒推出 最大子集
        List<Integer> ans = new ArrayList<>();
        if (maxSize == 1) {
            ans.add(nums[0]);
            return ans;
        }
        for (int i = n - 1; i >= 0 && maxSize > 0; i--) {
            if (maxVal % nums[i] == 0 && dp[i] == maxSize) {
                ans.add(nums[i]);
                maxVal = nums[i];
                maxSize--;
            }
        }
        return ans;
    }

}
