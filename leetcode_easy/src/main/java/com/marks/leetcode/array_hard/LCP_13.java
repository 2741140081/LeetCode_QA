package com.marks.leetcode.array_hard;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_13 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/9 15:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_13 {

    /**
     * @Description:
     * 我们得到了一副藏宝图，藏宝图显示，在一个迷宫中存在着未被世人发现的宝藏。
     * 迷宫是一个二维矩阵，用一个字符串数组表示。它标识了唯一的入口（用 'S' 表示），和唯一的宝藏地点（用 'T' 表示）。
     * 但是，宝藏被一些隐蔽的机关保护了起来。在地图上有若干个机关点（用 'M' 表示），只有所有机关均被触发，才可以拿到宝藏。
     * 要保持机关的触发，需要把一个重石放在上面。迷宫中有若干个石堆（用 'O' 表示），每个石堆都有无限个足够触发机关的重石。
     * 但是由于石头太重，我们一次只能搬一个石头到指定地点。
     * 迷宫中同样有一些墙壁（用 '#' 表示），我们不能走入墙壁。剩余的都是可随意通行的点（用 '.' 表示）。
     * 石堆、机关、起点和终点（无论是否能拿到宝藏）也是可以通行的。
     * 我们每步可以选择向上/向下/向左/向右移动一格，并且不能移出迷宫。搬起石头和放下石头不算步数。
     * 那么，从起点开始，我们最少需要多少步才能最后拿到宝藏呢？如果无法拿到宝藏，返回 -1 。
     *
     * tips:
     * 1 <= maze.length <= 100
     * 1 <= maze[i].length <= 100
     * maze[i].length == maze[j].length
     * S 和 T 有且只有一个
     * 0 <= M的数量 <= 16
     * 0 <= O的数量 <= 40，题目保证当迷宫中存在 M 时，一定存在至少一个 O 。
     * @param: maze
     * @return int
     * @author marks
     * @CreateDate: 2026/09/09 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimalSteps(String[] maze) {
        int result;
        result = method_01(maze);
        result = method_02(maze);
        return result;
    }

    int[] dx = {1, -1, 0, 0};
    int[] dy = {0, 0, 1, -1};
    int n, m;

    private int method_02(String[] maze) {
        n = maze.length;
        m = maze[0].length();
        // 机关 & 石头
        List<int[]> buttons = new ArrayList<int[]>();
        List<int[]> stones = new ArrayList<int[]>();
        // 起点 & 终点
        int sx = -1, sy = -1, tx = -1, ty = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (maze[i].charAt(j) == 'M') {
                    buttons.add(new int[]{i, j});
                }
                if (maze[i].charAt(j) == 'O') {
                    stones.add(new int[]{i, j});
                }
                if (maze[i].charAt(j) == 'S') {
                    sx = i;
                    sy = j;
                }
                if (maze[i].charAt(j) == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        }
        int nb = buttons.size();
        int ns = stones.size();
        int[][] startDist = bfs(sx, sy, maze);

        // 边界情况：没有机关
        if (nb == 0) {
            return startDist[tx][ty];
        }
        // 从某个机关到其他机关 / 起点与终点的最短距离。
        int[][] dist = new int[nb][nb + 2];
        for (int i = 0; i < nb; i++) {
            Arrays.fill(dist[i], -1);
        }
        // 中间结果
        int[][][] dd = new int[nb][][];
        for (int i = 0; i < nb; i++) {
            int[][] d = bfs(buttons.get(i)[0], buttons.get(i)[1], maze);
            dd[i] = d;
            // 从某个点到终点不需要拿石头
            dist[i][nb + 1] = d[tx][ty];
        }

        for (int i = 0; i < nb; i++) {
            int tmp = -1;
            for (int k = 0; k < ns; k++) {
                int midX = stones.get(k)[0], midY = stones.get(k)[1];
                if (dd[i][midX][midY] != -1 && startDist[midX][midY] != -1) {
                    if (tmp == -1 || tmp > dd[i][midX][midY] + startDist[midX][midY]) {
                        tmp = dd[i][midX][midY] + startDist[midX][midY];
                    }
                }
            }
            dist[i][nb] = tmp;
            for (int j = i + 1; j < nb; j++) {
                int mn = -1;
                for (int k = 0; k < ns; k++) {
                    int midX = stones.get(k)[0], midY = stones.get(k)[1];
                    if (dd[i][midX][midY] != -1 && dd[j][midX][midY] != -1) {
                        if (mn == -1 || mn > dd[i][midX][midY] + dd[j][midX][midY]) {
                            mn = dd[i][midX][midY] + dd[j][midX][midY];
                        }
                    }
                }
                dist[i][j] = mn;
                dist[j][i] = mn;
            }
        }

        // 无法达成的情形
        for (int i = 0; i < nb; i++) {
            if (dist[i][nb] == -1 || dist[i][nb + 1] == -1) {
                return -1;
            }
        }

        // dp 数组， -1 代表没有遍历到
        int[][] dp = new int[1 << nb][nb];
        for (int i = 0; i < 1 << nb; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < nb; i++) {
            dp[1 << i][i] = dist[i][nb];
        }

        // 由于更新的状态都比未更新的大，所以直接从小到大遍历即可
        for (int mask = 1; mask < (1 << nb); mask++) {
            for (int i = 0; i < nb; i++) {
                // 当前 dp 是合法的
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < nb; j++) {
                        // j 不在 mask 里
                        if ((mask & (1 << j)) == 0) {
                            int next = mask | (1 << j);
                            if (dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j]) {
                                dp[next][j] = dp[mask][i] + dist[i][j];
                            }
                        }
                    }
                }
            }
        }

        int ret = -1;
        int finalMask = (1 << nb) - 1;
        for (int i = 0; i < nb; i++) {
            if (ret == -1 || ret > dp[finalMask][i] + dist[i][nb + 1]) {
                ret = dp[finalMask][i] + dist[i][nb + 1];
            }
        }

        return ret;
    }

    public int[][] bfs(int x, int y, String[] maze) {
        int[][] ret = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(ret[i], -1);
        }
        ret[x][y] = 0;
        Queue<int[]> queue = new LinkedList<int[]>();
        queue.offer(new int[]{x, y});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            int curx = p[0], cury = p[1];
            for (int k = 0; k < 4; k++) {
                int nx = curx + dx[k], ny = cury + dy[k];
                if (inBound(nx, ny) && maze[nx].charAt(ny) != '#' && ret[nx][ny] == -1) {
                    ret[nx][ny] = ret[curx][cury] + 1;
                    queue.offer(new int[]{nx, ny});
                }
            }
        }
        return ret;
    }

    public boolean inBound(int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }


    /**
     * @Description:
     * 1.对于机关 M (i,j) 需要将其转成 一维的 key = i * 1000 + j, 并且 cnt 统计机关数量, 使用
     * Map 映射 key 与 num，表示机关编号, 这样之后可以得到机关的状态信息, mask = (1 << cnt) - 1
     * 2. 使用状态压缩 + 动态规划 + BFS + Dijkstra 算法, dp[i][j][mask][status] status 表示当前是否携带石头, 1 表示携带
     * 3. 由于只能在 T 处结束, 所以只能在 T 位置进行结束判断, mask = (1 << cnt) - 1
     * 超内存 43/48 空间复杂度 10^9 的数量级
     * @param: maze
     * @return int
     * @author marks
     * @CreateDate: 2026/09/09 15:31
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] maze) {
        int m = maze.length;
        int n = maze[0].length();
        int C = 1000;
        int cnt = 0;
        Map<Integer, Integer> map = new HashMap<>();
        int sx = -1, sy = -1;
        int tx = -1, ty = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (maze[i].charAt(j) == 'M') {
                    map.put(i * C + j, cnt);
                    cnt++;
                } else if (maze[i].charAt(j) == 'S') {
                    sx = i;
                    sy = j;
                } else if (maze[i].charAt(j) == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        }
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int[][][][] dp = new int[m][n][1 << cnt][2];
        // 初始化dp 为 -1
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < (1 << cnt); k++) {
                    for (int l = 0; l < 2; l++) {
                        dp[i][j][k][l] = -1;
                    }
                }
            }
        }
        dp[sx][sy][0][0] = 0;
        // 创建队列
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {sx, sy, 0, 0}); // {x, y, mask, status}
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1], mask = cur[2], status = cur[3];
                if (x == tx && y == ty && mask == (1 << cnt) - 1) {
                    return dp[x][y][mask][status];
                }
                for (int[] dir : dirs) {
                    int nx = x + dir[0], ny = y + dir[1];
                    if (nx >= 0 && nx < m && ny >= 0 && ny < n && maze[nx].charAt(ny) != '#') {
                        int nMask = mask, nStatus = status;
                        if (maze[nx].charAt(ny) == 'M') {
                            // 下一个是机关, 需要处理: 1. 机关是否已经触发 && 2. 当前是否携带石头
                            if ((mask & (1 << map.get(nx * C + ny))) == 0 && status == 1) {
                                // 需要更改状态
                                nMask |= (1 << map.get(nx * C + ny));
                                nStatus = 0;
                            }
                        } else if (maze[nx].charAt(ny) == 'O') {
                            // 下一个是石堆, 需要处理: 1. 当前是否携带石头
                            if (status == 0) {
                                // 需要更改状态
                                nStatus = 1;
                            }
                        }
                        if (dp[nx][ny][nMask][nStatus] == -1 || dp[x][y][mask][status] + 1 < dp[nx][ny][nMask][nStatus]) {
                            dp[nx][ny][nMask][nStatus] = dp[x][y][mask][status] + 1;
                            queue.offer(new int[] {nx, ny, nMask, nStatus});
                        }
                    }
                }
            }
        }
        return -1;
    }

}
