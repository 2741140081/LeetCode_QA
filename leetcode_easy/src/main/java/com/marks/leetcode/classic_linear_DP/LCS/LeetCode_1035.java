package com.marks.leetcode.classic_linear_DP.LCS;

/**
 * <p>项目名称: 不相交的线 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/12 11:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1035 {
    /**
     * @Description: [
     * 在两条独立的水平线上按给定的顺序写下 nums1 和 nums2 中的整数。
     *
     * 现在，可以绘制一些连接两个数字 nums1[i] 和 nums2[j] 的直线，这些直线需要同时满足：
     *
     *  nums1[i] == nums2[j]
     * 且绘制的直线不与任何其他连线（非水平线）相交。
     * 请注意，连线即使在端点也不能相交：每个数字只能属于一条连线。
     *
     * 以这种方法绘制线条，并返回可以绘制的最大连线数。
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/12 11:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int result = 0;
//        result = method_01(nums1, nums2);
        result = method_02(nums1, nums2);
        return result;
    }

    /**
     * @Description: [
     * 基于Method_01, 使用一维数组优化
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/12 14:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[j] = dp[j - 1] + 1;
                }else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }
        return dp[n];
    }

    /**
     * @Description: [
     * E1:
     * 输入：nums1 = [1,4,2], nums2 = [1,2,4]
     * 输出：2
     * 解释：可以画出两条不交叉的线，如上图所示。
     * 但无法画出第三条不相交的直线，因为从 nums1[1]=4 到 nums2[2]=4 的直线将与从 nums1[2]=2 到 nums2[1]=2 的直线相交。
     *
     * ]
     * @param nums1
     * @param nums2
     * @return int
     * @author marks
     * @CreateDate: 2024/8/12 11:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[][] dp = new int[2][n + 1];
        // 初始化 dp[0][j] = 0; or dp[i][0] = 0;
        int curr = 0;
        for (int i = 1; i <= m; i++) {
            curr = i % 2;
            int pre = 1 - curr;
            for (int j = 1; j <= n; j++) {
                dp[curr][j] = 0;
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[curr][j] = dp[pre][j - 1] + 1;
                }else {
                    dp[curr][j] = Math.max(dp[pre][j], dp[curr][j - 1]);
                }
            }
        }
        return dp[curr][n];
    }
}
