package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2646 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/30 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2646 {

    /**
     * @Description:
     * 现有一棵无向、无根的树，树中有 n 个节点，按从 0 到 n - 1 编号。
     * 给你一个整数 n 和一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条边。
     *
     * 每个节点都关联一个价格。给你一个整数数组 price ，其中 price[i] 是第 i 个节点的价格。
     * 给定路径的 价格总和 是该路径上所有节点的价格之和。
     * 另给你一个二维整数数组 trips ，其中 trips[i] = [starti, endi] 表示您从节点 starti 开始第 i 次旅行，并通过任何你喜欢的路径前往节点 endi 。
     *
     * 在执行第一次旅行之前，你可以选择一些 非相邻节点 并将价格减半。
     * 返回执行所有旅行的最小价格总和。
     * tips:
     * 1 <= n <= 50
     * edges.length == n - 1
     * 0 <= ai, bi <= n - 1
     * edges 表示一棵有效的树
     * price.length == n
     * price[i] 是一个偶数
     * 1 <= price[i] <= 1000
     * 1 <= trips.length <= 100
     * 0 <= starti, endi <= n - 1
     * @param: n
     * @param: edges
     * @param: price
     * @param: trips
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        int result;
        result = method_01(n, edges, price, trips);
        return result;
    }

    /**
     * @Description:
     * 1. 价格1是给奇数节点打折, 价格2是给偶数节点打折
     * 关键点: 1. 构建邻接表, 2. 构建路径, 3. 构建dp
     * AC: 15ms/46.05MB
     * @param: n
     * @param: edges
     * @param: price
     * @param: trips
     * @return int
     * @author marks
     * @CreateDate: 2025/12/30 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] count;
    private boolean flag;
    private int method_01(int n, int[][] edges, int[] price, int[][] trips) {
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            graph[x].add(y);
            graph[y].add(x);
        }
        this.count = new int[n]; // 到达节点的次数
        for (int[] trip : trips) {
            int start = trip[0];
            int end = trip[1];
            List<Integer> path = new ArrayList<>();
            path.add(start);
            boolean[] visited = new boolean[n];
            visited[start] = true;
            flag = false;
            if (start != end) {
                dfs(start, end, path, graph, visited);
            } else {
                this.count[start]++;
            }
        }
        /*
         * 这个价格减半存在问题, 需要通过dp 来处理
         * 1. 不是简单的通过奇偶性处理, 1,2,3,4; 可以1, 4 进行价格减半
         */
        int[] pair = dp(0, -1, price, graph);
        return Math.min(pair[0], pair[1]);
    }

    private int[] dp(int node, int parent, int[] price, List<Integer>[] graph) {
        int[] res = new int[2];
        res[0] = price[node] * count[node];
        res[1] = price[node] * count[node] / 2;
        for (int next : graph[node]) {
            if (next != parent) {
                int[] next_res = dp(next, node, price, graph);
                res[0] += Math.min(next_res[0], next_res[1]); // 可以取下一个价格减半的 和不进行减半的
                res[1] += next_res[0]; // 只能取下一个价格不进行减半的
            }
        }
        return res;
    }

    private void dfs(int parent, int target, List<Integer> path, List<Integer>[] graph, boolean[] visited) {
        if (flag) {
            return;
        }
        if (parent == target) {
            for (int i = path.size() - 1; i >= 0; i--) {
                int curr = path.get(i);
                this.count[curr]++;
            }
            flag = true;
            return;
        }
        for (int next : graph[parent]) {
            if (!visited[next]) {
                visited[next] = true;
                path.add(next);
                dfs(next, target, path, graph, visited);
                path.remove(path.size() - 1);
                visited[next] = false;
            }
        }
    }

}
