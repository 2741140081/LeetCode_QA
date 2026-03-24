package com.marks.leetcode.DP;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_764 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 11:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_764 {

    /**
     * @Description:
     * 在一个 n x n 的矩阵 grid 中，除了在数组 mines 中给出的元素为 0，其他每个元素都为 1。mines[i] = [xi, yi]表示 grid[xi][yi] == 0
     * 返回  grid 中包含 1 的最大的 轴对齐 加号标志的阶数 。如果未找到加号标志，则返回 0 。
     * 一个 k 阶由 1 组成的 “轴对称”加号标志 具有中心网格 grid[r][c] == 1 ，以及4个从中心向上、向下、向左、向右延伸，长度为 k-1，由 1 组成的臂。
     * 注意，只有加号标志的所有网格要求为 1 ，别的网格可能为 0 也可能为 1 。
     * tips:
     * 1 <= n <= 500
     * 1 <= mines.length <= 5000
     * 0 <= xi, yi < n
     * 每一对 (xi, yi) 都 不重复
     * @param: n
     * @param: mines
     * @return int
     * @author marks
     * @CreateDate: 2026/03/24 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int orderOfLargestPlusSign(int n, int[][] mines) {
        int result;
        result = method_01(n, mines);
        return result;
    }

    /**
     * @Description:
     * 1. 如果 grid[i][j] = 1, 需要通过得到周围4个相邻点的最短臂长, ans = Math.max(ans, 1 + dp[i][j])
     * 2. 为每一行/每一列都执行前缀和和后缀和, 对于 grid[i][j] = 1时, 获取 此时行的前缀和 pref[i][j]
     * 3. 边缘位置的臂长是1, 如果grid[i][j] = 1; 边缘最初臂长为1
     * 4. 前缀和应该使用dp 的方式进行计算, 即如果遇到grid[i][j] = 0, 则 dp[i][j] = 0;
     * 5. 需要准备5个二维数组
     * AC: 39ms/66.24MB
     * @param: n
     * @param: mines
     * @return int
     * @author marks
     * @CreateDate: 2026/03/24 11:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] mines) {
        int[][] grid = new int[n][n]; // 原始矩阵
        int[][] left = new int[n][n]; // 从左到右的臂长
        int[][] right = new int[n][n]; // 从右到左的臂长
        int[][] up = new int[n][n]; // 从上到下的臂长
        int[][] down = new int[n][n]; // 从下到上的臂长
        for (int i = 0; i < n; i++) {
            Arrays.fill(grid[i], 1);
        }
        for (int[] mine : mines) {
            grid[mine[0]][mine[1]] = 0;
        }
        // 遍历每一行, 处理 left 和 right,
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    left[i][j] = j == 0 ? 1 : left[i][j - 1] + 1;
                }
            }
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] != 0) {
                    right[i][j] = j == n - 1 ? 1 : right[i][j + 1] + 1;
                }
            }
        }
        // 遍历每一列, 处理 up 和 down
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[j][i] != 0) {
                    up[j][i] = j == 0 ? 1 : up[j - 1][i] + 1;
                }
            }
            for (int j = n - 1; j >= 0; j--) {
                if (grid[j][i] != 0) {
                    down[j][i] = j == n - 1 ? 1 : down[j + 1][i] + 1;
                }
            }
        }
        // 遍历整个grid[][], 取最大臂长
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int length = Math.min(Math.min(left[i][j], right[i][j]), Math.min(up[i][j], down[i][j]));
                ans = Math.max(ans, length);
            }
        }
        return ans;
    }

}
