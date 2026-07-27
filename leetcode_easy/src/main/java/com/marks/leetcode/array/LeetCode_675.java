package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_675 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/23 14:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_675 {

    /**
     * @Description:
     * 你被请来给一个要举办高尔夫比赛的树林砍树。树林由一个 m x n 的矩阵表示， 在这个矩阵中：
     * 0 表示障碍，无法触碰
     * 1 表示地面，可以行走
     * 比 1 大的数 表示有树的单元格，可以行走，数值表示树的高度
     * 每一步，你都可以向上、下、左、右四个方向之一移动一个单位，如果你站的地方有一棵树，那么你可以决定是否要砍倒它。
     * 你需要按照树的高度从低向高砍掉所有的树，每砍过一颗树，该单元格的值变为 1（即变为地面）。
     * 你将从 (0, 0) 点开始工作，返回你砍完所有树需要走的最小步数。 如果你无法砍完所有的树，返回 -1 。
     * 可以保证的是，没有两棵树的高度是相同的，并且你至少需要砍倒一棵树。
     *
     * tips:
     * m == forest.length
     * n == forest[i].length
     * 1 <= m, n <= 50
     * 0 <= forest[i][j] <= 10^9
     * @param: forest
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int cutOffTree(List<List<Integer>> forest) {
        int result;
        result = method_01(forest);
        result = method_02(forest);
        return result;
    }

    /**
     * @Description:
     * 1. 原来只需要对点点之间进行BFS 就行, 想复杂了.
     * AC: 409ms/46.42MB
     * @param: forest
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 15:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(List<List<Integer>> forest) {
        m = forest.size();
        n = forest.get(0).size();
        // List, 存储所有树的位置
        List<int[]> trees = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    trees.add(new int[]{i, j});
                }
            }
        }
        // 按照树的高度进行排序
        trees.sort(Comparator.comparingInt(a -> forest.get(a[0]).get(a[1])));
        int ans = 0;
        int[] cur = new int[]{0, 0};
        for (int[] tree : trees) {
            int path = BFS(cur, tree, forest);
            if (path == -1) {
                return -1;
            }
            ans += path;
            cur = tree;
        }
        return ans;
    }

    private int BFS(int[] start, int[] end, List<List<Integer>> forest) {
        int sx = start[0], sy = start[1];
        int tx = end[0], ty = end[1];
        if (sx == tx && sy == ty) {
            return 0;
        }

        int row = forest.size();
        int col = forest.get(0).size();
        int step = 0;
        Queue<int[]> queue = new ArrayDeque<int[]>();
        boolean[][] visited = new boolean[row][col];
        queue.offer(new int[]{sx, sy});
        visited[sx][sy] = true;
        while (!queue.isEmpty()) {
            step++;
            int sz = queue.size();
            for (int i = 0; i < sz; ++i) {
                int[] cell = queue.poll();
                int cx = cell[0], cy = cell[1];
                for (int[] dir : dirs) {
                    int nx = cx + dir[0];
                    int ny = cy + dir[1];
                    if (nx >= 0 && nx < row && ny >= 0 && ny < col) {
                        if (!visited[nx][ny] && forest.get(nx).get(ny) > 0) {
                            if (nx == tx && ny == ty) {
                                return step;
                            }
                            queue.offer(new int[]{nx, ny});
                            visited[nx][ny] = true;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private int m;
    private int n;
    private int[][] dist; // 所有点对的最短距离矩阵
    private int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 四个方向
    private final int INF = Integer.MAX_VALUE / 2;
    /**
     * @Description:
     * 1. 将高度大于 1 的树取出并且进行排序操作, 升序排序
     * 2. 从 (0, 0) 点开始, 使用 Dijkstra 算法, 计算 到其它点的最小移动步数
     * 3. Floyd-Warshall 可‌一次性预计算所有点对的最短距离‌，后续查询只需 O(1) 查表，是多查询场景的最优解。
     * 由于 floyd 算法只能处理中等规模的网格 <= 50, 当前网格大小符合要求, 所以采用.
     * 还是超时, 36/55
     * 4. 由于还是超时, 大概率是构建 dist 时的三重for 循环导致超时, 需要使用 Dijkstra 多源
     * 5. 仍然超时 42/55, 看看题解吧.
     * 6. 由于只需要处理特点点对之间的距离就可以, 不需要处理每一个点对之间的距离, 所以导致超时, 使用普通的BFS, 见 method_02
     * @param: forest
     * @return int
     * @author marks
     * @CreateDate: 2026/07/23 14:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(List<List<Integer>> forest) {
        m = forest.size();
        n = forest.get(0).size();
        buildFloydWarshallGridByDijkstra(forest);
        // List, 存储所有树的位置
        List<int[]> trees = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (forest.get(i).get(j) > 1) {
                    trees.add(new int[]{i, j});
                }
            }
        }
        // 按照树的高度进行排序
        trees.sort(Comparator.comparingInt(a -> forest.get(a[0]).get(a[1])));
        int ans = 0;
        int[] cur = new int[]{0, 0};
        for (int[] tree : trees) {
            int path = queryMinPath(cur, tree, forest);
            if (path == -1) {
                return -1;
            }
            ans += path;
            cur = tree;
        }
        return ans;
    }

    private void buildFloydWarshallGridByDijkstra(List<List<Integer>> forest) {
        dist = new int[m * n][m * n];
        // 初始化矩阵, 距离初始化位无限大 INF
        for (int i = 0; i < m * n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0; // 自己到自己的距离为 0
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 跳过障碍物
                if (forest.get(i).get(j) == 0) {
                    continue;
                }
                int u = i * n + j;
                dijkstra(u, forest);
            }
        }
    }

    // 从源点 start 执行 Dijkstra，更新 dist[start][*]
    private void dijkstra(int start, List<List<Integer>> forest) {
        // 创建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        boolean[] visited = new boolean[m * n];
        pq.offer(new int[]{start, 0}); // {currNode, distFromStart}
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int u = curr[0];
            if (visited[u]) {
                continue;
            }
            visited[u] = true;
            dist[start][u] = curr[1];
            for (int[] dir : dirs) {
                int x = u / n + dir[0], y = u % n + dir[1];
                if (x >= 0 && x < m && y >= 0 && y < n && forest.get(x).get(y) > 0) {
                    int v = x * n + y;
                    if (!visited[v] && dist[start][v] > curr[1] + 1) {
                        pq.offer(new int[]{v, curr[1] + 1});
                    }
                }
            }
        }
    }

    private void buildFloydWarshallGrid(List<List<Integer>> forest) {
        dist = new int[m * n][m * n];

        // 初始化矩阵, 距离初始化位无限大 INF
        for (int i = 0; i < m * n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0; // 自己到自己的距离为 0
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 跳过障碍物
                if (forest.get(i).get(j) == 0) {
                    continue;
                }
                int u = i * n + j;
                for (int[] dir : dirs) {
                    int x = i + dir[0], y = j + dir[1];
                    if (x >= 0 && x < m && y >= 0 && y < n && forest.get(x).get(y) > 0) {
                        int v = x * n + y;
                        dist[u][v] = 1; // 相邻格子之间移动距离为1
                    }
                }
            }
        }

        // Floyd-Warshall 核心：动态规划更新所有点对最短路径
        for (int k = 0; k < m * n; k++) {
            for (int i = 0; i < m * n; i++) {
                for (int j = 0; j < m * n; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }
    }

    private int queryMinPath(int[] start, int[] end, List<List<Integer>> forest) {
        int si = start[0], sj = start[1];
        int ei = end[0], ej = end[1];
        if (forest.get(si).get(sj) == 0 || forest.get(ei).get(ej) == 0) {
            return -1;
        }

        int u = si * n + sj;
        int v = ei * n + ej;
        return dist[u][v] == INF ? -1 : dist[u][v];
    }

}
