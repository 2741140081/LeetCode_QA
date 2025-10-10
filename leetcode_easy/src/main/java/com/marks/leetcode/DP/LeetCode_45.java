package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_45 {

    
    /**
     * @Description:
     * 给定一个长度为 n 的 0 索引整数数组 nums。初始位置在下标 0。
     *
     * 每个元素 nums[i] 表示从索引 i 向后跳转的最大长度。换句话说，如果你在索引 i 处，你可以跳转到任意 (i + j) 处：
     *
     * 0 <= j <= nums[i] 且
     * i + j < n
     * 返回到达 n - 1 的最小跳跃次数。测试用例保证可以到达 n - 1。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/10/10 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int jump(int[] nums) {
        int result;
        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 由于method_01 写的不够简便, 查看官方题解后
     * 1. 差不多想明白了, 即在前一次的跳跃过程中, 找到下一次能到达的最远距离
     * 2. 例如：nums = [2,3,1,1,4], 第一次跳跃可以到达 1 和 2 处, 找到1 + nums[1] 和 2 + nums[2] 的maxPosition
     * 3. 重复上述过程, 在遍历数组时，我们不访问最后一个元素，这是因为在访问最后一个元素之前，我们的边界一定大于等于最后一个位置，否则就无法跳到最后一个位置了。
     * 如果访问最后一个元素，在边界正好为最后一个位置的情况下，我们会增加一次「不必要的跳跃次数」，因此我们不必访问最后一个元素。
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2025/10/10 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        int length = nums.length;
        int end = 0;
        int maxPosition = 0;
        int steps = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition, i + nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }


    /**
     * @Description:
     * E1:
     * 输入: nums = [2,3,1,1,4]
     * 输出: 2
     * 这题可以用dp来解决
     * 1. 创建一个dp数组，dp[i]表示到达i位置的最小跳跃次数
     * 2. 动态规划的方程是什么?
     * 3. 从0位置开始，遍历nums数组, dp[0] = 0, dp[1] = 1, 初始化整个dp数组为Integer.MAX_VALUE, 并且dp[0] = 0,
     * 4. 遍历 nums 数组, nums[0] = 2, dp[i + 1] = Math.min(dp[i + 1], dp[i] + 1), dp[i + 2] = Math.min(dp[i + 2], dp[i] + 1)
     *
     * AC: 37ms/44.72MB
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2025/10/10 15:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= i + nums[i] && j < n; j++) {
                dp[j] = Math.min(dp[j], dp[i] + 1);
            }
        }
        return dp[n - 1];
    }

}
