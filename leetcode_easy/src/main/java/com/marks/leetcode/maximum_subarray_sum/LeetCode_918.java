package com.marks.leetcode.maximum_subarray_sum;

import java.util.ArrayDeque;
import java.util.Deque;

public class LeetCode_918 {
    /**
     * 给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。
     * 环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。
     * 子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。
     *
     * E1:
     * 输入：nums = [1,-2,3,-2]
     * 输出：3
     * 解释：从子数组 [3] 得到最大和 3
     *
     * E2:
     * 输入：nums = [5,-3,5]
     * 输出：10
     * 解释：从子数组 [5,5] 得到最大和 5 + 5 = 10
     *
     * E3:
     * 输入：nums = [3,-2,2,-3]
     * 输出：3
     * 解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3
     *
     * @param nums
     * @return
     */
    public int maxSubarraySumCircular(int[] nums) {
        int result = 0;

        result = method_01(nums); // 超时GG!!
        result = method_02(nums);
        int result_03 = method_03(nums);
        return result;
    }


    /**
     * 思路: 因为数组是一个环形数组, 所以可以将环形展开成2 * nums[],
     * 然后构建一个end - start = nums.length 的滑动窗口, 在滑动窗口中找到该最大值, 开干！
     *
     * 结论: 超时GG！！！
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        if (nums.length == 1) {
            // 返回nums的非空子数组
            return nums[0];
        }
        // nums.length >= 2
        int[] value = new int[nums.length * 2];

        // 展开环形数组
        for (int i = 0; i < nums.length; i++) {
            value[i] = nums[i];
            value[i + nums.length] = nums[i];
        }
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            max = maxSubArray(value, i, i + nums.length, max);
        }
        return max;
    }

    /**
     * 官方题解 方法1:
     * 0 ~ (i-1) --> 前缀和
     * dp[i~n] --> 保存后缀和
     * 开干
     * @param nums
     * @return
     */
    private int method_02(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];
        leftMax[0] = nums[0];
        int leftSum = nums[0];
        int pre = nums[0];
        int res = nums[0];
        for (int i = 1; i < n; i++) {
            pre = Math.max(pre + nums[i-1], nums[i-1]);
            res = Math.max(pre, res);
            leftSum += nums[i];
            leftMax[i] = Math.max(leftSum, leftMax[i-1]);
        }
        int rightSum = nums[n-1];
        for (int i = n - 1; i > 0; i--) {
            rightSum += nums[i];
            res = Math.max(res, rightSum + leftMax[i - 1]);
        }

        return res;
    }

    /**
     * 官方题解 方法3: 单调队列
     * Eg: nums = {1,-2,3,-2};
     * nums 扩展为2倍 {1,-2,3,-2,1,-2,3,-2}
     * 计算sumNums {1,-1, 2, 0, 1, -1, 2, 0}
     *
     * 使用双端队列ArrayDeque<int[]> 存放, 且该队列为单调队列
     * 存在i 和 j, 满足以下关系 j < i, j >= i - n
     * 求最大和即: Math.max(sum_i - sum_j)
     *
     * @param nums
     * @return
     */
    private int method_03(int[] nums) {
        int n = nums.length;
        int pre = nums[0];
        int res = nums[0];
        Deque<int[]> deque = new ArrayDeque<>();
        deque.offerLast(new int[]{0, pre});
        for (int i = 1; i < n * 2; i++) {
            // 判断队列元素是否超过范围
            while (!deque.isEmpty() && deque.peekFirst()[0] < (i - n)) {
                deque.pollFirst();
            }
            pre += nums[i % n];
            res = Math.max(res, pre - deque.peekFirst()[1]);
            while (!deque.isEmpty() && deque.peekLast()[1] >= pre) {
                deque.pollLast();
            }
            deque.offerLast(new int[]{i, pre});

        }
        return res;
    }

    private int maxSubArray(int[] ints, int start, int end, int max) {
        int[] dp = new int[ints.length];
        dp[start] = ints[start];
        for (int i = start + 1; i < end; i++) {
            dp[i] = Math.max(dp[i-1] + ints[i], ints[i]);
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
