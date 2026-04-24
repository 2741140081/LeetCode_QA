package com.marks.leetcode.DP_II;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3538 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/22 16:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3538 {

    /**
     * @Description:
     * 给你一个长度为 l 公里的直路，一个整数 n，一个整数 k 和 两个 长度为 n 的整数数组 position 和 time 。
     * 数组 position 列出了路标的位置（单位：公里），并且是 严格 升序排列的（其中 position[0] = 0 且 position[n - 1] = l）。
     * 每个 time[i] 表示从 position[i] 到 position[i + 1] 之间行驶 1 公里所需的时间（单位：分钟）。
     * 你 必须 执行 恰好 k 次合并操作。在一次合并中，你可以选择两个相邻的路标，下标为 i 和 i + 1（其中 i > 0 且 i + 1 < n），并且：
     * 更新索引为 i + 1 的路标，使其时间变为 time[i] + time[i + 1]。
     * 删除索引为 i 的路标。
     * 返回经过 恰好 k 次合并后从 0 到 l 的 最小总旅行时间（单位：分钟）。
     *
     * tips:
     * 1 <= l <= 105
     * 2 <= n <= min(l + 1, 50)
     * 0 <= k <= min(n - 2, 10)
     * position.length == n
     * position[0] = 0 和 position[n - 1] = l
     * position 是严格升序排列的。
     * time.length == n
     * 1 <= time[i] <= 100
     * 1 <= sum(time) <= 100
     * @param: l
     * @param: n
     * @param: k
     * @param: position
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2026/04/22 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTravelTime(int l, int n, int k, int[] position, int[] time) {
        int result;
        result = method_01(l, n, k, position, time);
        return result;
    }

    /**
     * @Description:
     * 1. 考虑动态规划
     * 2. 合并操作, 假设对下标 i 进行合并(i > 0), 改变的时间是, 距离 d1 = position[i + 1] - position[i], 时间现在使用的是 time[i - 1],
     * 并且会增加时间, d2 = position[i + 2] - position[i + 1]; i + 1 < n - 1时, 如果等于 n - 1, 则没有增加时间, t = time[i]
     * 3. 比较两者大小 s1 = (time[i - 1] - time[i]) * d1; s1 可能减少或者增加(需要的是减少的), s2 = time[i] * d2; 当 i < n - 2时成立,
     * 当 i = n - 2时, s2 = 0
     * 4. dp[i][j], 表示 前 i 个坐标, 合并了 j 次, 最小总旅行时间
     * 5. 对于当前下标 i, 如果选择合并, dp[i][j] = dp[i - 1][j - 1] + s1 + (i == n - 2 ? 0 : s2);
     * 选择不合并, dp[i][j] = dp[i - 1][j] + time[i] * (position[i + 1] - position[i]);  i < n - 1
     * 6. 找到问题了, 当前合并操作是基于前一次合并操作的基础上得到的, 需要再添加一个字段, dp[i][j][x], 表示前 i 个坐标, 前一次从 j 合并到 i,
     * 并且合并了 x 次
     * 7. 转移方程: 对于 下标 i, 如果不进行合并操作 dp[i][i][x] = dp[i - 1][1 ~ i - 1][x] + time[i] * (position[i + 1] - position[i]),
     * 其中 j 属于[Math.max(1, i - 1 - x) ~ i - 1], 将 j 定义为 从 下标 j 开始连续合并到 下标 i, 合并次数为 i - j 次
     * 8. 对于下标 i, 执行合并操作, 最大连续合并次数 Math.min(i - 1, k) dp[i][j][x] = dp[i - j - 1][m][k - j], 然后需要对 time 进行前缀和操作,
     * 我当前进行的是一个连续合并, 这就证明在 i - j - 1 的下标处, 这个下标是没有进行合并的, 否则连续合并次数必定 + 1, 所以 i - j - 1 是未合并,
     * 且此时已经的合并次数是 k - x 次, 就可以得到转移方程
     * dp[i][j][x] = dp[i - j - 1][0][x - j] + time[i - j - 1] * (position[i + 1] - position[i - j - 1]) + (prefixSum[i + 1] - prefixSum[i - j - 1]) * (position[i + 1] - position[i - j - 1])
     * 9. 重新定义数组 int[][][] dp = new int[n][k + 1][k + 1]; i 表示前 i 个坐标, j 表示已经合并的操作次数, x 表示当前 i 能执行的最大合并次数
     * 10. 现在的主要问题是, 我代码中没有定义清楚, x 表示最大连续合并次数, 但是 i 位置是否进行合并还是说合并到 i 位置处终止, 也就是x 是否包含[i, i + 1] 下标之间的合并操作
     * 11. 修改当前代码, [i, i + 1] 不参与合并操作,
     * @param: l
     * @param: n
     * @param: k
     * @param: position
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2026/04/22 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int l, int n, int k, int[] position, int[] time) {
        int[] prefixSum = new int[n]; // time 的前缀和
        prefixSum[0] = time[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + time[i];
        }
        int[][][] dp = new int[n][k + 1][k + 1];
        // 初始化dp
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE / 2);
            }
        }
        // 处理 i = 0 的情况
        dp[0][0][0] = 0;
        // 执行动态规划
        for (int i = 1; i < n; i++) {
            // 先循环下标 i 处的已执行的合并次数
            for (int j = 0; j <= Math.min(i - 1, k); j++) {
                // 在下标 i 是能够合并的最大合并次数
                for (int x = 0; x <= j; x++) {
                    if (x == 0) {
                        // 不进行合并操作, 需要考虑 i - 1 的合并操作
                        for (int m = 0; m <= j; m++) {
                            int sumTime = prefixSum[i - 1] - prefixSum[i - 1 - m];
                            dp[i][j][0] = Math.min(dp[i][j][0], dp[i - 1][j][m] + (sumTime + time[i]) * (position[i] - position[i - 1]));
                        }
                    } else {
                        // 有合并操作, 且 prev下标处的合并数为 0
                        int prev = i - x - 1;
                        // 合并的距离
                        int distance = position[i] - position[prev];
                        dp[i][j][x] = Math.min(dp[i][j][x], dp[prev][j - x][0] + time[prev] * distance);
                    }
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i <= k; i++) {
            ans = Math.min(ans, dp[n - 1][i][k]);
        }

        return ans;
    }

}
