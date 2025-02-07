package com.marks.leetcode.data_structure.queue;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 10:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_239 {
    /**
     * @Description:
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
     * 你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     *
     * 返回 滑动窗口中的最大值 。
     * @param nums
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/7 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result;
        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 单调队列, 双端队列
     *
     * AC:31ms/57.80MB
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)
     * @param nums
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/7 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                queue.pollLast();
            }
            queue.offerLast(i);
        }
        int[] ans = new int[n - k + 1];
        ans[0] = nums[queue.peekFirst()];
        for (int i = k; i < n; i++) {
            while (!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]) {
                queue.pollLast();
            }
            queue.offerLast(i);
            while (queue.peekFirst() <= i - k) {
                queue.pollFirst();
            }
            ans[i - k + 1] = nums[queue.peekFirst()];
        }
        return ans;

    }

    /**
     * @Description:
     * 队列实现, 优先队列
     * AC:89ms/58.74MB
     * 时间复杂度: O(nlogn)
     * 空间复杂度: O(n)
     * @param nums
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/2/7 10:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n - k + 1];
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            if (o1[0] != o2[0]) {
                return o2[0] - o1[0];
            } else {
                return o2[1] - o1[1];
            }
        });

        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{nums[i], i});
        }
        ans[0] = queue.peek()[0];

        for (int i = k; i < n; i++) {
            queue.offer(new int[]{nums[i], i});
            while (queue.peek()[1] <= i - k) {
                queue.poll();
            }
            ans[i - k + 1] = queue.peek()[0];
        }
        return ans;
    }
}
