package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_35 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/14 16:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_35 {

    /**
     * @Description:
     * 小明的电动车电量充满时可行驶距离为 cnt，每行驶 1 单位距离消耗 1 单位电量，且花费 1 单位时间。
     * 小明想选择电动车作为代步工具。地图上共有 N 个景点，景点编号为 0 ~ N-1。
     * 他将地图信息以 [城市 A 编号,城市 B 编号,两城市间距离] 格式整理在在二维数组 paths，表示城市 A、B 间存在双向通路。
     * 初始状态，电动车电量为 0。每个城市都设有充电桩，charge[i] 表示第 i 个城市每充 1 单位电量需要花费的单位时间。
     * 请返回小明最少需要花费多少单位时间从起点城市 start 抵达终点城市 end。
     * @param: paths
     * @param: cnt
     * @param: start
     * @param: end
     * @param: charge
     * @return int
     * @author marks
     * @CreateDate: 2026/01/14 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int electricCarPlan(int[][] paths, int cnt, int start, int end, int[] charge) {
        int result;
        result = method_01(paths, cnt, start, end, charge);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：paths = [[1,3,3],[3,2,1],[2,1,3],[0,1,4],[3,0,5]], cnt = 6, start = 1, end = 0, charge = [2,10,4,1]
     * 输出：43
     * 1. 这种计算图中最小路径的问题, 我总是使用 Dijkstra 算法来处理
     * 2. 但是这一题需要考虑充电问题, int[] dist 为节点 start 到 dist[i] 花费的最小时间. 此时花费的时间是 x, 剩余的电量是 y
     * 那么如果需要在当前地点充电到 y, 所需时间是多少? charge[i] * y + x
     * 3. 我是不是应该反过来思考, 即从end 点开始逆向行驶.
     * 4. 存在不可通过的路径 path[i][2] > cnt, 则不可通过, 构建邻接表时使用此条件判断可用边
     * 5. charge[i] < charge[j], 并且 i j 存在直连边. end - i - j. 并且
     * 5.1 c[i] <= c[j], 此时需要在 c[i] 处进行充电 path[end - i][2] * c[j] + path[i-j][2] * c[j]
     * 5.2 c[i] > c[j] && p[e-i] + p[i-j] > cnt. 此时需要在 c[j] 出充满电, 并且 c[i] 处需要充 sub = p[e-i] + p[i-j] - cnt, 时间是 c[i] * sub
     * 5.3 c[i] > c[j] && p[e-i] + p[i-j] <= cnt. 此时需要在 c[i] 处不需要进行充电, 只需要在 c[j] 处充电, 时间是 c[j] * (p[e-i] + p[i-j])
     * 6. 然后就需要递归的进行分析.
     * 7. 应该使用二维数组记录dist[i][2], 到达节点i 花费的时间和剩余的电量 dist[i] = {t, c}, t 为花费的时间, c 为剩余的电量
     * 8. 然后在一个节点进行充电时, 可以充[0 ~ cnt] 的电量, 然后就往下一个节点移动. 然后通过优先队列, 优先队列存储的是按照 t 的小根堆存储
     * 9. 做一下试试看, 果然成功了, 虽然时间复杂度很高, 但是通过
     * AC: 280ms/68.77MB, spend time: 1h
     * 10. 怎么优化? 存储一个int[] minDist 存储节点 i 到达下一个节点需要的最小的电量值, 例如节点 i, 与节点a,b,c 直接的距离是 10, 12, 5, 那么 minDist[i] = 5
     * AC: 239ms/65.98MB, 优化了一点点, 但是时间复杂度还是很高, 看看官解吧
     * 11 差不多懂了, 相当于是在我的基础上, 只需要充1格电, 然后吧这个添加到队列中, 不需要充[0~cnt] 的电量
     * @param: paths
     * @param: cnt
     * @param: start
     * @param: end
     * @param: charge
     * @return int
     * @author marks
     * @CreateDate: 2026/01/14 16:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] paths, int cnt, int start, int end, int[] charge) {
        final int INF = Integer.MAX_VALUE / 2;
        int n = charge.length;
        int[] minDist = new int[n];
        Arrays.fill(minDist, INF);
        // 构建邻接表
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] path : paths) {
            int u = path[0];
            int v = path[1];
            int d = path[2];
            if (d <= cnt) {
                graph[u].add(new int[]{v, d});
                graph[v].add(new int[]{u, d});
                minDist[u] = Math.min(minDist[u], d);
                minDist[v] = Math.min(minDist[v], d);
            }
        }
        int[][] dist = new int[n][cnt + 1]; // dist[i][0] 为花费的时间, dist[i][1] 为剩余的电量
        // 初始化 dist
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        // 构建优先队列, int[] = {t, i, c}
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        // 花费时间是0, 剩余电量是 0
        for (int i = minDist[start]; i <= cnt && start != end; i++) {
            dist[start][i] = charge[start] * i;
            queue.offer(new int[]{charge[start] * i, start, i});
        }
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int t = curr[0];
            int i = curr[1];
            int c = curr[2];
            if (i == end) {
                return t;
            }
            for (int[] next : graph[i]) {
                int j = next[0];
                int d = next[1];
                int temp = c - d; // 剩余的电量
                if (temp < 0) {
                    // 无法通过, 当前电量不支持通过当前路径, 跳过
                    continue;
                }
                if (dist[j][temp] > t + d) {
                    dist[j][temp] = t + d;
                    // 需要在j 处进行充电操作
                    for (int k = 0; k + temp <= cnt; k++) {
                        if(j != end) {
                            if (k + temp >= minDist[j]) {
                                queue.offer(new int[]{t + d + k * charge[j], j, k + temp});
                            }
                        } else {
                            queue.offer(new int[]{t + d + k * charge[j], j, k + temp});
                        }
                    }
                }
            }
        }
        return 0;
    }

}
