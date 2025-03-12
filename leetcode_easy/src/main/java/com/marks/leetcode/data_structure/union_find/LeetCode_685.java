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
     * @Description: [功能描述]
     * @param edges 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/12 17:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges) {
        List<Integer> rootNode = new ArrayList<>(); // 记录入度为0的根节点信息
        int size = 0;
        for (int[] edge : edges) {
            size = Math.max(size, Math.max(edge[0], edge[1]));
        }
        UnionFind uf = new UnionFind(size);
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            if (!rootNode.contains(x)) {
                rootNode.add(x);
            }

        }
        return new int[0];
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

        public void union(int x, int y) {
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                parent[Math.max(parentX, parentY)] = Math.min(parentX, parentY);
            }
        }

        public boolean isUnion(int x, int y) {
            return find(x) == find(y);
        }
    }
}
