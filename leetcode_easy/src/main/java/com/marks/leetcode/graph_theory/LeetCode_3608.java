package com.marks.leetcode.graph_theory;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3608 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3608 {

    /**
     * @Description:
     * 给你一个整数 n，表示一个包含 n 个节点（从 0 到 n - 1 编号）的无向图。
     * 该图由一个二维数组 edges 表示，
     * 其中 edges[i] = [ui, vi, timei] 表示一条连接节点 ui 和节点 vi 的无向边，该边会在时间 timei 被移除。
     * 同时，另给你一个整数 k。
     * 最初，图可能是连通的，也可能是非连通的。
     * 你的任务是找到一个 最小 的时间 t，使得在移除所有满足条件 time <= t 的边之后，该图包含 至少 k 个连通分量。
     * 返回这个 最小 时间 t。
     * 连通分量 是图的一个子图，其中任意两个顶点之间都存在路径，且子图中的任意顶点均不与子图外的顶点共享边。
     * @param: n
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minTime(int n, int[][] edges, int k) {
        int result;
        result = method_01(n, edges, k);
        return result;
    }

    /**
     * @Description:
     * 1. 连通性问题使用并查集求解. k >= uf.getCount()
     * 2. 对edges 按照 时间顺序进行降序排序, 假设最大时间是 maxTime, 此时所有的节点之间均不存在连接, 此时的连通分量数是 n
     * 3. 然后向并查集中添加边edge[i],  uf.union(u, v), 然后判断 uf.getCount() >= k, 如果true, ans = edge[i][2], 否则 false, break
     * 4. 存在问题, 初始时的节点构成的图可能存在多个连通分量. 需要计算初始连通分量大小 initCount.
     *
     * AC: 64ms/196.82MB
     * @param: n
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/08 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int k) {
        // 构建一个初始并查集, 找到初始连通分量大小
        UnionFind initUf = new UnionFind(n);
        for (int i = 0; i < edges.length; i++) {
            initUf.union(edges[i][0], edges[i][1]);
        }
        int initCount = initUf.getCount();
        if (k <= initCount) {
            return 0;
        }
        // 创建并查集
        UnionFind uf = new UnionFind(n);
        // 排序edges
        Arrays.sort(edges, (a, b) -> b[2] - a[2]);
        int ans = edges[0][2]; // 最大的时间
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int time = edges[i][2];
            uf.union(u, v);
            ans = time;
            if (uf.getCount() < k) {
                break;
            }
        }
        return ans;
    }

    // 构建并查集
    class UnionFind {
        private int[] root;
        private int[] rank;
        private int count;

        public UnionFind(int n) {
            this.count = n;
            this.root = new int[n];
            this.rank = new int[n];
            for (int i = 0; i < n; i++) {
                root[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int x) {
            if (root[x] == x) {
                return x;
            }
            return root[x] = find(root[x]);
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    root[rootY] = rootX;
                } else {
                    root[rootX] = rootY;
                    if (rank[rootX] == rank[rootY]) {
                        rank[rootY]++;
                    }
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }
    }

}
