package com.marks.leetcode.daily_question;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/14 17:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3152 {
    /**
     * @Description: [
     * 如果数组的每一对相邻元素都是两个奇偶性不同的数字，则该数组被认为是一个 特殊数组 。
     *
     * 你有一个整数数组 nums 和一个二维整数矩阵 queries，对于 queries[i] = [fromi, toi]，请你帮助你检查
     * 子数组
     *  nums[fromi..toi] 是不是一个 特殊数组 。
     *
     * 返回布尔数组 answer，如果 nums[fromi..toi] 是特殊数组，则 answer[i] 为 true ，否则，answer[i] 为 false 。
     *
     * tips:
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * 0 <= queries[i][0] <= queries[i][1] <= nums.length - 1
     * ]
     * @param nums
     * @param queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2024/8/14 17:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        boolean[] res = method_01(nums, queries);
        return res;
    }

    private boolean[] method_01(int[] nums, int[][] queries) {
        int m = nums.length;
        int n = queries.length;
        int[] dp = new int[m];
        Arrays.fill(dp, 1);
        boolean[] res = new boolean[n];
        for (int i = 1; i < m; i++) {
            //相当于 nums[i] % 2 != nums[i - 1] % 2
            if (((nums[i] ^ nums[i - 1]) & 1) == 1) {
                dp[i] = dp[i - 1] + 1;
            }
        }
        for (int i = 0; i < n; i++) {
            int from = queries[i][0];
            int to = queries[i][1];
            if (dp[to] >= to - from + 1) {
                res[i] = true;
            }
        }
        return res;
    }
}
