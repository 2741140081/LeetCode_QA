package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/8 16:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2540 {
    /**
     * @Description: [
     * 给你两个整数数组 nums1 和 nums2 ，它们已经按非降序排序，请你返回两个数组的 最小公共整数 。如果两个数组 nums1 和 nums2 没有公共整数，请你返回 -1 。
     *
     * 如果一个整数在两个数组中都 至少出现一次 ，那么这个整数是数组 nums1 和 nums2 公共 的。
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 16:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int getCommon(int[] nums1, int[] nums2) {
        int result = 0;
        result = method_01(nums1, nums2);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums1 = [1,2,3,6], nums2 = [2,3,4,5]
     * 输出：2
     * 解释：两个数组中的公共元素是 2 和 3 ，2 是较小值，所以返回 2 。
     * AC:1ms/61.39MB
     * ]
     * @param nums1 
     * @param nums2 
     * @return int
     * @author marks
     * @CreateDate: 2024/11/8 16:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int ans = -1;
        int left = 0;
        int right = 0;

        while (left < n && right < m) {
            if (nums1[left] == nums2[right]) {
                ans = nums1[left];
                break;
            } else if (nums1[left] > nums2[right]) {
                right++;
            } else {
                left++;
            }
        }
        return ans;
    }
}
