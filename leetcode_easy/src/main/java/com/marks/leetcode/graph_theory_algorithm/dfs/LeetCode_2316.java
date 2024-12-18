package com.marks.leetcode.graph_theory_algorithm.dfs;


/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/18 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2316 {
    /**
     * @Description: [
     * 给你一个整数 n ，表示一张 无向图 中有 n 个节点，编号为 0 到 n - 1 。同时给你一个二维整数数组 edges ，
     * 其中 edges[i] = [ai, bi] 表示节点 ai 和 bi 之间有一条 无向 边。
     *
     * 请你返回 无法互相到达 的不同 点对数目 。
     * ]
     * @param n 
     * @param edges
     * @return long
     * @author marks
     * @CreateDate: 2024/12/18 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countPairs(int n, int[][] edges) {
        long result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description: [
     * 被n = 100000, edges = []; 超时, 我去!!!
     * ]
     * @param n
     * @param edges
     * @return long
     * @author marks
     * @CreateDate: 2024/12/18 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[][] edges) {
        UnionF uf = new UnionF(n);
        for (int i = 0; i < edges.length; i++) {
            uf.union(edges[i][0], edges[i][1]);
        }
        long res = 0;
        for (int i = 0; i < n; i++) {
            // 3 * 4 = 12 + 5 * 2 + 6 =28
            res += n - uf.getSize(uf.find(i));
        }
        // 每个组合实际上被计算了两次（例如，(a,b)和(b,a)被视为同一种组合，但上述计算方式会分别计算它们一次）。因此，我们需要将总数除以2来得到正确的组合数。
        return res / 2;
    }

    class UnionF {
        private int[] root;
        private int[] rank;

        public UnionF(int size) {
            root = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                root[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int x) {
            if (root[x] == x) {
                return x;
            } else {
                root[x] = find(root[x]);
                return root[x];
            }

        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    root[rootY] = rootX;
                    rank[rootX] += rank[rootY];
                } else {
                    root[rootX] = rootY;
                    rank[rootY] += rank[rootX];
                }
            }
        }

        public int getSize(int x) {
            return rank[x];
        }
    }
}
