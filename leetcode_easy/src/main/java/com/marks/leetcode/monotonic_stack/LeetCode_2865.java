package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2865 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/25 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2865 {

    /**
     * @Description:
     * 给定一个包含 n 个整数的数组 heights 表示 n 座连续的塔中砖块的数量。
     * 你的任务是移除一些砖块来形成一个 山脉状 的塔排列。
     * 在这种布置中，塔高度先是非递减，有一个或多个连续塔达到最大峰值，然后非递增排列。
     * 返回满足山脉状塔排列的方案中，高度和的最大值 。
     * tips:
     * 1 <= n == heights.length <= 10^3
     * 1 <= heights[i] <= 10^9
     * @param: heights
     * @return long
     * @author marks
     * @CreateDate: 2025/12/25 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumSumOfHeights(int[] heights) {
        long result;
        result = method_01(heights);
        result = method_02(heights);
        return result;
    }

    /**
     * @Description:
     * 1. 使用单调栈来处理
     * 2. 预处理数组, 维护单调递增栈，弹出所有大于等于当前高度的元素, 因为这些元素都大于等于当前元素,
     * 所以这些元素可以减低到当前元素的值, 可以计算当前元素的高度和
     * @param: heights
     * @return long
     * @author marks
     * @CreateDate: 2025/12/25 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(int[] heights) {
        int n = heights.length;
        long res = 0;
        // prefix[i] 表示以 i 为峰值时，左侧（包括i）能达到的最大高度和
        long[] prefix = new long[n];
        // suffix[i] 表示以 i 为峰值时，右侧（包括i）能达到的最大高度和
        long[] suffix = new long[n];
        // 用于计算 prefix 的单调栈（递增栈）
        Deque<Integer> stack1 = new ArrayDeque<Integer>();
        // 用于计算 suffix 的单调栈（递增栈）
        Deque<Integer> stack2 = new ArrayDeque<Integer>();

        // 从左到右遍历，计算每个位置作为峰值时左侧的最大高度和
        for (int i = 0; i < n; i++) {
            // 维护单调递增栈，弹出所有大于等于当前高度的元素
            while (!stack1.isEmpty() && heights[i] < heights[stack1.peek()]) {
                stack1.pop();
            }
            if (stack1.isEmpty()) {
                // 如果栈为空，说明当前位置左边所有塔都可以设为当前高度
                prefix[i] = (long) (i + 1) * heights[i];
            } else {
                // 如果栈不为空，前面部分使用之前的计算结果，当前位置到栈顶位置之间的部分设为当前高度
                prefix[i] = prefix[stack1.peek()] + (long) (i - stack1.peek()) * heights[i];
            }
            stack1.push(i);
        }

        // 从右到左遍历，计算每个位置作为峰值时右侧的最大高度和
        for (int i = n - 1; i >= 0; i--) {
            // 维护单调递增栈，弹出所有大于等于当前高度的元素
            while (!stack2.isEmpty() && heights[i] < heights[stack2.peek()]) {
                stack2.pop();
            }
            if (stack2.isEmpty()) {
                // 如果栈为空，说明当前位置右边所有塔都可以设为当前高度
                suffix[i] = (long) (n - i) * heights[i];
            } else {
                // 如果栈不为空，后面部分使用之前的计算结果，栈顶位置到当前位置之间的部分设为当前高度
                suffix[i] = suffix[stack2.peek()] + (long) (stack2.peek() - i) * heights[i];
            }
            stack2.push(i);
            // 计算以当前位置为峰值时的总高度和（注意减去heights[i]，因为prefix和suffix都包含了它）
            res = Math.max(res, prefix[i] + suffix[i] - heights[i]);
        }
        return res;
    }

    /**
     * @Description:
     * 1. 直接暴力解法, 时间复杂度 O(n^2), 空间复杂度 O(1)
     * 2. 遍历数组, 选择当前 i 作为 山峰的索引, 左侧 和右侧都为递减数组
     * AC: 12ms/45.8MB
     * @param: heights
     * @return long
     * @author marks
     * @CreateDate: 2025/12/25 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] heights) {
        int n = heights.length;
        long ans = 0;
        for (int i = 0; i < n; i++) {
            int curr = heights[i];
            // 左侧递减数组
            long leftSum = 0;
            int prevLeft = curr;
            for (int j = i - 1; j >= 0; j--) {
                prevLeft = Math.min(prevLeft, heights[j]);
                leftSum += prevLeft;
            }
            // 右侧递减数组
            long rightSum = 0;
            int prevRight = curr;
            for (int j = i + 1; j < n; j++) {
                prevRight = Math.min(prevRight, heights[j]);
                rightSum += prevRight;
            }
            ans = Math.max(ans, leftSum + rightSum + curr);
        }
        return ans;
    }

}
