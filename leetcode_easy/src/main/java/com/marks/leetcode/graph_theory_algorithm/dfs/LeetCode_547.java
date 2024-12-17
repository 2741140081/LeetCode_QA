package com.marks.leetcode.graph_theory_algorithm.dfs;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/17 14:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_547 {
    private int[][] pre;
    /**
     * @Description: [
     * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
     * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
     *
     * 给你一个 n x n 的矩阵 isConnected ，
     * 其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
     * 返回矩阵中 省份 的数量。
     *
     * tips:
     * 1 <= n <= 200
     * n == isConnected.length
     * n == isConnected[i].length
     * isConnected[i][j] 为 1 或 0
     * isConnected[i][i] == 1
     * isConnected[i][j] == isConnected[j][i]
     * ]
     * @param isConnected
     * @return int
     * @author marks
     * @CreateDate: 2024/12/17 14:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findCircleNum(int[][] isConnected) {
        int result;
//        result = method_01(isConnected);
        result = method_02(isConnected);
        return result;
    }

    /**
     * @Description: [
     * 并查集, AC:2ms/46.84MB
     * ]
     * @param isConnected
     * @return int
     * @author marks
     * @CreateDate: 2024/12/17 15:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getCount();
    }

    /**
     * @Description: [
     * E1:
     * isConnected = [
     * [1,1,0],
     * [1,1,0],
     * [0,0,1]]
     * AC:31ms/46.93MB
     * 感觉时间复杂度有点高了, 看看官解
     * ]
     * @param isConnected
     * @return int
     * @author marks
     * @CreateDate: 2024/12/17 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] isConnected) {
        int n = isConnected.length;
        pre = new int[n][n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isConnected[i][j] == 1 && pre[i][j] == 0) {
                    DFSMaxArea(i, j, isConnected);
                    ans++;
                }
            }
        }
        return ans;
    }

    /**
     * @Description: [
     * [1,1,0],
     * [1,1,0],
     * [0,0,1]
     * 这个案例(0, 0)
     * 遍历(0, j) 和 (i, 0)
     * ]
     * @param i
     * @param j
     * @param isConnected
     * @return void
     * @author marks
     * @CreateDate: 2024/12/17 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private void DFSMaxArea(int i, int j, int[][] isConnected) {
        if (!inArea(i, j, isConnected) || pre[i][j] == 1 || isConnected[i][j] == 0) {
            return;
        }
        pre[i][j] = 1;
        for (int row = 0; row < isConnected.length; row++) {
            DFSMaxArea(row, j, isConnected);
        }
        for (int col = 0; col < isConnected.length; col++) {
            DFSMaxArea(i, col, isConnected);
        }

    }

    private boolean inArea(int i, int j, int[][] grid) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length) {
            return true;
        }
        return false;
    }

    class UnionFind {
        private int[] root; // 记录每个节点的父节点
        private int[] rank;   // 记录每个节点的高度（父节点的数目）
        private int count;

        public UnionFind(int size) {
            root = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                root[i] = i;
                rank[i] = 1; // 可选, 初始化高度 0/1
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

        public int getCount() {
            return count;
        }
    }
}

