package com.marks.leetcode.hotLC;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_4 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 10:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_4 {

    /**
     * @Description:
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * 算法的时间复杂度应该为 O(log (m+n)) 。
     *
     * tips:
     * nums1.length == m
     * nums2.length == n
     * 0 <= m <= 1000
     * 0 <= n <= 1000
     * 1 <= m + n <= 2000
     * -10^6 <= nums1[i], nums2[i] <= 10^6
     * @param: nums1
     * @param: nums2
     * @return double
     * @author marks
     * @CreateDate: 2025/12/09 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description:
     * 1. 中位数是有序数组中间的一个数, 当前整个数组的长度是 int len = m + n, 如果 len % 2 的情况进行讨论
     * 2.1 len 是奇数, mid = len / 2; 设计一个下标 idx = 0, 然后对两个有序数组进行比较,
     * nums1[left] > nums2[right], right++, idx++; 否则 left++, idx++; 当 idx == mid 时, 返回 此时的 nums1[left] 或者 nums2[right]
     * 2.2 len 是偶数, 需要找到两个数 mid = len / 2, 以及 mid - 1. 用 prev 记录 mid - 1 的值,
     * AC: 3ms/47.87MB
     * @param: nums1
     * @param: nums2
     * @return double
     * @author marks
     * @CreateDate: 2025/12/09 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private double method_01(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int idx = -1;
        int left = 0, right = 0;
        int len = m + n;
        int curr = 0, prev = 0;
        while (left < m && right < n) {
            // 判断是否到达中位数
            if (idx == len / 2) {
                if (len % 2 == 0) {
                    return (curr + prev) / 2.0;
                } else {
                    return curr;
                }
            }
            if (len % 2 == 0) {
                // 更新 prev
                prev = curr;
            }
            if (nums1[left] > nums2[right]) {
                curr = nums2[right];
                right++;
            } else {
                curr = nums1[left];
                left++;
            }
            idx++;
        }
        // 如果跳出循环, 则必定是 left == m || right == n, 计算剩余还需要多少个数字
        int remaining = len / 2 - idx;
        // 判断哪一个数组还剩余元素
        while (remaining > 0) {
            if (len % 2 == 0) {
                prev = curr;
            }
            curr = (left >= m ? nums2[right] : nums1[left]);
            left++;
            right++;
            remaining--;
        }
        if (len % 2 == 0) {
            return (curr + prev) / 2.0;
        } else {
            return curr;
        }
    }

}
