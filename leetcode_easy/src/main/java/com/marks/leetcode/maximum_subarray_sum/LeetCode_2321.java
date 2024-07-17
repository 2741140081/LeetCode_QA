package com.marks.leetcode.maximum_subarray_sum;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class LeetCode_2321 {
    /**
     * 给你两个下标从 0 开始的整数数组 nums1 和 nums2 ，长度都是 n 。
     * 你可以选择两个整数 left 和 right ，其中 0 <= left <= right < n ，接着 交换 两个子数组 nums1[left...right] 和 nums2[left...right] 。
     * 例如，设 nums1 = [1,2,3,4,5] 和 nums2 = [11,12,13,14,15] ，整数选择 left = 1 和 right = 2，那么 nums1 会变为 [1,12,13,4,5] 而 nums2 会变为 [11,2,3,14,15] 。
     * 你可以选择执行上述操作 一次 或不执行任何操作。
     * 数组的 分数 取 sum(nums1) 和 sum(nums2) 中的最大值，其中 sum(arr) 是数组 arr 中所有元素之和。
     * 返回 可能的最大分数 。
     * 子数组 是数组中连续的一个元素序列。arr[left...right] 表示子数组包含 nums 中下标 left 和 right 之间的元素（含 下标 left 和 right 对应元素）。
     *
     * E1:
     * 输入：nums1 = [60,60,60], nums2 = [10,90,10]
     * 输出：210
     * 解释：选择 left = 1 和 right = 1 ，得到 nums1 = [60,90,60] 和 nums2 = [10,60,10] 。
     * 分数为 max(sum(nums1), sum(nums2)) = max(210, 80) = 210 。
     *
     * E2:
     * 输入：nums1 = [20,40,20,70,30], nums2 = [50,20,50,40,20]
     * 输出：220
     * 解释：选择 left = 3 和 right = 4 ，得到 nums1 = [20,40,20,40,20] 和 nums2 = [50,20,50,70,30] 。
     * 分数为 max(sum(nums1), sum(nums2)) = max(140, 220) = 220 。
     *
     * E3:
     * 输入：nums1 = [7,11,13], nums2 = [1,1,1]
     * 输出：31
     * 解释：选择不交换任何子数组。
     * 分数为 max(sum(nums1), sum(nums2)) = max(31, 3) = 31 。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        int result = 0;
//        result = method_01(nums1, nums2);
        result = method_02(nums1, nums2);
        return result;
    }

    /**
     * 思路:
     * 已知条件:
     * nums1 = [60,60,60], nums2 = [10,90,10]
     * 1.sum(nums1) = sum1
     * 2.sum(nums2) = sum2
     * Eg E1:
     * sum1 = 180
     * sum2 = 110
     * if sum1 > sum2
     * 我需要执行将nums2中部分元素替换到nums1中(连续的、执行1次或者不执行)
     *
     * 后续该怎么处理 wait！
     * 我可以构造一个dp[n] 来存储nums1 前i个元素相加的和(包括i)
     * for nums[i] : nums1/nums2
     * dp[i] += num[i]
     * 得到
     * dp_1[] = {60, 120, 180}
     * dp_2[] = {10, 100, 110}
     *
     *
     * 继续思路:
     * 假设我用dp[n][0/1] 存储分别来自 max(nums1[i], nums2[i])中的最大值
     * int[][] dp = new int[n][2]
     * {{60,0}, {90,1}, {90,1}, {90,1}, {60,0}, {90,1}, {60,0}, {60,0}}
     * dp[0][0] = 60
     * dp[0][1] = 0
     * if sum1 > sum2
     * 我将寻找dp[][]中下标为1的最大子串和
     * 遍历dp
     *
     * 开干!!!
     *
     * 思路错误, 想的太简单了, 需要找最大子串和, 并非紧紧只是下标连续就行
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] dp = new int[n][2];
        for (int i = 0; i < n; i++) {
            // 定义0表示来自nums1的元素, 1表示来自nums2的元素
            if (nums1[i] >= nums2[i]) {
                dp[i][0] = nums1[i];
                dp[i][1] = 0;
            }else {
                dp[i][0] = nums2[i];
                dp[i][1] = 1;
            }

        }
        // 需要将nums2的元素转到nums1中
        int sum_0 = 0;
        int sum_1 = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i][1] == 0) {
                sum_0 += dp[i][0];
            }else {
                sum_1 += dp[i][0];
            }
        }
        if (sum_0 >= sum_1) {
            int left = 0;
            int right = 0;
            int sum = 0;
            int max = 0;
            ArrayDeque<int[]> deque = new ArrayDeque<>();
            // value: {sum, left, right}
            int[] value = new int[3];

            for (int i = 0; i < n; i++) {
                if (dp[i][1] == 1) {
                    if (sum == 0) {
                        left = i;
                    }
                    sum += dp[i][0];
                    max = Math.max(max, sum);
                }else {

                    if (sum != 0) {
                        // sum 不为0表示是从1进入到0
                        right = i - 1;
                        value[0] = sum;
                        value[1] = left;
                        value[2] = right;

                        while (!deque.isEmpty() && deque.peekLast()[0] < sum) {
                            deque.pollLast();
                        }
                        deque.offerLast(value);
                    }
                    sum = 0;
                }
            }
            int max_num2 = deque.peekFirst()[0];
            int sumArray = Arrays.stream(nums1).sum();
            for (int i = deque.peekFirst()[1]; i <= deque.peekFirst()[2]; i++) {
                sumArray -= nums1[i];
            }
            return sumArray + max_num2;
        }else {
            // 需要将nums2的元素转到nums1中
            int left = 0;
            int right = 0;
            int sum = 0;
            int max = 0;
            ArrayDeque<int[]> deque = new ArrayDeque<>();
            // value: {sum, left, right}
            int[] value = new int[3];

            for (int i = 0; i < n; i++) {
                if (dp[i][1] == 0) {
                    if (sum == 0) {
                        left = i;
                    }

                    sum += dp[i][0];
                    max = Math.max(max, sum);
                }else {
                    if (sum != 0) {
                        // sum 不为0表示是从1进入到0
                        right = i - 1;
                        value[0] = sum;
                        value[1] = left;
                        value[2] = right;

                        while (!deque.isEmpty() && deque.peekLast()[0] < sum) {
                            deque.pollLast();
                        }
                        deque.offerLast(value);
                    }
                    sum = 0;
                }
            }

            int max_num1 = deque.peekFirst()[0];
            int sumArray = Arrays.stream(nums2).sum();
            for (int i = deque.peekFirst()[1]; i <= deque.peekFirst()[2]; i++) {
                sumArray -= nums2[i];
            }
            return sumArray + max_num1;
        }

    }

    /**
     * 查看题解:
     * eg: nums1 = [60,60,60], nums2 = [10,90,10]
     * 求sumMax最大值
     * 已知条件
     * sum_1 = sum(num1), sum_2 = sum(nums2)
     * int n = nums1.length;
     * sumMax_1 = sum_1 + (nums2[left] - nums1[left]) +·····+ (nums2[right] - nums1[right]);
     *
     * 如果是将nums1的值替换到nums2中, 那么sumMax表达式写法
     * sumMax_2 = sum_2 + (nums1[left] - nums2[left]) + ····· + (nums1[right] - nums2[right]);
     *
     * 所以最后取Math.max(sumMax_1, sumMax_2)的最大值
     *
     * 定义一个数组, 保存数组相减的值
     * int[] sub_array = new int[n];
     * sub_array[i] = nums2[i] - nums1[i];
     *
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private int method_02(int[] nums1, int[] nums2) {
        int sumMax_1 = replaceElement(nums1, nums2);
        int sumMax_2 = replaceElement(nums2, nums1);
        return Math.max(sumMax_1, sumMax_2);
    }

    private int replaceElement(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int sum = Arrays.stream(nums1).sum();
        int[] sub_array = new int[n];
        for (int i = 0; i < sub_array.length; i++) {
            sub_array[i] = nums2[i] - nums1[i];
        }
        int[] dp = new int[n];
        int max = Math.max(sub_array[0], 0);
        dp[0] = Math.max(sub_array[0], 0);
        for (int i = 1; i < sub_array.length; i++) {
            dp[i] = Math.max(dp[i-1] + sub_array[i], sub_array[i]);
            max = Math.max(dp[i], max);
        }
        return sum + max;
    }

}
