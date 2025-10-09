package com.marks.leetcode.bitwise_operation;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/26 9:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3108 {

    /**
     * @Description:
     * 给你一个 n 个节点的带权无向图，节点编号为 0 到 n - 1 。
     * 给你一个整数 n 和一个数组 edges ，其中 edges[i] = [ui, vi, wi] 表示节点 ui 和 vi 之间有一条权值为 wi 的无向边。
     *
     * 在图中，一趟旅途包含一系列节点和边。旅途开始和结束点都是图中的节点，且图中存在连接旅途中相邻节点的边。
     * 注意，一趟旅途可能访问同一条边或者同一个节点多次。
     *
     * 如果旅途开始于节点 u ，结束于节点 v ，我们定义这一趟旅途的 代价 是经过的边权按位与 AND 的结果。
     * 换句话说，如果经过的边对应的边权为 w0, w1, w2, ..., wk ，那么代价为w0 & w1 & w2 & ... & wk ，其中 & 表示按位与 AND 操作。
     *
     * 给你一个二维数组 query ，其中 query[i] = [si, ti] 。
     * 对于每一个查询，你需要找出从节点开始 si ，在节点 ti 处结束的旅途的最小代价。如果不存在这样的旅途，答案为 -1 。
     *
     * 返回数组 answer ，其中 answer[i] 表示对于查询 i 的 最小 旅途代价。
     * 
     * tips:
     * 1 <= n <= 10^5
     * 0 <= edges.length <= 10^5
     * edges[i].length == 3
     * 0 <= ui, vi <= n - 1
     * ui != vi
     * 0 <= wi <= 10^5
     * 1 <= query.length <= 10^5
     * @param n 
     * @param edges 
     * @param query
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/26 9:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        int[] result;
        result = method_01(n, edges, query);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 5, edges = [[0,1,7],[1,3,7],[1,2,1]], query = [[0,3],[3,4]]
     * 输出：[1,-1]
     * 1. 我想到的第一个方法方法是并查集
     * 2. 先理清一下思路, query[i] = [si, ti], uf.find(si) == uf.find(ti)
     * 3. 先写一个广度优先搜索, 获取节点的 & 最小值
     * AC: 1257ms/96MB
     * @param n 
     * @param edges 
     * @param query 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/9/26 9:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int currentAndValue;
    private int[] method_01(int n, int[][] edges, int[][] query) {
        List<Integer>[] graph = buildGraph(n, edges); // 构建邻接表
        int[] andValues = new int[n];
        Arrays.fill(andValues, -1);
        Map<Long, Integer> andValueMap = new HashMap<>();

        // 然后再写一个并查集
        UnionF uf = new UnionF(n);

        for (int[] edge : edges) {
            int num1 = Math.max(edge[0], edge[1]);
            int num2 = Math.min(edge[0], edge[1]);
            uf.union(num1, num2);
            long key = ((long) num1 << 32) | num2;
            andValueMap.merge(key, edge[2], (oldValue, newValue) -> oldValue & newValue);
        }
        for (int i = 0; i < n; i++) {
            if (andValues[i] != -1) {
                continue;
            }
            boolean[] visited = new boolean[n];
            currentAndValue = Integer.MAX_VALUE;
            if (andValues[i] == -1) {
                bfs(graph, i, andValueMap, visited);
            }
            for (int j = 0; j < n; j++) {
                if (visited[j]) {
                    andValues[j] = currentAndValue;
                }
            }
        }
        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int si = query[i][0];
            int ti = query[i][1];
            if (uf.isConnected(si, ti)) {
                ans[i] = andValues[si];
            } else {
                ans[i] = -1;
            }
        }

        return ans;
    }

    private void bfs(List<Integer>[] graph, int index, Map<Long, Integer> andValueMap, boolean[] visited) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(index);
        while (!queue.isEmpty()) {
            int curr_i = queue.poll();
            visited[curr_i] = true;
            for (int j : graph[curr_i]) {
                long key = ((long) Math.max(curr_i, j) << 32) | Math.min(curr_i, j);
                if (andValueMap.containsKey(key)) {
                    currentAndValue &= andValueMap.get(key);
                }
                if (!visited[j]) {
                    queue.offer(j);
                    visited[j] = true;
                }
            }
        }
    }

    private List<Integer>[] buildGraph(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }
        return graph;
    }

    private class UnionF {
        private int[] root;
        private int[] rank;
        private int count;
        public UnionF(int size) {
            root = new int[size];
            rank = new int[size];
            count = size;
            for (int i = 0; i < size; i++) {
                root[i] = i;
                rank[i] = 1;
            }
        }
        public int find(int x) {
            if (x != root[x]) {
                root[x] = find(root[x]);
            }
            return root[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    root[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    root[rootX] = rootY;
                } else {
                    root[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }

        public int getCount() {
            return count;
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }
}
