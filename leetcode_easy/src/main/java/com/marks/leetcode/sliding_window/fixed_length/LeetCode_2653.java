package com.marks.leetcode.sliding_window.fixed_length;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/12 14:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2653 {
    /**
     * @Description: [
     * 给你一个长度为 n 的整数数组 nums ，请你求出每个长度为 k 的子数组的 美丽值 。
     * 一个子数组的 美丽值 定义为：如果子数组中第 x 小整数 是 负数 ，那么美丽值为第 x 小的数，否则美丽值为 0 。
     * 请你返回一个包含 n - k + 1 个整数的数组，依次 表示数组中从第一个下标开始，每个长度为 k 的子数组的 美丽值 。
     *
     * 子数组指的是数组中一段连续 非空 的元素序列。
     * tips:
     * n == nums.length
     * 1 <= n <= 10^5
     * 1 <= k <= n
     * 1 <= x <= k
     * -50 <= nums[i] <= 50
     * ]
     * @param nums
     * @param k
     * @param x
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/12 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] getSubarrayBeauty(int[] nums, int k, int x) {
        int[] result;
        result = method_01(nums, k, x);
        result = method_02(nums, k, x);
        return result;
    }
    /**
     * @Description: [
     * 查看题解
     * AC:37ms/62.03MB
     * ]
     * @param nums
     * @param k
     * @param x
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/12 15:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums, int k, int x) {
        int n = nums.length;
        final int BIAS = 50;
        // pre 存储num[i]的个数
        int[] pre = new int[2 * BIAS + 1];
        for (int i = 0; i < k - 1; i++) {
            pre[nums[i] + BIAS]++;
        }
        int[] ans = new int[n - k + 1];
        for (int i = k - 1; i < n; i++) {
            // 进入窗口元素
            pre[nums[i] + BIAS]++;
            int left = x;
            for (int j = 0; j < BIAS; j++) {
                // 枚举负数范围 [-50,-1]
                left = left - pre[j];
                if (left == 0) {
                    ans[i - k + 1] = j - BIAS;
                    break;
                }
            }
            // 离开窗口元素
            pre[nums[i - k + 1] + BIAS]--;
        }
        return ans;
    }

    /**
     * @Description: [
     * Result:703/717, 超时！！！
     * ]
     * @param nums
     * @param k
     * @param x
     * @return int[]
     * @author marks
     * @CreateDate: 2024/10/12 15:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int k, int x) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            list.add(nums[i]);
        }
        int value = list.stream().sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList()).get(x - 1);
        ans[0] = value < 0 ? value : 0;
        for (int i = k; i < n; i++) {
            list.add(nums[i]);
            list.remove(0);
            value = list.stream().sorted(Comparator.comparing(Integer::intValue)).collect(Collectors.toList()).get(x - 1);
            ans[i - k + 1] = value < 0 ? value : 0;
        }
        return ans;
    }
}
