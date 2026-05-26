package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_480 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/25 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_480 {

    /**
     * @Description:
     * 中位数是有序序列最中间的那个数。如果序列的长度是偶数，则没有最中间的数；此时中位数是最中间的两个数的平均数。
     * 例如：
     * [2,3,4]，中位数是 3
     * [2,3]，中位数是 (2 + 3) / 2 = 2.5
     * 给你一个数组 nums，有一个长度为 k 的窗口从最左端滑动到最右端。窗口中有 k 个数，每次窗口向右移动 1 位。
     * 你的任务是找出每次窗口移动后得到的新窗口中元素的中位数，并输出由它们组成的数组。
     *
     * @param: nums
     * @param: k
     * @return double[]
     * @author marks
     * @CreateDate: 2026/05/25 10:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] result;
//        result = method_01(nums, k);
        result = method_02(nums, k);
        return result;
    }

    /**
     * @Description:
     * 1. 使用优先队列 + 懒删除
     *
     * AC: 48ms/63.25MB
     * @param: nums
     * @param: k
     * @return double[]
     * @author marks
     * @CreateDate: 2026/05/25 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double[] method_02(int[] nums, int k) {
        DualHeap dualHeap = new DualHeap(k);
        int n = nums.length;
        double[] ans = new double[n - k + 1];
        for (int i = 0; i < k; i++) {
            dualHeap.insert(nums[i]);
        }
        ans[0] = dualHeap.getMedian();
        for (int i = k; i < n; i++) {
            dualHeap.insert(nums[i]);
            dualHeap.erase(nums[i - k]);
            ans[i - k + 1] = dualHeap.getMedian();
        }

        return ans;
    }


    private class DualHeap {
        private PriorityQueue<Integer> small;
        private PriorityQueue<Integer> large;
        private Map<Integer, Integer> delayed;
        private int k;
        private int smallSize;
        private int largeSize;

        public DualHeap(int k) {
            this.k = k;
//            small = new PriorityQueue<>((a, b) -> b - a); // 大根堆, 存储小的 k/2 个数, 不能使用 b - a, 由于数值较大, 导致发生溢出
            small = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
            large = new PriorityQueue<>(); // 小根堆, 存储大的 k/2 个数
            delayed = new HashMap<>();
            smallSize = largeSize = 0;
        }

        public double getMedian() {
            return (k & 1) == 1 ? small.peek() : ((double)small.peek() + (double)large.peek()) / 2;
        }

        public void insert(int num) {
            if (small.isEmpty() || num <= small.peek()) {
                small.offer(num);
                smallSize++;
            } else {
                large.offer(num);
                largeSize++;
            }
            makeBalance();
        }

        public void erase(int num) {
            delayed.merge(num, 1, Integer::sum);
            if (num <= small.peek()) {
                smallSize--;
                if (num == small.peek()) {
                    prune(small);
                }
            } else {
                largeSize--;
                if (num == large.peek()) {
                    prune(large);
                }
            }
            makeBalance();
        }

        private void makeBalance() {
            if (smallSize > largeSize + 1) {
                large.offer(small.poll());
                smallSize--;
                largeSize++;
                prune(small);
            } else if (smallSize < largeSize) {
                small.offer(large.poll());
                smallSize++;
                largeSize--;
                prune(large);
            }
        }

        private void prune(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty()) {
                int num = heap.peek();
                if (delayed.containsKey(num)) {
                    delayed.merge(num, -1, Integer::sum);
                    if (delayed.get(num) == 0) {
                        delayed.remove(num);
                    }
                    heap.poll();
                } else {
                    break;
                }
            }
        }

    }

    /**
     * @Description:
     * 1. 假设将 窗口中的元素存入一个 list 中, 那么窗口每次移动, 需要不断对list 进行删除和添加操作.
     * 2. 向有序集合中删除指定元素, 需要 O(logN) 的时间复杂度. 插入的复杂度也是 O(logN).
     * 3. 先实现基于二分查找.
     * AC: 292ms/59.27MB, 时间复杂度是O(NlogN), 空间复杂度是O(N), 时间复杂度还是太高, 看看官方题解怎么优化.
     * @param: nums
     * @param: k
     * @return double[]
     * @author marks
     * @CreateDate: 2026/05/25 10:37
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double[] method_01(int[] nums, int k) {
        List<Double> list = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < k; i++) {
            list.add((double) nums[i]);
        }
        list.sort(Comparator.comparingDouble(o -> o));
        double[] ans = new double[n - k + 1];
        if (k % 2 == 0) {
            ans[0] = (list.get(k / 2 - 1) + list.get(k / 2)) / 2.0;
        } else {
            ans[0] = list.get(k / 2);
        }
        for (int i = k; i < n; i++) {
            remove(list, nums[i - k]);
            add(list, nums[i]);
            if (k % 2 == 0) {
                ans[i - k + 1] = (list.get(k / 2 - 1) + list.get(k / 2)) / 2.0;
            } else {
                ans[i - k + 1] = list.get(k / 2);
            }
        }
        return ans;
    }

    private void add(List<Double> list, double num) {
        // 使用二分查找, 找到插入位置
        int index = Collections.binarySearch(list, num);
        if (index < 0) {
            // 得到插入点
            index = -index - 1;
            list.add(index, num);
        } else {
            list.add(index, num);
        }
    }

    private void remove(List<Double> list, double num) {
        // 使用二分查找，找到num的位置
        int index = Collections.binarySearch(list, num);
        if (index >= 0) {
            list.remove(index);
        }
    }

}
