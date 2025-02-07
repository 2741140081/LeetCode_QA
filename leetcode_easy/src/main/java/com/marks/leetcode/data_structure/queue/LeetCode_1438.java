package com.marks.leetcode.data_structure.queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1438 {
    /**
     * @Description:
     * 给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，
     * 该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
     *
     * 如果不存在满足条件的子数组，则返回 0 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * 0 <= limit <= 10^9
     * @param nums
     * @param limit
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 14:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestSubarray(int[] nums, int limit) {
        int result;
        result = method_01(nums, limit);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：nums = [8,4,2,7], limit = 4
     * 输出：2
     * [8, 0]
     * [8, 0], [4, 1]
     * 先按自己的想法来, 即双端队列中存储一个
     * 滑动窗口 + 单调队列
     * AC:38ms/57.09MB
     * @param nums
     * @param limit
     * @return int
     * @author marks
     * @CreateDate: 2025/2/7 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int limit) {
        int n = nums.length;
        int ans = 0, left = 0, right = 0;
        Deque<Integer> queMax = new LinkedList<>(); // 单调递增队列, 找最大值
        Deque<Integer> queMin = new LinkedList<>(); // 单调递减队列, 找最小值
        while (right < n) {
            int num = nums[right];
            while (!queMax.isEmpty() && queMax.peekLast() < num) {
                queMax.pollLast();
            }
            while (!queMin.isEmpty() && queMin.peekLast() > num) {
                queMin.pollLast();
            }
            queMax.offerLast(num);
            queMin.offerLast(num);
            while (!queMax.isEmpty() && !queMin.isEmpty() && (queMax.peekFirst() - queMin.peekFirst()) > limit) {
                if (nums[left] == queMax.peekFirst()) {
                    queMax.pollFirst();
                }
                if (nums[left] == queMin.peekFirst()) {
                    queMin.pollFirst();
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }
}
