package com.marks.leetcode.data_structure.union_find;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/12 16:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_765 {
    /**
     * @Description:
     * n 对情侣坐在连续排列的 2n 个座位上，想要牵到对方的手。
     *
     * 人和座位由一个整数数组 row 表示，其中 row[i] 是坐在第 i 个座位上的人的 ID。
     * 情侣们按顺序编号，第一对是 (0, 1)，第二对是 (2, 3)，以此类推，最后一对是 (2n-2, 2n-1)。
     *
     * 返回 最少交换座位的次数，以便每对情侣可以并肩坐在一起。 每次交换可选择任意两人，让他们站起来交换座位。
     *
     * tips:
     * 2n == row.length
     * 2 <= n <= 30
     * n 是偶数
     * 0 <= row[i] < 2n
     * row 中所有元素均无重复
     * @param row
     * @return int
     * @author marks
     * @CreateDate: 2025/3/12 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSwapsCouples(int[] row) {
        int result;
        result = method_01(row);
        return result;
    }

    /**
     * @Description:
     * E1:
     * row = [0,2,1,3]
     * 1. 既然是并查集, 那么创建并查集
     * AC: 0ms/41.94MB
     * @param row 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/12 16:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] row) {
        int n = row.length;
        int N = n / 2;
        UnionFind uf = new UnionFind(N);
        for (int i = 0; i < n; i += 2) {
            // 计算的是不同对的数量, 即情侣之间交错首尾排列, A1 B3, B4 C5, D7 C6, D9 A2;
            // 如果是情侣, 例如 E10, E11; row[10] / 2 = 5, row[11] / 2 = 5; 即不会影响uf的 count 值
            uf.union(row[i] / 2, row[i + 1] / 2);
        }
        return N - uf.getCount();
    }

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

        public int getCount() {
            return count;
        }
    }
}
