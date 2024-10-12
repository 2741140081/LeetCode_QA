package com.marks.leetcode.sliding_window.fixed_length;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2134 {
    /**
     * @Description: [
     * 交换 定义为选中一个数组中的两个 互不相同 的位置并交换二者的值。
     *
     * 环形 数组是一个数组，可以认为 第一个 元素和 最后一个 元素 相邻 。
     *
     * 给你一个 二进制环形 数组 nums ，返回在 任意位置 将数组中的所有 1 聚集在一起需要的最少交换次数。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * nums[i] 为 0 或者 1
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 16:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSwaps(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }
    /**
     * @Description: [
     * AC:10ms/61.09MB
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/10/12 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int k = Arrays.stream(nums).sum();
        int n = nums.length;
        if (k >= n - 1) {
            // 全部为1 / 只存储一个0
            return 0;
        }
        int[] arr = new int[2 * n];
        System.arraycopy(nums, 0, arr, 0, n);
        System.arraycopy(nums, 0, arr, n, n);
        int ans = 0; // 只是赋值一个较大的值
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += arr[i];
        }
        ans = k - sum;
        if (ans == 0) {
            return ans;
        }

        for (int i = k; i < n + k; i++) {
            sum = sum - arr[i - k] + arr[i];
            ans = Math.min(ans, k - sum);
            if (ans == 0) {
                return ans;
            }
        }
        return ans;
    }
}
