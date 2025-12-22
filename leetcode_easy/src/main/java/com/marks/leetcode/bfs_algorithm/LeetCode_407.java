package com.marks.leetcode.bfs_algorithm;

import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_407 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/22 11:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_407 {

    /**
     * @Description:
     * 给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
     * tips:
     * m == heightMap.length
     * n == heightMap[i].length
     * 1 <= m, n <= 200
     * 0 <= heightMap[i][j] <= 2 * 10^4
     * @param: heightMap
     * @return int
     * @author marks
     * @CreateDate: 2025/12/22 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int trapRainWater(int[][] heightMap) {
        int result;
        result = method_01(heightMap);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入: heightMap = [
     * [1,4,3,1,3,2],
     * [3,2,1,3,2,4],
     * [2,3,3,2,3,1]]
     * 输出: 4
     * 1. 完全没有思路, 看看官方的解法
     * 2. 首先需要明确, 如何才能接到雨水(保留雨水), 点(x, y), x,y 不为最外层节点, 并且(x,y) 的相邻节点值都要大于(x,y)
     * 3. 从最外层开始, 找到最外层的最小节点, 应为这可能是最低的, 因为木桶原理, 能接到多少水取决于最小的木板,
     * 需要用优先队列存储最外层的最小值,
     * 4. 需要不断更新最外层节点
     * AC: 19ms/46.99MB
     * @param: heightMap
     * @return int
     * @author marks
     * @CreateDate: 2025/12/22 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] heightMap) {
        int m = heightMap.length;
        int n = heightMap[0].length;
        if (m < 3 || n < 3) {
            // 当矩阵只有两行或者两列时, 无法接雨水, 返回0
            return 0;
        }
        // 创建优先队列存储最外层节点及其值
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        boolean[][] visited = new boolean[m][n]; // 创建一个二维数组, 用于记录节点是否被访问过
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == m - 1 || j == 0 || j == n - 1) {
                    queue.offer(new int[]{i, j, heightMap[i][j]});
                    visited[i][j] = true;
                }
            }
        }
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int ans = 0;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int[] dir : dirs) {
                int x = cur[0] + dir[0];
                int y = cur[1] + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && !visited[x][y]) {
                    if (heightMap[x][y] < cur[2]) {
                        ans += cur[2] - heightMap[x][y];
                    }
                    queue.offer(new int[]{x, y, Math.max(heightMap[x][y], Math.max(heightMap[x][y] ,cur[2]))}); // 更新最外层的节点值
                    visited[x][y] = true;
                }
            }
        }

        return ans;
    }

}
