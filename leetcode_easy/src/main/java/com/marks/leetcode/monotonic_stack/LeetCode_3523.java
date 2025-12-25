package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3523 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/25 16:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3523 {

    /**
     * @Description:
     * 给你一个整数数组 nums。在一次操作中，你可以选择一个子数组，并将其替换为一个等于该子数组 最大值 的单个元素。
     * 返回经过零次或多次操作后，数组仍为 非递减 的情况下，数组 可能的最大长度。
     * 子数组 是数组中一个连续、非空 的元素序列。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/25 16:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int maximumPossibleSize(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 使用单调栈, 从后往前遍历, 单调非严格递减栈
     * AC: 36ms/151.03MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2025/12/25 16:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() < nums[i]) {
                stack.pop();
            }
            stack.push(nums[i]);
        }
        return stack.size();
    }

}
