package com.marks.leetcode.graph_theory;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3613 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3613 {

    /**
     * @Description:
     * 给你一个无向连通图，包含 n 个节点，节点编号从 0 到 n - 1，
     * 以及一个二维整数数组 edges，其中 edges[i] = [ui, vi, wi] 表示一条连接节点 ui 和节点 vi 的无向边，边权为 wi，另有一个整数 k。
     * 你可以从图中移除任意数量的边，使得最终的图中 最多 只包含 k 个连通分量。
     * 连通分量的 成本 定义为该分量中边权的 最大值 。如果一个连通分量没有边，则其代价为 0。
     * 请返回在移除这些边之后，在所有连通分量之中的 最大成本 的 最小可能值 。
     *
     * @param: n
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int n, int[][] edges, int k) {
        int result;
        result = method_01(n, edges, k);
        result = method_02(n, edges, k);
        return result;
    }

    /**
     * @Description:
     * 贪心 + 并查集
     * AC: 66ms/148.76MB
     * @param: n
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 15:09
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[][] edges, int k) {
        UnionFind uf = new UnionFind(n);
        if (uf.getCount() <= k) {
            return 0;
        }
        // 排序
        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v);
            }
            if (uf.getCount() <= k) {
                return edges[i][2];
            }
        }
        return 0;
    }

    /**
     * @Description:
     * 1. 二分法 + 并查集
     * 2. 分析, 假设添加了 [0, mid] 条边后, count = uf.getCount(),
     * 3. count > k, 说明不满足条件, 需要继续扩大边的数量, left = mid + 1;
     * 4. count <= k, 说明满足条件, 需要缩小边的数量, right = mid - 1;
     *
     * AC: 113ms/158.97MB
     * @param: n
     * @param: edges
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/09 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges, int k) {
        if (k >= n) {
            return 0;
        }
        int m = edges.length;
        // 排序edges
        Arrays.sort(edges, (a, b) -> a[2] - b[2]); // 权值升序排序
        int left = 0;
        int right = m - 1;
        int ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (canRemoveEdges(n, edges, mid, k)) {
                ans = edges[mid][2];
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean canRemoveEdges(int n, int[][] edges, int mid, int k) {
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i <= mid; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            if (uf.find(u) != uf.find(v)) {
                uf.union(u, v);
            }
        }
        return uf.getCount() > k;
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
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                if (rank[parentX] > rank[parentY]) {
                    root[parentY] = parentX;
                } else if (rank[parentX] < rank[parentY]) {
                    root[parentX] = parentY;
                } else {
                    root[parentY] = parentX;
                    rank[parentX] += 1;
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }
    }

}
