package com.marks.leetcode.data_structure.union_find;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/12 17:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_685 {
    /**
     * @Description:
     * 在本问题中，有根树指满足以下条件的 有向 图。该树只有一个根节点，所有其他节点都是该根节点的后继。
     * 该树除了根节点之外的每一个节点都有且只有一个父节点，而根节点没有父节点。
     *
     * 输入一个有向图，该图由一个有着 n 个节点（节点值不重复，从 1 到 n）的树及一条附加的有向边构成。
     * 附加的边包含在 1 到 n 中的两个不同顶点间，这条附加的边不属于树中已存在的边。
     *
     * 结果图是一个以边组成的二维数组 edges 。 每个元素是一对 [ui, vi]，用以表示 有向 图中连接顶点 ui 和顶点 vi 的边，其中 ui 是 vi 的一个父节点。
     *
     * 返回一条能删除的边，使得剩下的图是有 n 个节点的有根树。若有多个答案，返回最后出现在给定二维数组的答案。
     *
     * tips:
     * n == edges.length
     * 3 <= n <= 1000
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     * @param edges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/12 17:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int[] result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * E1:
     * edges = [[1,2],[1,3],[2,3]]
     * 例如[1, 2], 那么可以将 1 添加为根节点
     * [1, 3], 1 还是一个根节点
     * [2, 3]
     *
     * 好像想到一个方法, find(x) == find(y)
     * 那么这个就是一个冗余的, 但是还是会有特殊情况, 1 -> 2, 5 -> 3, 2 -> 3, 1 -> 5
     * 另外还需要一个额外数组记录节点的入度数量, degree[i] > 1, 那么这条节点就需要删除
     *
     * 我们需要理解 n == edges.length, 如果该图是一个有根节点的图, 那么节点个数为n, 则边的个数为 n - 1, 且根节点没有父节点
     * 也就是说我们需要删除一条边使得该有向图成一个有根树
     *
     * 仅仅只是查看官解, 还需要自己理解, 有点难理解, wait ToDo
     *
     * 看一个成环的案例
     * E2:
     * [1,2],[2,3],[3,4],[4,1]
     * p[1] = 2, p[2] = 3, p[3] = 4
     * p[1] = 4, p[4] = 4
     * @param edges 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/12 17:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges) {
        int n = edges.length;
        UnionFind uf = new UnionFind(n + 1);
        int[] parent = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            parent[i] = i;
        }
        int conflict = -1;
        int cycle = -1;
        for (int i = 0; i < n; ++i) {
            int[] edge = edges[i];
            int node1 = edge[0], node2 = edge[1];
            if (parent[node2] != node2) {
                conflict = i; // 子节点的入度数大于1， parent[node2] != node2, 表明parent[node2] = x, x是node2的父节点, 但是现在node1有变成 node2的父节点
            } else {
                parent[node2] = node1;
                if (uf.find(node1) == uf.find(node2)) { // 拥有相同的子节点, 成环, [1,2],[2,3],[3,4],[4,1] => p[1] = 4 = p[4]
                    cycle = i;
                } else {
                    uf.union(node1, node2);
                }
            }
        }
        if (conflict < 0) {
            int[] redundant = {edges[cycle][0], edges[cycle][1]};
            return redundant;
        } else {
            int[] conflictEdge = edges[conflict];
            int[] redundant;
            if (cycle >= 0) {
                redundant = new int[]{parent[conflictEdge[1]], conflictEdge[1]};
            } else {
                redundant = new int[]{conflictEdge[0], conflictEdge[1]};
            }
            return redundant;
        }
    }

    private static class UnionFind {
        private int[] parent;

        public UnionFind(int size) {
            parent = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
            }
        }

        public int find(int x) {
            if (parent[x] == x) {
                return parent[x];
            } else {
                x = parent[x];
                return find(x);
            }
        }

        /**
         * @Description:
         * [1, 2], [2, 3] [3, 4], [4, 5]
         * parent[1] = 1 parent[2] = 2,
         * parent[1] = 2
         * parent[2] = 3
         * @param x
         * @param y
         * @return void
         * @author marks
         * @CreateDate: 2025/3/17 10:23
         * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
         */
        public void union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                parent[parentX] = parentY;
            }
        }

        public boolean isUnion(int x, int y) {
            return find(x) == find(y);
        }
    }
}
