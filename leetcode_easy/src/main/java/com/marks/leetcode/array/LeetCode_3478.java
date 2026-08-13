package com.marks.leetcode.array;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3478 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/15 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3478 {

    /**
     * @Description:
     * 给你两个整数数组，nums1 和 nums2，长度均为 n，以及一个正整数 k 。
     * 对从 0 到 n - 1 每个下标 i ，执行下述操作：
     * 找出所有满足 nums1[j] 小于 nums1[i] 的下标 j 。
     * 从这些下标对应的 nums2[j] 中选出 至多 k 个，并 最大化 这些值的总和作为结果。
     * 返回一个长度为 n 的数组 answer ，其中 answer[i] 表示对应下标 i 的结果。
     *
     * tips:
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^6
     * 1 <= k <= n
     * @param: nums1
     * @param: nums2
     * @param: k
     * @return long[]
     * @author marks
     * @CreateDate: 2026/07/15 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long[] findMaxSum(int[] nums1, int[] nums2, int k) {
        long[] result;
        result = method_01(nums1, nums2, k);
        return result;
    }

    /**
     * @Description:
     * 1. 需要对 nums1 通过 Integer[] index 进行间接排序,
     * 2. 对于 nums[i], 需要通过二分查找法, 找到 Integer[] index 中小于 nums[i] 下标 j, 可以使用二分查找
     * 3. 然后构建一个基于 nums2 和 index 的前缀和 long[] preSum, 由于 index 是一个递增序列, 所以由 index 来构建的
     * preSum[i] = preSum[i - 1] + nums2[j]; 其中 j = index[i - 1];
     * AC: 301ms/142.6MB
     * @param: nums1
     * @param: nums2
     * @param: k
     * @return long[]
     * @author marks
     * @CreateDate: 2026/07/15 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] method_01(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        // 构建间接排序索引
        Integer[] index = new Integer[n];
        for (int i = 0; i < n; i++) {
            index[i] = i;
        }
        Arrays.sort(index, Comparator.comparingInt(a -> nums1[a])); // 升序排序
        // 构建前缀和
        long[] preSum = new long[n + 1];
        // 还是需要使用优先队列来存储 nums2 的 k 个最大值, 创建一个小根堆的优先队列
        PriorityQueue<Integer> pq = new PriorityQueue<>(); // 默认小根堆
        long sum = 0; // 记录窗口 pq 的和
        for (int i = 1; i <= n; i++) {
            int j = index[i - 1];
            // 向 pq 中添加 nums2[j]
            pq.add(nums2[j]);
            sum += nums2[j];
            // 判断 pq 大小是否超过 k
            if (!pq.isEmpty() && pq.size() > k) {
                int num = pq.poll();
                sum -= num;
            }
            preSum[i] = sum;
        }
        // 结果数组
        long[] ans = new long[n];
        // 遍历 nums1
        for (int i = 0; i < n; i++) {
            int num = nums1[i];
            // 通过二分查找, 找到 index 中小于 nums[i] 的最大下标 j.
            int idx = getIndexByBinarySearch(index, nums1, num);
            ans[i] = preSum[idx + 1];
        }

        return ans;
    }

    private int getIndexByBinarySearch(Integer[] index, int[] nums1, int target) {
        int n = index.length;
        int left = 0, right = n - 1;
        int ans = -1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums1[index[mid]] < target) {
                ans = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return ans;
    }

}
