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
 * @date 2025/2/7 16:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3066 {
    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 nums 和一个整数 k 。
     *
     * 一次操作中，你将执行：
     *
     * 选择 nums 中最小的两个整数 x 和 y 。
     * 将 x 和 y 从 nums 中删除。
     * 将 min(x, y) * 2 + max(x, y) 添加到数组中的任意位置。
     * 注意，只有当 nums 至少包含两个元素时，你才可以执行以上操作。
     *
     * 你需要使数组中的所有元素都大于或等于 k ，请你返回需要的 最少 操作次数。
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 17:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minOperations(int[] nums, int k) {
        int result;
        result = method_01(nums, k);
        return result;
    }

    /**
     * @Description:
     * AC:191ms/69.18MB
     * @param nums
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 17:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        PriorityQueue<Long> queue = new PriorityQueue<>(Comparator.comparingLong(o -> o));
        for (int i = 0; i < n; i++) {
            queue.offer((long) nums[i]);
        }
        while (queue.size() >= 2 && queue.peek() < k) {
            long x = queue.poll();
            long y = queue.poll();
            queue.offer(x + x + y);
            ans++;
        }
        return ans;
    }
}
