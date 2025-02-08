package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/8 10:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2233 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 给你一个非负整数数组 nums 和一个整数 k 。每次操作，你可以选择 nums 中 任一 元素并将它 增加 1 。
     *
     * 请你返回 至多 k 次操作后，能得到的 nums的 最大乘积 。由于答案可能很大，请你将答案对 10^9 + 7 取余后返回。
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumProduct(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * ans 不能用int, 必须用long, 否则会产生溢出 (long ans = 0;)
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/8 11:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        for (int i = 0; i < n; i++) {
            queue.offer(nums[i]);
        }
        while (k > 0) {
            if (!queue.isEmpty()) {
                queue.offer(queue.poll() + 1);
            }
            k--;
        }
        long ans = 0;
        if (!queue.isEmpty()) {
            ans = queue.poll();
        }
        while (!queue.isEmpty()) {
            ans = (ans * queue.poll()) % MOD;
        }
        return (int) ans;
    }
}
