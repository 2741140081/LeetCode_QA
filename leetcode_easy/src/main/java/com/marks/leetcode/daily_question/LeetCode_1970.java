package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1970 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/31 14:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1970 {

    /**
     * @Description:
     * 给你一个下标从 1 开始的二进制矩阵，其中 0 表示陆地，1 表示水域。同时给你 row 和 col 分别表示矩阵中行和列的数目。
     *
     * 一开始在第 0 天，整个 矩阵都是 陆地 。但每一天都会有一块新陆地被 水 淹没变成水域。
     * 给你一个下标从 1 开始的二维数组 cells ，其中 cells[i] = [ri, ci] 表示在第 i 天，第 ri 行 ci 列（下标都是从 1 开始）的陆地会变成 水域 （也就是 0 变成 1 ）。
     *
     * 你想知道从矩阵最 上面 一行走到最 下面 一行，且只经过陆地格子的 最后一天 是哪一天。
     * 你可以从最上面一行的 任意 格子出发，到达最下面一行的 任意 格子。你只能沿着 四个 基本方向移动（也就是上下左右）。
     *
     * 请返回只经过陆地格子能从最 上面 一行走到最 下面 一行的 最后一天 。
     * tips:
     * 2 <= row, col <= 2 * 10^4
     * 4 <= row * col <= 2 * 10^4
     * cells.length == row * col
     * 1 <= ri <= row
     * 1 <= ci <= col
     * cells 中的所有格子坐标都是 唯一 的。
     * @param: row
     * @param: col
     * @param: cells
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int latestDayToCross(int row, int col, int[][] cells) {
        int result;
        result = method_01(row, col, cells);
        return result;
    }

    /**
     * @Description:
     * 差不多理解题目
     * 1. 并查集, 初始时, 所有节点之间都是互通的, 即可以从顶部节点移动到底部节点
     * 2. 创建一个超级节点 s 和 t, s 与 第一行节点连接, t 与 最后一行节点连接
     * 3. cells.length == row * col, 证明最终所有的陆地都会被水域覆盖
     * 4. 反过来看, 倒序遍历cells, 就相当于找到一条路径, 从顶部节点到底部节点, uf.isConnected(s, t) == true, 返回 i
     * AC: 18ms/77.49MB
     * @param: row
     * @param: col
     * @param: cells
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 14:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int row, int col, int[][] cells) {
        int n = row * col;
        UnionFind uf = new UnionFind(n + 2);
        int s = n, t = n + 1;
        int[][] grid = new int[row][col]; // 被水域淹没的节点
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}}; // 上下左右
        // 倒序遍历cells
        for (int i = cells.length - 1; i >= 0; i--) {
            int x = cells[i][0] - 1; // 节点从1开始
            int y = cells[i][1] - 1;
            int idx = x * col + y; // 将二维坐标转换为一维
            grid[x][y] = 1; // 标记为陆地
            for (int[] dir : dirs) {
                int nx = x + dir[0];
                int ny = y + dir[1];
                if (nx < 0 || nx >= row || ny < 0 || ny >= col || grid[nx][ny] == 0) {
                    continue;
                }
                int nidx = nx * col + ny;
                uf.union(idx, nidx);
            }
            // 如果 x == 0, 则将 s 与 idx 连通
            if (x == 0) {
                uf.union(s, idx);
            }
            // 如果 x == row - 1, 则将 t 与 idx 连通
            if (x == row - 1) {
                uf.union(t, idx);
            }
            // 判断是否连通
            if (uf.isConnected(s, t)) {
                return i;
            }

        }
        return 0;
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
