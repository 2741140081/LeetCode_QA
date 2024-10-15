package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/15 9:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1658 {
    /**
     * @Description: [
     * 给你一个整数数组 nums 和一个整数 x 。每一次操作时，你应当移除数组 nums 最左边或最右边的元素，然后从 x 中减去该元素的值。
     * 请注意，需要 修改 数组以供接下来的操作使用。
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^4
     * 1 <= x <= 10^9
     * 如果可以将 x 恰好 减到 0 ，返回 最小操作数 ；否则，返回 -1 。
     * ]
     * @param nums
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/10/15 9:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums, int x) {
        int result = 0;
        result = method_01(nums, x);
        result = method_02(nums, x);
        return result;
    }
    /**
     * @Description: [
     * AC:10ms/62.60MB
     * ]
     * @param nums
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/10/15 16:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int x) {
        int n = nums.length;
        int sum = Arrays.stream(nums).sum();
        // 判断特殊情况, 1. 不满足时 2. 刚好满足时
        if (sum < x) {
            return -1;
        } else if (sum == x) {
            return n;
        }

        // 目标是找到一个子数组，使得它的和为 total - x
        int k = sum - x;
        // 左边界
        int left = 0;
        // ans 为子数组的长度, 若存在, 返回结果为n - ans, 找到最长的ans, 若不存在, 则返回-1
        int ans = -1;
        // count 记录窗口的sum值, 如果超过k, 则将left右移
        int count = 0;
        for (int i = 0; i < n; i++) {
            count += nums[i];
            while (count > k) {
                // 如果超过k, 则将left右移
                count -= nums[left++];
            }
            if (count == k) {
                // 符合条件是
                ans = Math.max(ans, i - left + 1);
            }
        }
        return ans == -1 ? ans : n - ans;
    }

    /**
     * @Description: [
     * 存在问题, 因为可能中间某一段值和为x, 且i - left + 1最小
     * 该方法错误, 待完成
     * ]
     * @param nums
     * @param x
     * @return int
     * @author marks
     * @CreateDate: 2024/10/15 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int x) {
        int n = nums.length;
        int max = Arrays.stream(nums).sum();
        // 判断特殊情况
        if (max < x) {
            return -1;
        } else if (max == x) {
            return n;
        }
        int left = 0;
        int ans = n + 1;// 设置一个较大的值
        int[] pre = new int[2 * n];
        System.arraycopy(nums, 0, pre, 0, n);
        System.arraycopy(nums, 0, pre, n, n);
        int sum = 0;
        for (int i = 0; i < n * 2; i++) {
            sum += pre[i];
            if (sum == x) {
                ans = Math.min(ans, i - left + 1);
            }
            while (sum > x) {
                sum -= pre[left++];
            }
        }
        return ans > n ? -1 : ans;
    }
}
