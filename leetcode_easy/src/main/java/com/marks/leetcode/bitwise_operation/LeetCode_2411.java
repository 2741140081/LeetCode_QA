package com.marks.leetcode.bitwise_operation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 9:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2411 {


    /**
     * @Description:
     * 给你一个长度为 n 下标从 0 开始的数组 nums ，数组中所有数字均为非负整数。
     * 对于 0 到 n - 1 之间的每一个下标 i ，你需要找出 nums 中一个 最小 非空子数组，它的起始位置为 i （包含这个位置），同时有 最大 的 按位或运算值 。
     *
     * 换言之，令 Bij 表示子数组 nums[i...j] 的按位或运算的结果，你需要找到一个起始位置为 i 的最小子数组，这个子数组的按位或运算的结果等于 max(Bik) ，其中 i <= k <= n - 1 。
     * 一个数组的按位或运算值是这个数组里所有数字按位或运算的结果。
     *
     * 请你返回一个大小为 n 的整数数组 answer，其中 answer[i]是开始位置为 i ，按位或运算结果最大，且 最短 子数组的长度。
     *
     * 子数组 是数组里一段连续非空元素组成的序列。
     *
     * tips:
     * n == nums.length
     * 1 <= n <= 10^5
     * 0 <= nums[i] <= 10^9
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/10/10 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] smallestSubarrays(int[] nums) {
        int[] result;
        result = method_01(nums);
        result = method_02(nums);
        result = method_03(nums);

        return result;
    }

    private int[] method_03(int[] nums) {
        return new int[0];
    }


    /**
     * @Description:
     * 1. 想到一个思路
     * 2. 对于每一个nums[i] 已经更新的 nums[i] |= x;
     * 3. List<Integer>[] list = new List[32]; 假设nums[i] = 2, 二进制 0010, 存储为0的位置,
     * 即将 i 下标存储到 list[0], list[2] ... list[30] 位置处 list[0].add(i)
     * 4. 当处理x时, 假设 x = 3, 二进制 0011, 即它的0位和1位是1, 需要将list[0] 和 list[1] 给遍历,
     * AC: 119ms/143.31MB
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/10/10 10:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums) {
        int maxValue = Arrays.stream(nums).max().getAsInt();
        int length = maxValue == 0 ? 1 : Integer.SIZE - Integer.numberOfLeadingZeros(maxValue);
        List<Integer>[] lists = new List[length];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = new ArrayList<>();
        }
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, 1);
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 0; j < lists.length; j++) {
                if ( (x & (1 << j)) != 0) {
                    // 当前位不是0, 将list[j]更新
                    for (int left: lists[j]) {
                        ans[left] = i - left + 1;
                    }
                    lists[j] = new ArrayList<>();
                } else {
                    // 当前位是0
                    lists[j].add(i);
                }
            }
        }
        return ans;
    }


    /**
     * @Description:
     * 1. 双重for循环遍历,
     * 超时!!! 怎么优化?
     * @param nums
     * @return int[]
     * @author marks
     * @CreateDate: 2025/10/10 9:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 每一个元素自身是一个最大值, 由于不能是空数组, 所以给全部赋值为1
        Arrays.fill(ans, 1);
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = i - 1; j >= 0 && x != 0; j--) {
                if ( (nums[j] | x) > nums[j] ) {
                    ans[j] = i - j + 1; // 更新长度
                }
                nums[j] |= x; // 更新nums[j]
            }
        }

        return ans;
    }

}
