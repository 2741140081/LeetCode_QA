package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3938 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/7 16:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3938 {

    /**
     * @Description:
     * 给你一个 m x n 的整数矩阵 grid 。
     * 两个玩家在矩阵中移动：
     * 玩家 1 从左上角单元格 (0, 0) 出发，只能向右或向下移动。他们的目的地是右下角单元格 (m - 1, n - 1) 。
     * 玩家 2 从左下角单元格 (m - 1, 0) 出发，只能向右或向上移动。他们的目的地是右上角单元格 (0, n - 1) 。
     * 每个玩家必须选择一条从各自起始单元格到目的地的有效路径。
     * 如果一个单元格属于 两条 被选中的路径，则称该单元格为 共享 单元格。
     * 返回一个整数，表示所有 共享 单元格的值的 最大 可能总和。
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 2 <= m, n <= 1000
     * 4 <= m * n <= 5 * 10^5
     * -100 <= grid[i][j] <= 100
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/07/07 16:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScore(int[][] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 玩家1和2只会在某一行/某一列存在共享路径, 并且共享路径是一段连续的数组.
     * 2. 现在需要求一个数组 int[] nums 中找到连续的子数组, 并且返回子数组的最大和.
     * 并且子数组为非空, 因为玩家1与2必定存在交集, 即共享路径存在.
     * 3. 可以使用动态规划 dp 来解决子数组求和的最大和.
     * 4. 当 i = 0, 或者 j = 0 或者 i = m - 1, j = n - 1时, 当这些边界单元格是存在与共享路径时,
     * 此时子数组至少需要2个元素而不是1个元素, 非边界时, 子数组只需要1个元素即可.
     * 5. 使用前缀和来计算最大和. int[] prevSum = new int[m + 1]; 当 i = 0 时, j 必须要大于1, 或者当 j = m - 1时, i 必须要小于 m - 2.
     * 即当 i 或者 j 为边界时, 此时至少需要2个元素.
     * 超时: 569/581
     *
     * AC: 43ms/183.32MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/07/07 16:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int maxSum = Integer.MIN_VALUE;

        // 检查每一行的可能共享路径
        for (int i = 0; i < m; i++) {
            // 对于第i行，使用带约束的前缀和方法计算最大连续子数组和
            boolean isBoundary = i == 0 || i == m - 1;
            int currentMax = kadaneAlgorithm(grid[i], isBoundary);
            // 求 0 开始的最大和
            maxSum = Math.max(maxSum, currentMax);
        }

        // 检查每一列的可能共享路径
        for (int j = 0; j < n; j++) {
            // 提取第j列的数据
            int[] colData = new int[m];
            for (int i = 0; i < m; i++) {
                colData[i] = grid[i][j];
            }
            boolean isBoundary = j == 0 || j == n - 1;
            int currentMax = kadaneAlgorithm(colData, isBoundary);
            maxSum = Math.max(maxSum, currentMax);
        }

        return maxSum;
    }


    /**
     * Kadane算法：求一维数组中连续子数组的最大和, 求 [1 ~ n - 2] 直接的最大和
     * @param nums 输入数组
     * @return 最大连续子数组和
     */
    private int kadaneAlgorithm(int[] nums, boolean isBoundary) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();
        int len = nums.length;
        // 处理特殊范围2 的情
        if (len == 2) {
            return nums[0] + nums[1];
        } else if (isBoundary) {
            // 当前子数组最小长度为2, 可以使用 dp[i][j] 来处理, 要求 i 与 j 直接的子数组长度为2
            int[] preSum = new int[len + 1];
            for (int i = 0; i < len; i++) {
                preSum[i + 1] = preSum[i] + nums[i];
            }
            int ans = Integer.MIN_VALUE;
            for (int i = 0; i < len + 1; i++) {
                for (int j = 0; j <= i - 2; j++) {
                    ans = Math.max(ans, preSum[i] - preSum[j]);
                }
            }
            return ans;
        } else {
            int maxCurrent = Math.max(nums[0] + nums[1], nums[1]);
            int maxGlobal = Math.max(nums[0] + nums[1], nums[1]);

            for (int i = 2; i < len - 1; i++) {
                // 要么从当前元素重新开始，要么继续之前的子数组
                maxCurrent = Math.max(nums[i], maxCurrent + nums[i]);
                // 更新全局最大值
                if (maxCurrent > maxGlobal) {
                    maxGlobal = maxCurrent;
                }
            }
            sum = Math.max(maxGlobal, sum);
            // 执行倒序，求 [2 ~ len - 1]
            int maxEnd = Math.max(nums[len - 1] + nums[len - 2], nums[len - 2]);
            int maxEndGlobal = Math.max(nums[len - 1] + nums[len - 2], nums[len - 2]);
            for (int i = len - 3; i > 0; i--) {
                maxEnd = Math.max(nums[i], maxEnd + nums[i]);
                if (maxEnd > maxEndGlobal) {
                    maxEndGlobal = maxEnd;
                }
            }
            sum = Math.max(maxEndGlobal, sum);

            return sum;
        }
    }

}
