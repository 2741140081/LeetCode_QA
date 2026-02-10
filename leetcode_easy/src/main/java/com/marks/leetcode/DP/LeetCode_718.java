package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_718 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/10 17:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_718 {

    /**
     * @Description:
     * 给两个整数数组 nums1 和 nums2 ，返回 两个数组中 公共的 、长度最长的子数组的长度 。
     * tips:
     * 1 <= nums1.length, nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 100
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int findLength(int[] nums1, int[] nums2) {
        int result;
        result = method_01(nums1, nums2);
        return result;
    }


    /**
     * @Description:
     * E1:
     * 输入：nums1 = [1,2,3,2,1], nums2 = [3,2,1,4,7]
     * 输出：3
     * 解释：长度最长的公共子数组是 [3,2,1] 。
     * 1. 暴力破解，两层循环，判断长度
     * AC: 26ms/65.11MB
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/02/10 17:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[m + 1][n + 1];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (nums1[i] == nums2[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                }
                ans = Math.max(ans, dp[i + 1][j + 1]);
            }
        }
        return ans;
    }

}
