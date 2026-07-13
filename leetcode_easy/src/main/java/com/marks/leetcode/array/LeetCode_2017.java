package com.marks.leetcode.array;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2017 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/8 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2017 {


    /**
     * @Description:
     * 给你一个下标从 0 开始的二维数组 grid ，数组大小为 2 x n ，其中 grid[r][c] 表示矩阵中 (r, c) 位置上的点数。
     * 现在有两个机器人正在矩阵上参与一场游戏。
     * 两个机器人初始位置都是 (0, 0) ，目标位置是 (1, n-1) 。
     * 每个机器人只会 向右 ((r, c) 到 (r, c + 1)) 或 向下 ((r, c) 到 (r + 1, c)) 。
     * 游戏开始，第一个 机器人从 (0, 0) 移动到 (1, n-1) ，并收集路径上单元格的全部点数。
     * 对于路径上所有单元格 (r, c) ，途经后 grid[r][c] 会重置为 0 。
     * 然后，第二个 机器人从 (0, 0) 移动到 (1, n-1) ，同样收集路径上单元的全部点数。
     * 注意，它们的路径可能会存在相交的部分。
     * 第一个 机器人想要打击竞争对手，使 第二个 机器人收集到的点数 最小化 。
     * 与此相对，第二个 机器人想要 最大化 自己收集到的点数。
     * 两个机器人都发挥出自己的 最佳水平 的前提下，返回 第二个 机器人收集到的 点数 。
     *
     * tips:
     * grid.length == 2
     * n == grid[r].length
     * 1 <= n <= 5 * 10^4
     * 1 <= grid[r][c] <= 10^5
     * @param: grid
     * @return long
     * @author marks
     * @CreateDate: 2026/07/08 16:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long gridGame(int[][] grid) {
        long result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 机器人1会执行 'Z' 字形的移动, 收集第 0 行前 [0 ~ i] 个元素 和 第 1 行 后 [i ~ n - 1] 个元素, i 的取值范围是 [0 ~ n - 1];
     * 2. 此时机器人2只能收集 第 0 行 [i + 1 ~ n - 1] 的元素 或者 第1行 [0 ~ i - 1] 的元素, 取两者的最大值.
     * 3. 需要4个字段 int sum0, sum1 分别是第 0 行 和第1 行 的元素和, 并且 int prefix 和 suffix 分别是 第0行的前缀和 和 第1行 的后缀和.
     * 4. 然后遍历 i, i 的范围是 0 ~ n - 1; 对于 prefix += nums[i][0]; suffix -= nums[i - 1][1]; (i > 0) 然后用 sum0 - prefix 和 sum1 - suffix 取最大值 max.
     * ans = Math.max(ans, max);
     * 5. 防止数据溢出, 使用 long 替代 int
     * AC: 5ms/93.74MB
     * @param: grid
     * @return long
     * @author marks
     * @CreateDate: 2026/07/08 16:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] grid) {
        int n = grid[0].length;

        long sum0 = 0, sum1 = 0;
        for (int i = 0; i < n; i++) {
            sum0 += grid[0][i];
            sum1 += grid[1][i];
        }

        long prefix = 0;
        long suffix = sum1;
        long ans = Long.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            prefix += grid[0][i];
            if (i > 0) {
                suffix -= grid[1][i - 1];
            }

            long robot2Option1 = sum0 - prefix;
            long robot2Option2 = sum1 - suffix;
            long maxForRobot2 = Math.max(robot2Option1, robot2Option2);

            ans = Math.min(ans, maxForRobot2);
        }

        return ans;
    }

}
