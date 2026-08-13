package com.marks.leetcode.daily_question;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3660 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/7 14:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3660 {

    /**
     * @Description:
     * 给你一个整数数组 nums。
     * 从任意下标 i 出发，你可以根据以下规则跳跃到另一个下标 j：
     * 仅当 nums[j] < nums[i] 时，才允许跳跃到下标 j，其中 j > i。
     * 仅当 nums[j] > nums[i] 时，才允许跳跃到下标 j，其中 j < i。
     * 对于每个下标 i，找出从 i 出发且可以跳跃 任意 次，能够到达 nums 中的 最大值 是多少。
     *
     * 返回一个数组 ans，其中 ans[i] 是从下标 i 出发可以到达的最大值。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/07 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maxValue(int[] nums) {
        int[] result;
//        result = method_01(nums);
        result = method_02(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 官方题解, 使用单调递增栈
     * AC: 22ms/185.73MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/03 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];

        List<Item> stack = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Item curr = new Item(nums[i], i, i);

            // 单调递增栈
            while (!stack.isEmpty() && stack.get(stack.size() - 1).value() > nums[i]) {
                Item top = stack.remove(stack.size() - 1);
                curr = new Item(Math.max(curr.value(), top.value()), top.left(), curr.right());
            }

            stack.add(curr);
        }

        for (int i = 0; i < stack.size(); i++) {
            for (int j = stack.get(i).left(); j <= stack.get(i).right(); j++) {
                ans[j] = stack.get(i).value();
            }
        }

        return ans;
    }

    record Item(int value, int left, int right) {}

    /**
     * @Description:
     * 输入: nums = [2,3,1]
     * 输出: [3,3,3]
     * 1. 对于 nums[i], 向前跳跃, 可以跳跃到更小的 nums[j] 处, nums[j] < nums[i] && j > i
     * 2. 向后跳跃, 可以跳跃到更大的值处 nums[k], nums[k] > nums[i] && k < i
     * 3. 有没有什么反例, 例如某一个下标无法跳到最大值的情况? 当数组是一个非严格递减数组时, 任意点都可以跳到最大值;
     * 4. 相反, 如果数组是一个非严格递增数组时, eg:[1,2,3,3,4], 无法向前跳跃, 但是向后跳跃只能变得更小.
     * 5. 现在向非递增数组中插入一个较小值 2, 在末尾处, 整个数组变成[1,2,3,3,4,2] => 此时 3,3,4,2 都可以跳到最大值4处.
     * 然后剩余的元素继续组成数组[1,2,2] 此时的max = 4; 然后如果此时在来一个 1. 最终 2,2,1 都可以变为最大值 4, 剩余元素
     * 6. [1,1], 并且栈中应该存入的是下标值, 而不是具体值, 方便后续给 ans[] 赋值
     * 7. 概念, 创建一个单调递增栈: 栈底到栈顶元素为非严格递减序列.
     * 8. 发生错误, case: nums = {16,4,24,2,3}; 当处理到 4 时, 将16 出栈了, 导致 ans[0] = 16, 但是 0 可以跳到 下标 3 处,
     * 然后在跳到 2 处获取最大值, 导致结果错误.
     * 9. 还是错误, case: nums = {30,21,5,35,24}
     * 10. 直接查看题解吧
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/07 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        // 给 ans[] 数组进行初始化, 初始化值为 nums[], 相当于拷贝, 使用 System.arraycopy()
        System.arraycopy(nums, 0, ans, 0, n);
        // 创建单调递增栈
        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0; // 记录区间[0 ~ i] 的最大值
        // 遍历 [0 ~ n - 1]
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                int idx = stack.pop();
                ans[idx] = Math.max(ans[idx], max);
            }
            stack.push(i);
            max = Math.max(max, nums[i]);
            ans[i] = max;
        }
        // 然后倒序遍历, 将 stack 转为 递减栈, 记录最小值下标
        int minIdx = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] > nums[minIdx]) {
                ans[i] = Math.max(ans[i], ans[minIdx]);
            } else if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }
        }

        return ans;
    }

}
