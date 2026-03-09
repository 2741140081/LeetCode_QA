package com.marks.leetcode.DP;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2289 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/9 16:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2289 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 。在一步操作中，移除所有满足 nums[i - 1] > nums[i] 的 nums[i] ，其中 0 < i < nums.length 。
     * 重复执行步骤，直到 nums 变为 非递减 数组，返回所需执行的操作数。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/03/09 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int totalSteps(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [5,3,4,4,7,3,6,11,8,5,11]
     * 输出：3
     * 1. 贪心策略, 对于每个nums[i], 找到满足第一个 nums[j] > nums[i], 删除 i 所需的操作数 i - j,
     * 时间复杂度是O(n^2), 会超时,
     * 2. 需要用单调递减栈来优化, 单调递减栈存储索引, nums[i] < nums[j], 入栈, 并且记录此时的操作数 i - j,
     * 3. nums[i] <= nums[j] 出栈操作, 栈顶元素出栈, 直到栈为空或者栈顶元素大于nums[i], 直接 i - j, 不需要 + 1.
     * 4. 不能单纯的按照距离来计算, [14,13,2,6,13], 如果单纯按照距离算, 结果是 4, 但是 13, 2 是同时第一次操作时被删除的
     * need todo
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/03/09 16:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0; // 默认为0, 不需要进行操作
        // 单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        int n = nums.length;
        int[] dp = new int[n]; // 记录当前元素i, 需要删除的元素个数
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                // 出栈
                stack.pop();
            }
            // 当前元素入栈
            if (!stack.isEmpty()) {
                // 栈不为空, 记录此时的操作数
                dp[stack.peek()]++;
            }
            // 入栈
            stack.push(i);
        }
        return ans;
    }

}
