package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/8 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2477 {
    private long ans = 0;
    /**
     * @Description:
     * 给你一棵 n 个节点的树（一个无向、连通、无环图），每个节点表示一个城市，编号从 0 到 n - 1 ，且恰好有 n - 1 条路。
     * 0 是首都。给你一个二维整数数组 roads ，其中 roads[i] = [ai, bi] ，表示城市 ai 和 bi 之间有一条 双向路 。
     *
     * 每个城市里有一个代表，他们都要去首都参加一个会议。
     * 每座城市里有一辆车。给你一个整数 seats 表示每辆车里面座位的数目。
     * 城市里的代表可以选择乘坐所在城市的车，或者乘坐其他城市的车。相邻城市之间一辆车的油耗是一升汽油。
     *
     * 请你返回到达首都最少需要多少升汽油。
     *
     * tips:
     * 1 <= n <= 10^5
     * roads.length == n - 1
     * roads[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * roads 表示一棵合法的树。
     * 1 <= seats <= 10^5
     * @param roads
     * @param seats
     * @return long
     * @author marks
     * @CreateDate: 2025/8/8 15:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long minimumFuelCost(int[][] roads, int seats) {
        long result;
        result = method_01(roads, seats);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：roads = [[3,1],[3,2],[1,0],[0,4],[0,5],[4,6]], seats = 2
     * 输出：7
     * 1. 既然每个节点都有一辆车, 那么每个节点到父节点的耗油都是1L, 并且父节点此时停留很多的车, 但是这些车不一定都会开到下一个节点.
     * 2. 假设此时父节点的代表数为 x, 需要多少辆车可以装下这些代表, 用 c 表示车辆数 c = (x + seats - 1) / seats。需要移动到下一个节点的耗油是 c * 1L。
     * 3. 解题方法是深度优先搜索, 自底向上遍历, 返回值为当前节点到下一个节点的代表的人员数量。
     * 4. just do it!
     * AC: 55ms(27.27%)/96.97MB(67.53%)
     * @param roads
     * @param seats
     * @return long
     * @author marks
     * @CreateDate: 2025/8/8 15:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[][] roads, int seats) {
        List<Integer>[] graph = buildGraph(roads);
        dfs(0, -1, graph, seats);
        return ans;
    }

    private int dfs(int node, int parent, List<Integer>[] graph, int seats) {
        int sum = 1;
        for (int next : graph[node]) {
            if (next != parent) {
                sum += dfs(next, node, graph, seats);
            }
        }
        if (node != 0) {
            ans += (sum + seats - 1) / seats;
        }
        return sum;
    }

    private List<Integer>[] buildGraph(int[][] roads) {
        int n = roads.length + 1;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : roads) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        return graph;
    }
}
