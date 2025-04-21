package com.marks.leetcode.graph_theory_algorithm;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1584 {
    /**
     * @Description: [
     * 给你一个points 数组，表示 2D 平面上的一些点，其中 points[i] = [xi, yi] 。
     *
     * 连接点 [xi, yi] 和点 [xj, yj] 的费用为它们之间的 曼哈顿距离 ：|xi - xj| + |yi - yj| ，其中 |val| 表示 val 的绝对值。
     *
     * 请你返回将所有点连接的最小总费用。只有任意两点之间 有且仅有 一条简单路径时，才认为所有点都已连接。
     * ]
     * @param points 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCostConnectPoints(int[][] points) {
        int result;
        result = method_01(points);
        return result;
    }

    /**
     * @Description: [
     * AC:458ms/66.07MB
     * ]
     * @param points
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 14:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] points) {
        int n = points.length;
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                int dist = Math.abs(x1 - x2) + Math.abs(y1 - y2);
                edges.add(new int[]{i, j, dist});
            }
        }

        Collections.sort(edges, Comparator.comparingInt(o -> o[2])); // 升序拍戏dist, 找到权值最小的边, 并且将边添加到树中

        UnionFind uf = new UnionFind(n);
        int ans = 0;
        for (int[] edge : edges) {
            int i = edge[0];
            int j = edge[1];
            if (uf.isConnected(i, j)) {
                continue;
            }
            uf.union(i, j);
            ans += edge[2];
        }
        return ans;
    }

    class UnionFind {
        private int[] root;
        private int[] rank;

        public UnionFind(int size) {
            this.root = new int[size];
            this.rank = new int[size];
            for (int i = 0; i < size; i++) {
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
                } else if (rank[rootX] < rank[rootY]) {
                    root[rootX] = rootY;
                } else {
                    root[rootY] = rootX;
                    rank[rootX] += 1;
                }
            }
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }

    }


}
