package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3676 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/12 16:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3676 {

    /**
     * @Description:
     * 给你一个整数数组 nums，包含 互不相同 的元素。
     * nums 的一个子数组 nums[l...r] 被称为 碗（bowl），如果它满足以下条件：
     * 子数组的长度至少为 3。也就是说，r - l + 1 >= 3。
     * 其两端元素的 最小值 严格大于 中间所有元素的 最大值。也就是说，min(nums[l], nums[r]) > max(nums[l + 1], ..., nums[r - 1])。
     * 返回 nums 中 碗 子数组的数量
     * 子数组 是数组中连续的元素序列。
     *
     * tips:
     * 3 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * nums 由不同的元素组成。
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/05/12 16:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long bowlSubarrays(int[] nums) {
        long result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 至少需要3个元素才能构成一个碗, 并且 bowl 的两端元素最小值严格大于中间所有元素的最大值
     * 2. 判断 i 能否有 i - 1 和 i + 1 构成一个碗, Math.min(nums[i - 1], nums[i + 1]) > nums[i]
     * 3. 需要构建一个单调递减的栈, 当前位于 i 处, nums[i], 栈顶元素为 int top = stack.peek(); 分情况讨论:
     * 3.1 top > nums[i], 则添加到栈中
     * 3.2 top == nums[i], 弹出栈顶元素, 又因为 nums[i] == top, 所以不能构成碗, ans 不变
     * 3.3 top < nums[i], 弹出栈顶元素, 并且判断 int min = Math.min(nums[i], stack.peek()); if min > top, 则构成碗, ans++;
     * 另外需要注意栈弹出元素后栈为空的情况, 由于需要3个元素才能构成碗, 所以当栈空时, 不能构成碗.
     * AC: 21ms/123.5MB
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/05/12 16:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] nums) {

        long ans = 0;
        // 单调递减栈
        Deque<Integer> stack = new ArrayDeque<>();
        for (int num : nums) {
            while (!stack.isEmpty() && stack.size() >= 2 && num >= stack.peek()) {
                int top = stack.pop();
                if (!stack.isEmpty() && Math.min(num, stack.peek()) > top) {
                    ans++;
                }
            }
            stack.push(num);
        }

        return ans;
    }

}
