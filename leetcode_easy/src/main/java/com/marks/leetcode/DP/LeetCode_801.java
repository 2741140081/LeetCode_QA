package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_801 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/8 16:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_801 {

    /**
     * @Description:
     * 我们有两个长度相等且不为空的整型数组 nums1 和 nums2 。在一次操作中，我们可以交换 nums1[i] 和 nums2[i]的元素。
     *
     * 例如，如果 nums1 = [1,2,3,8] ， nums2 =[5,6,7,4] ，
     * 你可以交换 i = 3 处的元素，得到 nums1 =[1,2,3,4] 和 nums2 =[5,6,7,8] 。
     *
     * 返回 使 nums1 和 nums2 严格递增 所需操作的最小次数 。
     * 数组 arr 严格递增 且  arr[0] < arr[1] < arr[2] < ... < arr[arr.length - 1] 。
     * 注意：
     * 用例保证可以实现操作。
     *
     * tips:
     * 2 <= nums1.length <= 10^5
     * nums2.length == nums1.length
     * 0 <= nums1[i], nums2[i] <= 2 * 10^5
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/04/08 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSwap(int[] nums1, int[] nums2) {
        int result;
        result = method_01(nums1, nums2);
        result = method_02(nums1, nums2);
        return result;
    }

    // 滚动数组优化空间复杂度
    // AC: 5ms/91.82MB
    private int method_02(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] dp = new int[2][2];
        dp[0][0] = 0;
        dp[0][1] = 1;
        int curr = 0, pre;
        for (int i = 1; i < n; i++) {
            boolean f1 = nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1];
            boolean f2 = nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1];
            curr = i % 2;
            pre = 1 - curr;
            if (f1 && f2) {
                // 条件 a / b 均满足时
                dp[curr][0] = Math.min(dp[pre][0], dp[pre][1]);
                dp[curr][1] = Math.min(dp[pre][0], dp[pre][1]) + 1; // 需要 swap + 1
            } else if (f1){
                // 仅满足 a
                dp[curr][0] = dp[pre][0];
                dp[curr][1] = dp[pre][1] + 1;
            } else if (f2) {
                // 仅满足 b
                dp[curr][0] = dp[pre][1];
                dp[curr][1] = dp[pre][0] + 1;
            }
        }
        return Math.min(dp[curr][0], dp[curr][1]);
    }

    /**
     * @Description:
     * 1. 用例保证可以实现操作, 只有两种情况
     *  a. nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]
     *  b. nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]
     * 2. 如果是情况a, dp[i][1] 表示交换当前i, 如果 a 满足 但是 b 不满足时, 则只能从 dp[i - 1][1] 传递, 而不能从 dp[i - 1][0].
     * 因为如果交换 i 之后 判断的是 nums1[i - 1] 与 nums2[i], 以及 nums2[i - 1] 与 nums1[i] 需要严格递增, 但是 条件 b 是不满足的情况,
     * 所以不能从 dp[i - 1][0] 传递.
     * 3. 如果 a 和 b 同时满足时, 则 dp[i][0/1] 可以从 dp[i - 1][0] 和 dp[i - 1][1] 获取, 取两者的较小值即可.
     * 4. 初始化, dp[0][0] = 0, dp[0][1] = 1;
     * AC: 13ms/93.53MB
     * @param: nums1
     * @param: nums2
     * @return int
     * @author marks
     * @CreateDate: 2026/04/08 16:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;
        dp[0][1] = 1;
        for (int i = 1; i < n; i++) {
            boolean f1 = nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1];
            boolean f2 = nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1];
            if (f1 && f2) {
                // 条件 a / b 均满足时
                dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1]);
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1; // 需要 swap + 1
            } else if (f1){
                // 仅满足 a
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] + 1;
            } else if (f2) {
                // 仅满足 b
                dp[i][0] = dp[i - 1][1];
                dp[i][1] = dp[i - 1][0] + 1;
            }
        }
        return Math.min(dp[n - 1][0], dp[n - 1][1]);
    }

}
