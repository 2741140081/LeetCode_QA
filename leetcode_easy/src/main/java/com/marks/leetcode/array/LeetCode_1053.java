package com.marks.leetcode.array;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1053 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/27 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1053 {

    /**
     * @Description:
     * 给你一个正整数数组 arr（可能存在重复的元素），
     * 请你返回可在 一次交换（交换两数字 arr[i] 和 arr[j] 的位置）后得到的、按字典序排列小于 arr 的最大排列。
     * 如果无法这么操作，就请返回原数组。
     *
     * tips:
     * 1 <= arr.length <= 10^4
     * 1 <= arr[i] <= 10^4
     * @param: arr
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/27 15:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] prevPermOpt1(int[] arr) {
        int[] result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description:
     * 1. 要求小于 arr 的字典序, 并且是最大的排列, 存在下标 i > j, 并且 nums[i] < nums[j], 此时执行一次交换后,
     * 得到的数组字典序小于原数组
     * 2. 如何得到最大值, 应该使用贪心的思想, 假设由 a b c 三个下标, 其中 a < b < c 并且 a > c, b > c, 此时交换 a c 或者 b c 都可以，
     * 那么那种方案得到的结果会更大, 很明显 交换 b, c 得到的结果更大.
     * 3. 同样如果 a, b, c a > b && a > c, b 和 c 大小关系不确定, 但是 b 的坐标比c 更小,\
     * AC: 1ms/46.39MB
     * @param: arr
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/27 15:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] arr) {
        int n = arr.length;
        // 创建一个递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        // 从后向前遍历
        int max = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (stack.isEmpty() || arr[i] <= arr[stack.peek()]) {
                stack.push(i);
            } else {
                max = i;
                break;
            }
        }
        if (max == -1) {
            // 返回原数组, 即数组是升序数组, 无法减小
            return arr;
        }
        int prev = stack.peek();
        while (!stack.isEmpty() && arr[stack.peek()] < arr[max]) {
            // 值最大, 并且值相同时, 选择下标最小
            int idx = stack.pop();
            if (arr[idx] > arr[prev]) {
                prev = idx;
            }
        }
        // 交换 max 和 prev 的值
        int temp = arr[max];
        arr[max] = arr[prev];
        arr[prev] = temp;

        return arr;
    }

}
