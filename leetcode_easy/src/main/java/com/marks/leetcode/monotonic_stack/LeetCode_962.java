package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/28 14:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_962 {
    /**
     * @Description: [
     * 给定一个整数数组 A，坡是元组 (i, j)，其中  i < j 且 A[i] <= A[j]。这样的坡的宽度为 j - i。
     *
     * 找出 A 中的坡的最大宽度，如果不存在，返回 0 。
     *
     * tips:
     * 2 <= A.length <= 50000
     * 0 <= A[i] <= 50000
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/11/28 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxWidthRamp(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description: [
     * 输入：[6,0,8,2,1,5]
     * 输出：4
     * 解释：
     * 最大宽度的坡为 (i, j) = (1, 5): A[1] = 0 且 A[5] = 5.
     * 想到了, got it!
     * 之前都是找离 i 最近的一个元素大于nums[i], 本题是找最远的一个元素大于nums[i]
     * [73,74,75,71,69,72,76,73]
     * [73] -> [74] -> [75] -> [75, 71] -> [75, 71, 69]
     * -> [75, 72] -> [76] -> [76, 73]
     * AC:11ms/54.19MB
     *
     * ]
     * @param nums 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/28 14:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int ans = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (stack.isEmpty() || nums[stack.peek()] > nums[i]) {
                stack.push(i);
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];
            while (!stack.isEmpty() && nums[stack.peek()] <= num) {
                Integer preIndex = stack.poll();
                ans = Math.max(ans, i - preIndex);
            }
        }
        return ans;
    }
}
