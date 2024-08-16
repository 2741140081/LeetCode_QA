package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称: 将三个组排序 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/16 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2826 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 。nums 的每个元素是 1，2 或 3。在每次操作中，你可以删除 nums 中的一个元素。
     * 返回使 nums 成为 非递减 顺序所需操作数的 最小值。
     * tips:
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 3
     * 进阶:你可以使用 O(n) 时间复杂度以内的算法解决吗？
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/16 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumOperations(List<Integer> nums) {
        int result = 0;
//        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    private int method_02(List<Integer> nums) {
        int n = nums.size();
        int[] dp = new int[4];
        for (Integer num : nums) {
            dp[num]++;
            dp[2] = Math.max(dp[1], dp[2]);
            dp[3] = Math.max(dp[2], dp[3]);
        }
        return n - Arrays.stream(dp).max().getAsInt();
    }

    /**
     * @Description: [
     * E1:输入：nums = [2,1,3,2,1]
     * 输出：3
     * 解释：
     * 其中一个最优方案是删除 nums[0]，nums[2] 和 nums[3]。
     * 删除后的结果nums = {1, 1}
     * 1. 删除后的数组是一个非递减的数组nums = [a1, a2, a_n]
     * 2. 易得a_n >= a1
     * 对于 nums[i] >= a_n
     * dp[i] = dp[i - 1]
     * nums[i] < a_n
     * dp[i] = Math.min()
     * 3. 我需要记录1, 2, 3在新数组中的个数是多少
     * dp = [0, 1, 0];
     * int maxValue = 0;
     * for(0~n)
     * 对于i
     * a.nums[i] == 1  {
     *     if nums[i] >= maxValue {
     *         dp[0]++
     *     }else{
     *         if dp[1] + dp[2] <= 1
     *         dp[0]++
     *         dp[1] = 0
     *         dp[2] = 0
     *     }
     * }
     *
     * b.nums[i] == 2 {
     *
     *
     *     if(nums[i] >= maxvalue){
     *         dp[i]++
     *     }else {
     *         if dp[2] <= 1 {
     *             dp[1]++
     *             dp[2] = 0;
     *         }
     *     }
     * }
     *
     * c.nums[i] == 3 {
     *      maxValue = nums[i]
     *      dp[2]++
     * }
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/16 16:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<Integer> nums) {
        int n = nums.size();
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums.get(i) >= nums.get(j)) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return n - Arrays.stream(dp).max().getAsInt();
    }
}
