package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1856 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/14 11:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1856 {

    /**
     * @Description:
     * 一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
     * 比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
     * 给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。由于答案可能很大，请你返回答案对  109 + 7 取余 的结果。
     * 请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 64 位有符号整数 保存。
     * 子数组 定义为一个数组的 连续 部分。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^7
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/14 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSumMinProduct(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 需要的是一个连续的子数组, 假设有一个子数组[i, j]
     * 2. 需要找到最大值,假设 子数组[i, j] 中 nums[j] 是子数组的最小值 sum 是子数组[i, j] 的和, 那么 dp[j] = nums[j] * sum
     * 3. 处理下一个 j + 1 的下标节点, nums[j + 1], 需要考虑 nums[j + 1] 与 nums[j] 的关系
     * 3.1 nums[j + 1] >= nums[j], dp[j + 1] = dp[j] + nums[j + 1] * nums[j]; 即最小值没有发生改变, 但是 sum 值增加了 nums[j + 1], 所以对于总的共享了 nums[j] * nums[j + 1];
     * 3.2 nums[j + 1] < nums[j], 则最小值发生了改变, sum = dp[j] / nums[j] + nums[j + 1], dp[j + 1] = sum * nums[j + 1]; 并且 dp[j + 1] = Math.max(dp[j], dp[j + 1]);
     * 4. 即使用动态规划, 假设使用一个单调递增的栈, 递增栈感觉有问题, 用递减栈试试看, 假设是一个单调递减栈, 那么对于当前 节点 nums[i], 与栈顶元素 int top = stack.peek(), 的关系进行讨论
     * 5. 不对, 子数组[i, j], 那么sum 值可以通过 前缀和 O(1) 计算得到, 但是如何找到 [i, j] 子数组的最小值? 用线段树? 找区间的最小值?
     * 6. 还是不对, 如果使用线段树 或者稀疏表, 还是需要O(n^2) 来遍历 i, j 的组合, 这样必定超时? 那么我们考虑换思路, 即假设 nums[i] 是区间[j, k] 的最小值,
     * Math.max(nums[k + 1], nums[j - 1]) < nums[i] && nums[i] >= nums[j ~ k], 这不就构成一个单调递增的栈? 但是栈中存放的是下标, 而不是实际值.
     * 假设单调递增栈为 stack = new ArrayDeque<>(); 假设栈有元素[1,3,4,7] 这4个下标, 当前下标位于 i = 8 处, 需要判断 nums[i] 与 nums[stack.peek()] 的大小关系
     * 7.1 nums[i] < nums[stack.peek()], 栈顶元素出栈, 栈中元素为[1,3,4], 下标 7 出栈, 此时栈顶元素为 4, 当前 下标 i = 8, 假设我们还有一个前缀和数组 prevSum[], 那么[5~7] 的和为 int sum = prevSum[8] - prevSum[4 + 1];
     * 并且 [5 ~ 7] 区间的最小值是 nums[7], 可以得到 下标7作为最小值可以得到的最小乘积为 nums[7] * sum, 最后将当前 i 入栈
     * 7.2 nums[i] == nums[stack.peek()], 那么栈顶的元素就没有必要, 栈顶元素出栈, 然后 i 入栈.
     * 7.3 nums[i] > nums[stack.peek()], 这种直接入栈 i 即可.
     * 7.4 最终我们会得到一个单调递增的栈, 如果栈不为空
     * AC: 29ms/88.93MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/14 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int MOD = (int) 1e9 + 7;
        // 前缀和
        long[] prevSum = new long[n + 1];
        prevSum[0] = 0;
        for (int i = 0; i < n; i++) {
            prevSum[i + 1] = prevSum[i] + nums[i];
        }
        // 单调递增栈
        Deque<Integer> stack = new ArrayDeque<>();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                int top = stack.peek();
                stack.pop();
                int left = stack.isEmpty() ? 0 : (stack.peek() + 1);
                long sum = prevSum[i] - prevSum[left];
                ans = Math.max(ans, nums[top] * sum);
            }
            stack.push(i);
        }
        // 处理栈中剩余的元素
        while (!stack.isEmpty()) {
            int top = stack.pop();
            int left = stack.isEmpty() ? 0 : (stack.peek() + 1);
            long sum = prevSum[n] - prevSum[left];
            ans = Math.max(ans, nums[top] * sum);
        }

        return (int) (ans % MOD);
    }

}
