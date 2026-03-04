package com.marks.leetcode.DP;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1696 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/27 15:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1696 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
     * 一开始你在下标 0 处。每一步，你最多可以往前跳 k 步，但你不能跳出数组的边界。
     * 也就是说，你可以从下标 i 跳到 [i + 1， min(n - 1, i + k)] 包含 两个端点的任意位置。
     * 你的目标是到达数组最后一个位置（下标为 n - 1 ），你的 得分 为经过的所有数字之和。
     * 请你返回你能得到的 最大得分 。
     * tips:
     * 1 <= nums.length, k <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxResult(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [1,-1,-2,4,-7,3], k = 2
     * 输出：7
     * 1. 最简单的办法是暴力求解 dp[i] = Math.max(dp[j]) + nums[i]; j => {i - k, i - 1}, 时间复杂度是O(n^2), 大概率超时, 舍弃
     * 2. 需要想一个办法, 类似于滑动窗口, 窗口大小为k, 不断移动过程中, 删除和添加两端的元素, 并且更新整个窗口中的最大值
     * 直接查看官方题解, 使用双端队列, 需要再次理解, need todo
     * AC: 32ms/79.41MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/02/27 15:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        // 创建队列
        Deque<Integer> deque = new ArrayDeque<Integer>();
        deque.offerLast(0);
        for (int i = 1; i < n; i++) {
            // 弹出超过k范围的元素
            while (!deque.isEmpty() && deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            // 更新dp[i]
            dp[i] = nums[i] + dp[deque.peekFirst()];
            // 弹出小于dp[i]的元素
            while (!deque.isEmpty() && dp[i] >= dp[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        return dp[n - 1];
    }

}
