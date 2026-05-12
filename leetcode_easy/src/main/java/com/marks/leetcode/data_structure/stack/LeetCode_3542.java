package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3542 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/12 14:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3542 {

    /**
     * @Description:
     * 给你一个大小为 n 的 非负 整数数组 nums 。你的任务是对该数组执行若干次（可能为 0 次）操作，使得 所有 元素都变为 0。
     * 在一次操作中，你可以选择一个子数组 [i, j]（其中 0 <= i <= j < n），将该子数组中所有 最小的非负整数 的设为 0。
     * 返回使整个数组变为 0 所需的最少操作次数。
     * 一个 子数组 是数组中的一段连续元素。
     *
     * tips:
     * 1 <= n == nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/12 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 每一次操作, 会将子数组[i, j] 中所有的最小的非负整数设为 0
     * 2. 232 类型, 需要2次操作, 但是 323 类型, 需要3次操作
     * 3. 需要一个单调递增的栈, 存储元素. 栈顶元素和下一个元素情况进行讨论
     * 3.1 stack.peek() < nums[i], 添加到栈中
     * 3.2 stack.peek() = nums[i], 弹出栈顶元素, 但是 ans 不变,
     * 3.3 stack.peek() > nums[i], 弹出栈顶元素, ans++, 直到栈顶元素小于 nums[i],
     * 并且当 stack.peek() == nums[i] 时, ans 不变.
     * 4. 需要特殊处理 0 的情况
     * AC: 50ms/141.13MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/05/12 14:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int ans = 0;
        // 创建单调递增栈
        Deque<Integer> stack = new ArrayDeque<>();
        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() >= num) {
                int max = stack.pop();
                if (max > num) {
                    ans++;
                }
            }
            stack.push(num);
        }
        // 处理栈中剩余元素
        while (!stack.isEmpty()) {
            int max = stack.pop();
            if (max > 0) { // 由于 0 不需要操作, 所以需要特殊处理
                ans++;
            }
        }
        return ans;
    }

}
