package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_16_17 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/9 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_Interview_16_17 {

    /**
     * @Description:
     * 给定一个整数数组，找出总和最大的连续数列，并返回总和。
     *
     * 示例：
     *
     * 输入： [-2,1,-3,4,-1,2,1,-5,4]
     * 输出： 6
     * 解释： 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * 进阶：
     *
     * 如果你已经实现复杂度为 O(n) 的解法，尝试使用更为精妙的分治法求解。
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSubArray(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * dp[i] = max(dp[i-1] + nums[i], nums[i])
     * AC: 1ms/46.7MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 16:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int ans = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    /**
     * @Description:
     * 查看官方题解的分治法题解
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/02/09 16:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[] nums) {
        return getInfo(nums, 0, nums.length - 1).mSum;
    }

    public class Status {
        public int lSum, rSum, mSum, iSum;

        /**
         lSum 表示 [l,r] 内以 l 为左端点的最大子段和
         rSum 表示 [l,r] 内以 r 为右端点的最大子段和
         mSum 表示 [l,r] 内的最大子段和
         iSum 表示 [l,r] 的区间和
         */
        public Status(int lSum, int rSum, int mSum, int iSum) {
            this.lSum = lSum;
            this.rSum = rSum;
            this.mSum = mSum;
            this.iSum = iSum;
        }
    }

    public Status getInfo(int[] a, int l, int r) {
        if (l == r) {
            return new Status(a[l], a[l], a[l], a[l]);
        }
        int m = (l + r) >> 1;
        Status lSub = getInfo(a, l, m);
        Status rSub = getInfo(a, m + 1, r);
        return pushUp(lSub, rSub);
    }

    public Status pushUp(Status l, Status r) {
        int iSum = l.iSum + r.iSum;
        int lSum = Math.max(l.lSum, l.iSum + r.lSum);
        int rSum = Math.max(r.rSum, r.iSum + l.rSum);
        int mSum = Math.max(Math.max(l.mSum, r.mSum), l.rSum + r.lSum);
        return new Status(lSum, rSum, mSum, iSum);
    }

}
