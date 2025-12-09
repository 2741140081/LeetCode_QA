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
     * 1. 开始分析, 如何在一个数组中找到中位数? 中位数是数组中间的数, 如果数组长度为偶数, 中位数是两个中间数之和除以2; int n = nums.length;
     * 2. n & 1 == 0, int index = n / 2; double ans = ((double) nums[index] + nums[index - 1]) / 2; n & 1 ==1, dobule ans = nums[index];
     * 3. 开始解决当前m + n 的问题, int m_mid = m / 2; int n_mid = n / 2;
     * 3.1 nums1[m_mid] > nums2[n_mid], nums1[m_mid] < nums2[n_mid], nums1[m_mid] == nums2[n_mid]
     * need todo
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

        return 0;
    }

}
