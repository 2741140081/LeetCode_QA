package com.marks.leetcode.sliding_window.random_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/23 16:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_209 {
    /**
     * @Description: [
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     *
     * 找出该数组中满足其总和大于等于 target 的长度最小的子数组
     *  [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     *
     *  tips:
     *  1 <= target <= 10^9
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * ]
     * @param target
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/23 16:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSubArrayLen(int target, int[] nums) {
        int result = 0;
//        result = method_01(target, nums);
        result = method_02(target, nums);
        return result;
    }
    /**
     * @Description: [前缀和 + 二分查找
     * 关键点
     * 1.pre[right] - pre[left] >= target
     * 2.index = (- index) - 1
     *
     * eg:nums = [1,2,3,4,5], target = 11
     * pre = [0,1,3,6,10,15]
     * i = 1, temp = 11, index = -5 ==> index = 4
     * ans = 4
     *
     * i = 2, temp = 12, index = -5, ===> index = 4
     * ans = Math.min(4, 3) = 3
     *
     * i = 3, temp = 14, index = -5, ===> index = 4
     * ans = Math.min(3, 2)
     *
     * ]
     * @param target
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/23 16:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int target, int[] nums) {
        int n = nums.length;
        int[] pre = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + nums[i - 1];
        }
        if (pre[n] < target) {
            return 0;
        }
        int ans = n + 1;
        for (int i = 1; i <= n; i++) {
            // pre[right] - pre[left] >= target
            int temp = target + pre[i - 1];
            int index = Arrays.binarySearch(pre, temp);
            if (index < 0) {
                index = -index;
                index -= 1; // index - 1是 获取nums[]的下标
            }
            if (index <= n) {
                ans = Math.min(ans, index - i + 1);
            }
        }
        return ans == n + 1 ? 0 : ans;
    }

    /**
     * @Description: [
     * nums = [1,2,3,4,5], target = 11
     * 滑动窗口
     * AC:1ms/53.69MB
     * ]
     * @param target
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/23 16:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int target, int[] nums) {
        int n = nums.length;
        int left = 0;
        int ans = n + 1;

        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            while (sum - nums[left] >= target) {
                sum -= nums[left++];
            }
            if (sum >= target) {
                ans = Math.min(ans, i - left + 1);
            }
        }
        return ans == n + 1 ? 0 : ans;
    }
}
