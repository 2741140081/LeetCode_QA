package com.marks.leetcode.all_str;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_864 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/9 11:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_864 {

    /**
     * @Description:
     * 给定一个二维网格 grid ，其中：
     * '.' 代表一个空房间
     * '#' 代表一堵墙
     * '@' 是起点
     * 小写字母代表钥匙
     * 大写字母代表锁
     * 我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间。
     * 我们不能在网格外面行走，也无法穿过一堵墙。如果途经一个钥匙，我们就把它捡起来。除非我们手里有对应的钥匙，否则无法通过锁。
     * 假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，字母表中的前 k 个字母在网格中都有自己对应的一个小写和一个大写字母。
     * 换言之，每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁。另外，代表钥匙和锁的字母互为大小写并按字母顺序排列。
     * 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
     *
     * tips:
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 30
     * grid[i][j] 只含有 '.', '#', '@', 'a'-'f' 以及 'A'-'F'
     * 钥匙的数目范围是 [1, 6]
     * 每个钥匙都对应一个 不同 的字母
     * 每个钥匙正好打开一个对应的锁
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/09/09 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int shortestPathAllKeys(String[] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 压缩钥匙的状态, 先遍历整个网络, 统计钥匙的数量 cnt, 状态为 2^cnt, mask = (1 << cnt) - 1
     * 2. 使用动态规划 或者说记忆化 来存储每个房间的状态 dp[i][j][mask]
     * 3. 使用 Dijkstra 算法来找到最短路径, 使用广度优先搜索 BFS
     * AC: 18ms/46.73MB
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/09/09 11:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    @SuppressWarnings("all")
    private int method_01(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        int cnt = 0;
        int sx = -1, sy = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'f') {
                    cnt++;
                } else if (grid[i].charAt(j) == '@') { // 起始点
                    sx = i;
                    sy = j;
                }
            }
        }
        int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int[][][] dp = new int[m][n][1 << cnt];
        // 初始化 dp 数组, -1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        // 创建队列
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {sx, sy, 0}); // {i, j, mask}
        dp[sx][sy][0] = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] curr = queue.poll();
                int cx = curr[0], cy = curr[1];
                int mask = curr[2];
                if (mask == (1 << cnt) - 1) {
                    return dp[cx][cy][mask];
                }

                for (int[] dir : dirs) {
                    int nx = cx + dir[0], ny = cy + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && grid[nx].charAt(ny) != '#') {
                        if (grid[nx].charAt(ny) >= 'A' && grid[nx].charAt(ny) <= 'F') {
                            // 判断如果下一个房间是锁，那么需要判断是否-have-key
                            if ((mask & (1 << (grid[nx].charAt(ny) - 'A'))) != 0) {
                                if (dp[nx][ny][mask] == -1 || dp[nx][ny][mask] > dp[cx][cy][mask] + 1) {
                                    dp[nx][ny][mask] = dp[cx][cy][mask] + 1;
                                    queue.offer(new int[] {nx, ny, mask});
                                }
                            }
                        } else if (grid[nx].charAt(ny) >= 'a' && grid[nx].charAt(ny) <= 'f') {
                            // 下一个房间是钥匙
                            int nmask = mask | (1 << (grid[nx].charAt(ny) - 'a'));
                            if (dp[nx][ny][nmask] == -1 || dp[nx][ny][nmask] > dp[cx][cy][mask] + 1) {
                                dp[nx][ny][nmask] = dp[cx][cy][mask] + 1;
                                queue.offer(new int[] {nx, ny, nmask});
                            }
                        } else {
                            // 下一个房间是空房间或者起始点
                            if (dp[nx][ny][mask] == -1 || dp[nx][ny][mask] > dp[cx][cy][mask] + 1) {
                                dp[nx][ny][mask] = dp[cx][cy][mask] + 1;
                                queue.offer(new int[] {nx, ny, mask});
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }

}
