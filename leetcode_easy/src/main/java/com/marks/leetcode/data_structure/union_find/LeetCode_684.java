package com.marks.leetcode.data_structure.union_find;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/12 17:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_684 {
    /**
     * @Description:
     * 树可以看成是一个连通且 无环 的 无向 图。
     *
     * 给定往一棵 n 个节点 (节点值 1～n) 的树中添加一条边后的图。添加的边的两个顶点包含在 1 到 n 中间，且这条附加的边不属于树中已存在的边。
     * 图的信息记录于长度为 n 的二维数组 edges ，edges[i] = [ai, bi] 表示图中在 ai 和 bi 之间存在一条边。
     *
     * 请找出一条可以删去的边，删除后可使得剩余部分是一个有着 n 个节点的树。如果有多个答案，则返回数组 edges 中最后出现的那个。
     * @param edges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/12 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] findRedundantConnection(int[][] edges) {
        int[] result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * AC: 1ms/42.25MB
     * @param edges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/3/12 17:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges) {
        int size = 0;
        for (int[] edge : edges) {
            size = Math.max(size, Math.max(edge[0], edge[1]));
        }
        UnionFind uf = new UnionFind(size + 1);
        int[] ans = new int[2];
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            if (uf.isUnion(x, y)) {
                ans[0] = x;
                ans[1] = y;
            }
            uf.union(x, y);

        }
        return ans;
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
