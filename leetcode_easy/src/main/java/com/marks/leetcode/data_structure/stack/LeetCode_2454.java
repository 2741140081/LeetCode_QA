package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2454 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/18 15:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2454 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的非负整数数组 nums 。对于 nums 中每一个整数，你必须找到对应元素的 第二大 整数。
     * 如果 nums[j] 满足以下条件，那么我们称它为 nums[i] 的 第二大 整数：
     * j > i
     * nums[j] > nums[i]
     * 恰好存在 一个 k 满足 i < k < j 且 nums[k] > nums[i] 。
     * 如果不存在 nums[j] ，那么第二大整数为 -1 。
     * 比方说，数组 [1, 2, 4, 3] 中，1 的第二大整数是 4 ，2 的第二大整数是 3 ，3 和 4 的第二大整数是 -1 。
     * 请你返回一个整数数组 answer ，其中 answer[i]是 nums[i] 的第二大整数。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^9
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/18 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] secondGreaterElement(int[] nums) {
        int[] result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 有3个下标[i, k, j], 并且 i < k < j, 且 nums[k] > nums[i] && nums[j] > nums[i], 但是 nums[k] 和 nums[j] 的大小关系不确定(任意关系都可以)
     * 2. 先降低预期, 即找到 第一个 nums[k] > nums[i] && k > i, 这种直接可以用单调递减栈来进行处理, 那么可以得到一个第一个最大数的结果即 firstGreater[i],
     * firstGreater[i] = k, 表示 nums[i] 的第一个最大数下标值
     * 3. 直接看题解吧
     * need todo: 当前看题解CV, 需要自行理解.
     * AC: 38ms/106.87MB
     * @param: nums
     * @return int[]
     * @author marks
     * @CreateDate: 2026/05/18 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);
        Deque<Integer> stack = new ArrayDeque<Integer>();
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < nums.length; ++i) {
            while (!pq.isEmpty() && pq.peek()[0] < nums[i]) {
                res[pq.poll()[1]] = nums[i];
            }
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                pq.offer(new int[]{nums[stack.peek()], stack.peek()});
                stack.pop();
            }
            stack.push(i);
        }
        return res;
    }

}
