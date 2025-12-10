package com.marks.leetcode.hotLC;

import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: MedianFinder </p>
 * <p>描述:
 * 中位数是有序整数列表中的中间值。如果列表的大小是偶数，则没有中间值，中位数是两个中间值的平均值。
 * 例如 arr = [2,3,4] 的中位数是 3 。
 * 例如 arr = [2,3] 的中位数是 (2 + 3) / 2 = 2.5 。
 * 实现 MedianFinder 类:
 *
 * </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/10 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class MedianFinder {
    private int size;
    // 使用优先队列构建大根堆和小根堆
    private PriorityQueue<Integer> smallHeap;
    private PriorityQueue<Integer> largeHeap;

    /**
     * @Description:
     * MedianFinder() 初始化 MedianFinder 对象。
     * 1. 使用两个堆, 一个大根堆, 一个小根堆
     * 2. 添加元素时, 添加到两个堆中, 堆的大小差不能超过1
     * 3. 获取中位数时, 获取两个堆的元素个数, 奇数时, 取中间的元素, 偶数时, 取两个堆的元素个数相同的元素
     * 4. 大根堆存储小的元素, 小根堆存储大的元素
     * AC: 160ms/109.09MB
     * @return
     * @author marks
     * @CreateDate: 2025/12/10 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public MedianFinder() {
        size = 0;
        smallHeap = new PriorityQueue<>(); // 存储大元素
        largeHeap = new PriorityQueue<>((a, b) -> b - a); // 存储小元素
    }

    /**
     * @Description:
     * void addNum(int num) 将数据流中的整数 num 添加到数据结构中。
     * 1.
     * @param: num
     * @return void
     * @author marks
     * @CreateDate: 2025/12/10 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public void addNum(int num) {
        // 判断堆的大小
        if (size % 2 == 0) {
            if (!largeHeap.isEmpty() && num < largeHeap.peek()) {
                largeHeap.offer(num);
                num = largeHeap.poll();
            }
            smallHeap.offer(num);
        } else {
            if (!smallHeap.isEmpty() && num > smallHeap.peek()) {
                smallHeap.offer(num);
                num = smallHeap.poll();
            }
            largeHeap.offer(num);
        }
        size++;
    }

    /**
     * @Description:
     * double findMedian() 返回到目前为止所有元素的中位数。与实际答案相差 10^-5 以内的答案将被接受。
     * 1. 获取两个堆的元素个数, 奇数时, 取中间的元素, 偶数时, 取两个堆的元素个数相同的元素
     * @return double
     * @author marks
     * @CreateDate: 2025/12/10 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double findMedian() {
        if (size % 2 == 0) {
            return (smallHeap.peek() + largeHeap.peek()) / 2.0;
        } else {
            return smallHeap.peek();
        }
    }
}
