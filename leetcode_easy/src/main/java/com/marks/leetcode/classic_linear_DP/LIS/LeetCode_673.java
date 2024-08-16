package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称: LIS 的数量 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/16 14:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_673 {
    /**
     * @Description: [
     * 给定一个未排序的整数数组 nums ， 返回最长递增子序列的个数 。
     *
     * 注意 这个数列必须是 严格 递增的。
     * tips:
     * 1 <= nums.length <= 2000
     * -10^6 <= nums[i] <= 10^6
     * ]
     * @param nums
     * @return int
     * @author marks
     * @CreateDate: 2024/8/16 14:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findNumberOfLIS(int[] nums) {
        int result = 0;
        result = method_01(nums);
        return result;
    }

    /**
     * E1:
     * 输入: [1,3,5,4,7]
     * 输出: 2
     * 解释: 有两个最长递增子序列，分别是 [1, 3, 4, 7] 和[1, 3, 5, 7]。
     *
     * 先按自己理解来，直接动态规划干起来！
     * int[][] dp = new int[n + 1][n + 1]
     * dp[i][j] = value; 解读: i表示前i个数组元素, j表示最大的递增子序列数目, value表示方案数. 感觉定义的还可以
     * 考虑特殊情况
     * i = 0时, j必定为0 所以dp[0][0] = 0
     * i = 1时, j也必定为1, 且dp[1][1] = 1, 假设这个序列是递减序列 那么每一个i， dp[i][1] = 1. OK, 初始化完成！
     * 状态转移方程
     * 对于第i个数 nums[i - 1] = temp
     * 1. nums[i - 1] > nums[0 ~ (i - 2)]
     * dp[i] = Math.max(dp[i], dp[j] + 1)
     * dp[i][j] = dp[i - 1][j - 1]
     * 以上很难推出转态转移方程, 不如先得出LeetCode_300的一维dp[]
     *
     * 以E1为例:
     * nums[] = {1, 3, 5, 4, 7}
     * dp[] = {1, 2, 3, 3, 4}
     *
     * 新增一个count[] 数组来记录方案数
     * 初始化count[] = {1, 1, 1, 1, 1}
     * // 是一个新的方式来达到LIS
     * if(dp[i] == dp[j] + 1)
     * count[i] = count[i] + count[j]
     * // 是在LIS后面添加更长的LIS
     * dp[i] > dp[j] + 1
     * count[i] = count[j]
     * @param nums
     * @return
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        int[] count = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            count[i] = 1;
            // 遍历nums[0 ~ (i - 1)], 查找其中nums[i] > nums[j]的值, 更新dp[i] = Math.max(dp[i], dp[j] + 1)
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[j] + 1 > dp[i]) {
                        dp[i] = Math.max(dp[i], dp[j] + 1);
                        count[i] = count[j];
                    } else if (dp[j] + 1 == dp[i]) {
                        count[i] += count[j];
                    }
                }
            }
        }
        int maxLIS = Arrays.stream(dp).max().getAsInt();
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == maxLIS) {
                res += count[i];
            }
        }
        return res;
    }
}
