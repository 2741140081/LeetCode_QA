package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/6 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_907 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description: [
     * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
     * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
     * tips:
     * 1 <= arr.length <= 3 * 10^4
     * 1 <= arr[i] <= 3 * 10^4
     * ]
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/12/6 10:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int sumSubarrayMins(int[] arr) {
        int result;
        result = method_01(arr);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：arr = [3,1,2,4]
     * 输出：17
     * ans + 3;
     * stack[1, 2, 4]
     * 看了看题解, 相当于求最大面积的, 而这题是求所有面积
     * []
     *
     * AC:30ms/50.69MB
     * ]
     * @param arr
     * @return int
     * @author marks
     * @CreateDate: 2024/12/6 10:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] arr) {
        int n = arr.length;
        Deque<Integer> stack = new ArrayDeque<Integer>();
        int[] left = new int[n];
        int[] right = new int[n];
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                stack.pop();
            }
            left[i] = i - (stack.isEmpty() ? -1 : stack.peek());
            stack.push(i);
        }
        stack.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[i] < arr[stack.peek()]) {
                stack.pop();
            }
            right[i] = (stack.isEmpty() ? n : stack.peek()) - i;
            stack.push(i);
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = (ans + (long)right[i] * left[i]* arr[i]) % MOD;
        }
        return (int) ans;
    }
}
