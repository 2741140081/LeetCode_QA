package com.marks.leetcode.data_structure.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 15:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_862 {
    /**
     * @Description:
     * 给你一个整数数组 nums 和一个整数 k ，找出 nums 中和至少为 k 的 最短非空子数组 ，并返回该子数组的长度。
     * 如果不存在这样的 子数组 ，返回 -1 。
     *
     * 子数组 是数组中 连续 的一部分。
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestSubarray(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }
    
    /**
     * @Description:
     * nums = [2,-1,2], k = 3
     * 待完成, 这个完成只是查看官解
     * TODO
     * @param nums 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 15:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        long[] preSumArr = new long[n + 1];
        for (int i = 0; i < n; i++) {
            preSumArr[i + 1] = preSumArr[i] + nums[i];
        }
        int res = n + 1;
        Deque<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 0; i <= n; i++) {
            long curSum = preSumArr[i];
            while (!queue.isEmpty() && curSum - preSumArr[queue.peekFirst()] >= k) {
                res = Math.min(res, i - queue.pollFirst());
            }
            while (!queue.isEmpty() && preSumArr[queue.peekLast()] >= curSum) {
                queue.pollLast();
            }
            queue.offerLast(i);
        }
        return res < n + 1 ? res : -1;
    }
}
