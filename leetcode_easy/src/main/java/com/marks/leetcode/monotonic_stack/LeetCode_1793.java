package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/29 14:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1793 {
    /**
     * @Description: [
     * 给你一个整数数组 nums （下标从 0 开始）和一个整数 k 。
     *
     * 一个子数组 (i, j) 的 分数 定义为 min(nums[i], nums[i+1], ..., nums[j]) * (j - i + 1) 。
     * 一个 好 子数组的两个端点下标需要满足 i <= k <= j 。
     *
     * 请你返回 好 子数组的最大可能 分数 。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 2 * 10^4
     * 0 <= k < nums.length
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 15:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumScore(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        result = method_03(nums, k);
        return result;
    }

    /**
     * @Description: [
     * 优化栈, 只需要一次遍历即可求出left[] 和  right[]
     * AC:32ms/58.19MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/4 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        Arrays.fill(right, n);
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                right[stack.peek()] = i;
                stack.poll();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        int ans = nums[k];
        for (int i = 0; i < n; i++) {
            // i <= k <= j, 但是left[] 和right[] 存储的是小于ming(nums[i] ~ nums[j]), 所以需要大于left[i], 小于right[i]
            if (k > left[i] && k < right[i]) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * nums[i]);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * @see LeetCode_84#method_02(int[])
     * 本地与之方法一致, 同样是求
     * AC:48ms/56.75MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/12/2 14:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
//        Arrays.fill(right, n);
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.poll();
            }
            left[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }

        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[i]) {
                stack.poll();
            }
            right[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }

        int ans = nums[k];
        for (int i = 0; i < n; i++) {
            // i <= k <= j, 但是left[] 和right[] 存储的是小于ming(nums[i] ~ nums[j]), 所以需要大于left[i], 小于right[i]
            if (k > left[i] && k < right[i]) {
                ans = Math.max(ans, (right[i] - left[i] - 1) * nums[i]);
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums = [1,4,3,7,4,5], k = 3
     * 输出：15
     * 解释：最优子数组的左右端点下标是 (1, 5) ，分数为 min(4,3,7,4,5) * (5-1+1) = 3 * 5 = 15 。
     * nums[3] = 7
     * [1, 3, 7]
     * [7, 4]
     * 感觉不能动态规划,
     * 双指针
     * left = k - 1, right = k + 1;
     * AC:4ms/56.38MB
     * ]
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/11/29 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int left = k - 1, right = k + 1;
        int ans = 0;
        for (int i = nums[k];; --i) {
            while (left >= 0 && nums[left] >= i) {
                --left;
            }
            while (right < n && nums[right] >= i) {
                ++right;
            }
            ans = Math.max(ans, (right - left - 1) * i);
            if (left == -1 && right == n) {
                break;
            }
        }
        return ans;
    }
}
