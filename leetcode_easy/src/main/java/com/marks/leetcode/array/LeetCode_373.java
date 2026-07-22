package com.marks.leetcode.array;

import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_373 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/22 16:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_373 {

    /**
     * @Description:
     * 给定两个以 非递减顺序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。
     * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。
     * 请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。
     *
     * tips:
     * 1 <= nums1.length, nums2.length <= 10^5
     * -10^9 <= nums1[i], nums2[i] <= 10^9
     * nums1 和 nums2 均为 升序排列
     * 1 <= k <= 10^4
     * k <= nums1.length * nums2.length
     * @param: nums1
     * @param: nums2
     * @param: k
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/07/22 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<List<Integer>> result;
        result = method_01(nums1, nums2, k);
        return result;
    }

    /**
     * @Description:
     * E1: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
     * 1.
     * @param: nums1
     * @param: nums2
     * @param: k
     * @return java.util.List<java.util.List<java.lang.Integer>>
     * @author marks
     * @CreateDate: 2026/07/22 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<List<Integer>> method_01(int[] nums1, int[] nums2, int k) {

        return null;
    }

}
