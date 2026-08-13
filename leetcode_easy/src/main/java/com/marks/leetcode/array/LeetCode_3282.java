package com.marks.leetcode.array;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3282 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/8 15:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3282 {

    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 nums 。
     * 你的目标是从下标 0 出发，到达下标 n - 1 处。每次你只能移动到 更大 的下标处。
     * 从下标 i 跳到下标 j 的得分为 (j - i) * nums[i] 。
     * 请你返回你到达最后一个下标处能得到的 最大总得分 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/08 15:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long findMaximumScore(List<Integer> nums) {
        long result;
        result = method_01(nums);
        result = method_02(nums);
        result = method_03(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 查看题解, 直接记录最大值即可
     * AC: 8ms/159.19MB
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/08 16:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_03(List<Integer> nums) {
        long ans = 0;
        long maxScore = 0;
        int n = nums.size();
        for (int i = 0; i < n - 1; i++) {
            maxScore = Math.max(maxScore, nums.get(i));
            ans += maxScore;
        }
        return ans;
    }

    /**
     * @Description:
     * 1. 使用单调递减栈, 存储 int[] 数据, int[] {value, idx}, 根据value 值进行单调递减.
     * 2. 需要舍弃 n - 1 的位置, 因为不能计算最后一个位置处得分
     * AC: 45ms/176.37MB
     * 3. 时间复杂度是O(n), 所有元素值进行一次入栈和出栈操作
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/08 16:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_02(List<Integer> nums) {
        int n = nums.size();
        // 创建单调递减栈, 使用 ArrayDeque
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{Integer.MAX_VALUE / 2, n - 1});
        for (int i = n - 2; i >= 0; i--) {
            // 先弹出栈顶元素小于等于 nums[i]
            while (!stack.isEmpty() && stack.peek()[0] <= nums.get(i)) {
                stack.pop();
            }
            stack.push(new int[]{nums.get(i), i});
        }
        long ans = 0;
        while (stack.size() > 1) {
            int[] cur = stack.pop();
            int[] next = stack.peek();
            ans += (long) (next[1] - cur[1]) * cur[0];
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 动态规划肯定不行, 普通的动态规划需要的时间复杂度是 O(n^2), 超时
     * 2. 采用贪心的策略, 将 list 添加到优先队列中, 优先队列的排序是大根堆,int[] {value, idx}
     * 3. 同时需要记录最小的 int minIdx 值, 这个 idx 表示已经处理的 idx 值
     * 4. 由于不能计算最后一个位置处, 所以 minIdx 最大值是 n - 1
     * AC: 453ms/197.37MB
     * 5. 由于使用了优先队列, 所以时间复杂度是 O(nlogn), 但是完全可以从后向前遍历, 贪心的思路来处理, 使用单调栈来处理
     * @param: nums
     * @return long
     * @author marks
     * @CreateDate: 2026/07/08 15:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(List<Integer> nums) {
        int n = nums.size();
        // 创建大根堆的优先队列, int[] {value, idx}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        int minIdx = n - 1; // 当前位于 n 的位置
        // 将 nums 添加到优先队列中
        for (int i = 0; i < n - 1; i++) {
            pq.offer(new int[] {nums.get(i), i});
        }
        long ans = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int value = cur[0], idx = cur[1];
            // 判断 minIdx 与 idx 之间的关系
            if (minIdx > idx) {
                ans += (long) (minIdx - idx) * value;
                // 更新 minIdx
                minIdx = idx;
            }
        }

        return ans;
    }

}
