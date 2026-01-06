package com.marks.leetcode.graph_theory;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1579 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1579 {

    /**
     * @Description:
     * Alice 和 Bob 共有一个无向图，其中包含 n 个节点和 3  种类型的边：
     *
     * 类型 1：只能由 Alice 遍历。
     * 类型 2：只能由 Bob 遍历。
     * 类型 3：Alice 和 Bob 都可以遍历。
     * 给你一个数组 edges ，其中 edges[i] = [typei, ui, vi] 表示节点 ui 和 vi 之间存在类型为 typei 的双向边。
     * 请你在保证图仍能够被 Alice和 Bob 完全遍历的前提下，找出可以删除的最大边数。
     * 如果从任何节点开始，Alice 和 Bob 都可以到达所有其他节点，则认为图是可以完全遍历的。
     *
     * 返回可以删除的最大边数，如果 Alice 和 Bob 无法完全遍历图，则返回 -1 。
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 15:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxNumEdgesToRemove(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 如果要测试连通性, 可以使用并查集
     * 2. 分别给Alice Bob 创建两个并查集
     * AC: 30ms/138.57MB
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 15:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        UnionFind ufA = new UnionFind(n);
        UnionFind ufB = new UnionFind(n);
        int ans = 0;
        // 先对edges 进行排序, 按照 type 降序排序, 因为type 3 = type 1 + type 2, 所以 type 3 先一步用于并查集, 可以得到最大删除边数
        Arrays.sort(edges, (a, b) -> b[0] - a[0]);
        for (int[] edge : edges) {
            int type = edge[0];
            int u = edge[1] - 1;
            int v = edge[2] - 1;
            if (type == 3) {
                if (ufA.isConnected(u, v) && ufB.isConnected(u, v)) {
                    // 可以删除
                    ans++;
                } else {
                    ufA.union(u, v);
                    ufB.union(u, v);
                }
            } else if (type == 2) {
                // ufB
                if (ufB.isConnected(u, v)) {
                    ans++;
                } else {
                    ufB.union(u, v);
                }
            } else if (type == 1) {
                // ufA
                if (ufA.isConnected(u, v)) {
                    ans++;
                } else {
                    ufA.union(u, v);
                }
            }
        }
        if (ufA.count != 1 || ufB.count != 1) {
            return -1; // 无法完全遍历
        }
        return ans;
    }

    // 创建并查集
    class UnionFind {
        private int[] root;
        private int[] rank;
        private int count;
        public UnionFind(int size) {
            root = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                root[i] = i;
                rank[i] = 1;
            }
            this.count = size;
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
                } else if (rank[rootX] < rank[rootY]) {
                    root[rootX] = rootY;
                } else {
                    root[rootY] = rootX;
                    rank[rootX] += 1;
                }
                count--;
            }
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

}
