package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1673 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/25 14:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1673 {

    /**
     * @Description:
     * 给你一个整数数组 nums 和一个正整数 k ，返回长度为 k 且最具 竞争力 的 nums 子序列。
     * 数组的子序列是从数组中删除一些元素（可能不删除元素）得到的序列。
     * 在子序列 a 和子序列 b 第一个不相同的位置上，如果 a 中的数字小于 b 中对应的数字，那么我们称子序列 a 比子序列 b（相同长度下）更具 竞争力 。
     * 例如，[1,3,4] 比 [1,3,5] 更具竞争力，在第一个不相同的位置，也就是最后一个位置上， 4 小于 5 。
     * @param: nums
     * @param: k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/25 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] mostCompetitive(int[] nums, int k) {
        int[] result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 构建一个单调递增的栈, 栈中保存的是值
     * 2. 当前值小于栈顶值, 如果 i 后面还有 j 个元素, 那么可以弹出栈顶元素, 直到栈为空或者栈顶元素小于当前值
     * 或者 j 个元素填充到栈后, 无法使得栈的大小为 k
     * 3. 假设当前栈的大小已经填充了 k 个元素, 可以弹出 n - i <= k 个元素
     * AC: 24ms/113.77MB
     * @param: nums
     * @param: k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/12/25 14:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int k) {
        int n = nums.length;
        // 队列 Deque
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int j = n - i; // 剩余元素个数
            while (!stack.isEmpty() && stack.peek() > nums[i]) {
                if (stack.size() - 1 + j >= k) {
                    // 可以弹出栈顶元素
                    stack.pop();
                } else {
                    // 跳出循环
                    break;
                }
            }
            // 需要维持栈的大小为 k
            if (stack.size() < k) {
                stack.push(nums[i]); // 添加当前元素到栈中
            }
        }
        int[] ans = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            ans[i] = stack.pop();
        }
        return ans;
    }

}
