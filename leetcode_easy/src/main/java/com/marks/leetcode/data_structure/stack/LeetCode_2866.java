package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2866 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/12 15:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2866 {

    /**
     * @Description:
     * 给你一个长度为 n 下标从 0 开始的整数数组 maxHeights 。
     * 你的任务是在坐标轴上建 n 座塔。第 i 座塔的下标为 i ，高度为 heights[i] 。
     * 如果以下条件满足，我们称这些塔是 美丽 的：
     * 1 <= heights[i] <= maxHeights[i]
     * heights 是一个 山脉 数组。
     * 如果存在下标 i 满足以下条件，那么我们称数组 heights 是一个 山脉 数组：
     * 对于所有 0 < j <= i ，都有 heights[j - 1] <= heights[j]
     * 对于所有 i <= k < n - 1 ，都有 heights[k + 1] <= heights[k]
     * 请你返回满足 美丽塔 要求的方案中，高度和的最大值 。
     *
     * tips:
     * 1 <= n == maxHeights <= 10^5
     * 1 <= maxHeights[i] <= 10^9
     * @param: maxHeights
     * @return long
     * @author marks
     * @CreateDate: 2026/05/12 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        long result;
        result = method_01(maxHeights);
        return result;
    }

    /**
     * @Description:
     * 1. h[i] <= max[i]
     * 2. 如何确定某一个点是山顶? 可能需要遍历整个 max[] 数组, 然后取 i 作为山顶, 假设我们以当前 i 作为山顶. 如何处理两侧元素?
     * 3. [j, i] 是左侧, [i, k] 是右侧, 先处理[j, i], 从左向右遍历, max[j] >= max[j - 1], 这是满足条件情况, 添加从左向右的动态规划
     * int[] leftDp = new int[n]; dp[j] = dp[j - 1] + max[j]; leftDp[] 是当 j 作为山顶时, 从左侧的可以得到的最大值是 leftDp[j].
     * 3.1 max[j] < max[j - 1], 则将 j - 1 弹出, 那么栈中存储的是下标, 而不是具体值, 栈中继续弹出元素, 直到 栈为空或者栈顶元素小于等于 max[j].
     * 如果栈为空, leftDp[j] = (j - 0 + 1) * max[j]; 如果栈不为空, max[idx] <= max[j],
     * 即 j 到 idx + 1 这一段总共有多少个元素? j - (idx + 1) + 1 = j - idx 个元素, 则 leftDp[j] = leftDp[idx] + (j - idx) * max[j];
     * 4. 同理可以得到 rightDp[], 处理右侧元素
     * 5. 最后遍历整个数组, 取最大值
     * AC: 33ms/97.34MB
     * @param: maxHeights
     * @return long
     * @author marks
     * @CreateDate: 2026/05/12 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(List<Integer> maxHeights) {
        int n = maxHeights.size();
        long[] leftDp = new long[n];
        long[] rightDp = new long[n];
        // 创建单调递增栈, 存储下标值
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && maxHeights.get(stack.peek()) > maxHeights.get(i)) {
                // 弹出元素
                stack.pop();
            }
            // 更新leftDp[i + 1]
            leftDp[i] = stack.isEmpty() ? (long) (i + 1) * maxHeights.get(i) : ((long) (i - stack.peek()) * maxHeights.get(i) + leftDp[stack.peek()]);
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && maxHeights.get(stack.peek()) > maxHeights.get(i)) {
                stack.pop();
            }
            rightDp[i] = stack.isEmpty() ? (long) (n - i) * maxHeights.get(i) : ((long) (stack.peek() - i) * maxHeights.get(i) + rightDp[stack.peek()]);
            stack.push(i);
        }
        long result = 0;
        for (int i = 0; i < n; i++) {
            // 减去重复的元素
            result = Math.max(result, leftDp[i] + rightDp[i] - maxHeights.get(i));
        }

        return result;
    }

}
