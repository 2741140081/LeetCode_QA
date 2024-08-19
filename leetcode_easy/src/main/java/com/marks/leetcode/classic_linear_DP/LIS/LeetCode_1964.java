package com.marks.leetcode.classic_linear_DP.LIS;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/19 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1964 {
    /**
     * @Description: [
     * 你打算构建一些障碍赛跑路线。给你一个 下标从 0 开始 的整数数组 obstacles ，数组长度为 n ，其中 obstacles[i] 表示第 i 个障碍的高度。
     *
     * 对于每个介于 0 和 n - 1 之间（包含 0 和 n - 1）的下标  i ，在满足下述条件的前提下，请你找出 obstacles 能构成的最长障碍路线的长度：
     *
     * 你可以选择下标介于 0 到 i 之间（包含 0 和 i）的任意个障碍。
     * 在这条路线中，必须包含第 i 个障碍。
     * 你必须按障碍在 obstacles 中的 出现顺序 布置这些障碍。
     * 除第一个障碍外，路线中每个障碍的高度都必须和前一个障碍 相同 或者 更高 。
     * 返回长度为 n 的答案数组 ans ，其中 ans[i] 是上面所述的下标 i 对应的最长障碍赛跑路线的长度。
     *
     * tips:
     * n == obstacles.length
     * 1 <= n <= 10^5
     * 1 <= obstacles[i] <= 10^7
     * ]
     * @param obstacles
     * @return int[]
     * @author marks
     * @CreateDate: 2024/8/19 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
//        int[] res = method_01(obstacles);
        int[] res = method_02(obstacles);
        return res;
    }

    /**
     * @Description: [贪心+二分法
     *
     *
     * ]
     * @param obstacles
     * @return int[]
     * @author marks
     * @CreateDate: 2024/8/19 16:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int[] obstacles) {
        //最长上升子序列
        int[] stack = new int[obstacles.length];//恒定的
        int top = -1;
        int[] res = new int[obstacles.length];
        for (int i = 0; i < obstacles.length; i++) {
            if (top == -1 || obstacles[i] >= stack[top]) {
                stack[++top] = obstacles[i];
                res[i] = top + 1;
            } else {
                //二分
                int l = 0, r = top, m;
                while (l < r) {
                    m = l + (r - l) / 2;
                    if (stack[m] <= obstacles[i]) {
                        l = m + 1;
                    } else {
                        r = m;
                    }
                }
                stack[r] = obstacles[i];
                res[i] = r + 1;
            }
        }
        return res;
    }

    /**
     * @Description: [
     *
     * 条件:
     * 在这条路线中，必须包含第 i 个障碍。
     * 你必须按障碍在 obstacles 中的 出现顺序 布置这些障碍。
     * 路线中每个障碍的高度都必须和前一个障碍 相同 或者 更高 。
     * 返回长度为 n 的答案数组 ans ，其中 ans[i] 是上面所述的下标 i 对应的最长障碍赛跑路线的长度
     * E1:
     * 输入：obstacles = [1,2,3,2]
     * 输出：[1,2,3,3]
     * 解释：每个位置的最长有效障碍路线是：
     * - i = 0: [1], [1] 长度为 1
     * - i = 1: [1,2], [1,2] 长度为 2
     * - i = 2: [1,2,3], [1,2,3] 长度为 3
     * - i = 3: [1,2,3,2], [1,2,2] 长度为 3
     *
     * 也就是返回ans[] 存放对应 i 的最大长度(收到限制, 必须要包含第 i 个obstacles[i])
     * 1 <= n <= 10^5
     * 暴力大概率超时
     * method_01不负众望果然超时
     * ]
     * @param obstacles
     * @return int[]
     * @author marks
     * @CreateDate: 2024/8/19 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[] obstacles) {
        int n = obstacles.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (obstacles[i] >= obstacles[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        return dp;
    }
}
