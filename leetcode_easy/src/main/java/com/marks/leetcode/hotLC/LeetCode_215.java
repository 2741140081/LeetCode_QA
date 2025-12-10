package com.marks.leetcode.hotLC;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_215 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/10 9:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_215 {

    /**
     * @Description:
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/10 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findKthLargest(int[] nums, int k) {
        int result = 0;
        result = method_01(nums, k);
        result = method_02(nums, k);
        result = method_03(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 自行构建堆排序
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/10 10:46
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_03(int[] nums, int k) {
        int n = nums.length;
        int heapSize = n;
        buildMaxHeap(nums, heapSize); // 构建最大堆
        for (int i = n - 1; i >= n - k + 1; i--) {
            // 取出k - 1 个最大的元素
            swap(nums, 0, i); // 把堆顶的最大元素放到堆尾部, 相当于删除了堆顶的元素
            heapSize--;
            maxHeapify(nums, 0, heapSize); // 堆顶的元素可能不是最大的元素，需要重新调整
        }
        return nums[0];
    }

    private void buildMaxHeap(int[] nums, int heapSize) {
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            maxHeapify(nums, i, heapSize);
        }
    }

    private void maxHeapify(int[] nums, int i, int heapSize) {
        int left = i * 2 + 1, right = i * 2 + 2, largest = i;
        if (left < heapSize && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < heapSize && nums[right] > nums[largest]) {
            largest = right;
        }
        if (largest != i) {
            swap(nums, i, largest);
            maxHeapify(nums, largest, heapSize);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * @Description:
     * 1. 使用优先队列存储较大的元素, 使用优先队列的最小堆存储最大的元素
     * AC: 80ms/67.36MB
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/10 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums, int k) {
        // 构建优先队列
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            priorityQueue.add(nums[i]);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        return priorityQueue.peek();
    }

    /**
     * @Description:
     * E1:
     * 输入: [3,2,3,1,2,4,5,5,6], k = 4
     * 输出: 4
     * 1. 排序
     * @param: nums
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/10 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        return nums[n - k];
    }

}
