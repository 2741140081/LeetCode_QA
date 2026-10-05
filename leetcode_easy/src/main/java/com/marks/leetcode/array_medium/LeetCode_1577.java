package com.marks.leetcode.array_medium;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1577 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/22 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1577 {

    /**
     * @Description:
     * 给你两个整数数组 nums1 和 nums2 ，请你返回根据以下规则形成的三元组的数目（类型 1 和类型 2 ）：
     * 类型 1：三元组 (i, j, k) ，如果 nums1[i]^2 == nums2[j] * nums2[k] 其中 0 <= i < nums1.length 且 0 <= j < k < nums2.length
     * 类型 2：三元组 (i, j, k) ，如果 nums2[i]^2 == nums1[j] * nums1[k] 其中 0 <= i < nums2.length 且 0 <= j < k < nums1.length
     * tips:
     * 1 <= nums1.length, nums2.length <= 1000
     * 1 <= nums1[i], nums2[i] <= 10^5
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numTriplets(int[] nums1, int[] nums2) {
        int result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description:
     * 1. 使用 map 记录 nums1 中任意两个不同元素之间的乘积，key 为乘积，value 为乘积出现的次数，
     * 由于要保证 j < k 所以采用双重for 循环进行遍历, 可以分别得到 map1 和 map2 分别记录 nums1 和 nums2 中任意两个不同元素之间的乘积
     * AC: 77ms/59.32MB
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/09/22 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        Map<Long, Integer> map1 = new HashMap<>();
        Map<Long, Integer> map2 = new HashMap<>();
        int m = nums1.length, n = nums2.length;
        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                long product = (long)nums1[i] * nums1[j];
                map1.merge(product, 1, Integer::sum);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                long product = (long)nums2[i] * nums2[j];
                map2.merge(product, 1, Integer::sum);
            }
        }
        int ans = 0;
        for (int num : nums1) {
            long square = (long)num * num;
            ans += map2.getOrDefault(square, 0);
        }
        for (int num : nums2) {
            long square = (long)num * num;
            ans += map1.getOrDefault(square, 0);
        }

        return ans;
    }

}
