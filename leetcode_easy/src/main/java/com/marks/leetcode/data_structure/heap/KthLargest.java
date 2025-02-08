package com.marks.leetcode.data_structure.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: 数据流中的第 K 大元素 </p>
 * <p>文件名称: LeetCode_703 </p>
 * <p>描述:
 * 设计一个找到数据流中第 k 大元素的类（class）。注意是排序后的第 k 大元素，不是第 k 个不同的元素。
 *
 * 请实现 KthLargest 类：
 *
 * KthLargest(int k, int[] nums) 使用整数 k 和整数流 nums 初始化对象。
 * int add(int val) 将 val 插入数据流 nums 后，返回当前数据流中第 k 大的元素。
 * </p>
 *
 * <p> AC:20ms/48.93MB </p>
 * @author marks
 * @version v1.0
 * @date 2025/2/7 17:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class KthLargest {
    private PriorityQueue<Integer> queue;
    private int size;

    public KthLargest(int k, int[] nums) {
        queue = new PriorityQueue<>(Comparator.comparingInt(o -> o));
        size = k;
        for (int i = 0; i < nums.length; i++) {
            queue.offer(nums[i]);
            if (queue.size() > size) {
                queue.poll();
            }
        }
    }

    public int add(int val) {
        queue.offer(val);
        while (queue.size() > size) {
            queue.poll();
        }
        return queue.peek();
    }
}
