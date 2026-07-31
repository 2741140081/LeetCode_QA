package com.marks.leetcode.array_hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1799 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/31 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1799 {

    /**
     * @Description:
     * 给你 nums ，它是一个大小为 2 * n 的正整数数组。你必须对这个数组执行 n 次操作。
     * 在第 i 次操作时（操作编号从 1 开始），你需要：
     * 选择两个元素 x 和 y 。
     * 获得分数 i * gcd(x, y) 。
     * 将 x 和 y 从 nums 中删除。
     * 请你返回 n 次操作后你能获得的分数和最大为多少。
     * 函数 gcd(x, y) 是 x 和 y 的最大公约数。
     *
     * tips:
     * 1 <= n <= 7
     * nums.length == 2 * n
     * 1 <= nums[i] <= 10^6
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/31 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScore(int[] nums) {
        int result;
        result = method_01(nums);
        return result;
    }

    /**
     * @Description:
     * 1. 感觉又是状态压缩 + 动态规划
     * 2. 仿照之前的 1681 分组题, 来处理当前这一题, 预处理所有 int[][] gcd 值 prev[i][j] = prev[j][i] = gcd(nums[i], nums[j]), (i != j)
     * AC: 446ms/45.95MB
     * @param: nums
     * @return int
     * @author marks
     * @CreateDate: 2026/07/31 16:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] nums) {
        int n = nums.length;
        // 预处理 gcd(x, y)
        HashMap<Integer, Integer> values = new HashMap<>();
        // 外层循环 i 从 0 到 n-2
        for (int i = 0; i < n - 1; i++) {
            // 内层循环 j 从 i+1 到 n-1，确保不重复且 j > i
            for (int j = i + 1; j < n; j++) {
                // 直接通过位运算构造 mask，第 i 位和第 j 位置 1
                int mask = (1 << i) | (1 << j);
                // 计算 nums[i] 和 nums[j] 的最大公约数并存入 map
                values.put(mask, gcd(nums[i], nums[j]));
            }
        }
        // 执行动态规划
        int[] dp = new int[1 << n];
        int INF = Integer.MIN_VALUE / 2;
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            // 跳过无效 mask
            if (dp[mask] == INF) {
                continue;
            }

            int sub = (~mask) & ((1 << n) - 1);
            if (Integer.bitCount(sub) < 2) {
                continue;
            }

            // 下一个子集
            int nxt = sub;
            while (nxt > 0) {
                int cnt = Integer.bitCount(mask) / 2; // 计算此时 mask 已经处理了多少对数字
                // 是否是有效分组
                if (values.containsKey(nxt)) {
                    dp[mask | nxt] = Math.max(dp[mask | nxt], dp[mask] + values.get(nxt) * (cnt + 1));
                }
                nxt = (nxt - 1) & sub; // 枚举 sub 的所有非空子集
            }
        }
        return dp[(1 << n) - 1];
    }

    private int gcd(int nums1, int nums2) {
        if (nums1 == 0) {
            return nums2;
        }
        return gcd(nums2 % nums1, nums1);
    }

}
