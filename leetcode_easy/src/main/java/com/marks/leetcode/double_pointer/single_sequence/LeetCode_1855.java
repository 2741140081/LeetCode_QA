package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/11 16:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1855 {
    /**
     * @Description: [
     * 给你两个 非递增 的整数数组 nums1 和 nums2 ，数组下标均 从 0 开始 计数。
     *
     * 下标对 (i, j) 中 0 <= i < nums1.length 且 0 <= j < nums2.length 。如果该下标对同时满足 i <= j 且 nums1[i] <= nums2[j] ，则称之为 有效 下标对，该下标对的 距离 为 j - i​​ 。​​
     *
     * 返回所有 有效 下标对 (i, j) 中的 最大距离 。如果不存在有效下标对，返回 0 。
     *
     * 一个数组 arr ，如果每个 1 <= i < arr.length 均有 arr[i-1] >= arr[i] 成立，那么该数组是一个 非递增 数组。
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/11/11 16:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int maxDistance(int[] nums1, int[] nums2) {
        int result;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description: [
     * 输入：nums1 = [55,30,5,4,2], nums2 = [100,20,10,10,5]
     * 输出：2
     * 解释：有效下标对是 (0,0), (2,2), (2,3), (2,4), (3,3), (3,4) 和 (4,4) 。
     * 最大距离是 2 ，对应下标对 (2,4) 。
     *
     * AC:3ms/56.36MB
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/11/11 16:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int left = 0;
        int right = 0;
        int ans = 0;

        while (left < m && right < n) {
            if (nums1[left] <= nums2[right]) {
                ans = Math.max(ans, right - left);
                right++;
            }else {
                left++;
                right++;
            }
        }
        return ans;
    }
}
