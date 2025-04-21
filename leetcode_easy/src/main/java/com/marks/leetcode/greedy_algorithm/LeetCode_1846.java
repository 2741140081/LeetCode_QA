package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 15:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1846 {
    /**
     * @Description:
     * 给你一个正整数数组 arr 。请你对 arr 执行一些操作（也可以不进行任何操作），使得数组满足以下条件：
     *
     * arr 中 第一个 元素必须为 1 。
     * 任意相邻两个元素的差的绝对值 小于等于 1 ，也就是说，对于任意的 1 <= i < arr.length （数组下标从 0 开始），都满足 abs(arr[i] - arr[i - 1]) <= 1 。abs(x) 为 x 的绝对值。
     * 你可以执行以下 2 种操作任意次：
     *
     * 减小 arr 中任意元素的值，使其变为一个 更小的正整数 。
     * 重新排列 arr 中的元素，你可以以任意顺序重新排列。
     * 请你返回执行以上操作后，在满足前文所述的条件下，arr 中可能的 最大值 。
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * AC: 6ms/56.57MB
     * @param arr 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 15:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int ans = 1;
        Arrays.sort(arr);
        arr[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] >= arr[i - 1] + 1) {
                arr[i] = arr[i - 1] + 1;
            }
            ans = Math.max(ans, arr[i]);
        }
        return ans;
    }
}
