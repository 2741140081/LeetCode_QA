package com.marks.leetcode.graph_theory;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3532 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/8 15:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3532 {

    /**
     * @Description:
     * 给你一个整数 n，表示图中的节点数量，这些节点按从 0 到 n - 1 编号。
     * 同时给你一个长度为 n 的整数数组 nums，该数组按 非递减 顺序排序，以及一个整数 maxDiff。
     * 如果满足 |nums[i] - nums[j]| <= maxDiff（即 nums[i] 和 nums[j] 的 绝对差 至多为 maxDiff），则节点 i 和节点 j 之间存在一条 无向边 。
     * 此外，给你一个二维整数数组 queries。对于每个 queries[i] = [ui, vi]，需要判断节点 ui 和 vi 之间是否存在路径。
     * 返回一个布尔数组 answer，其中 answer[i] 等于 true 表示在第 i 个查询中节点 ui 和 vi 之间存在路径，否则为 false。
     * @param: n
     * @param: nums
     * @param: maxDiff
     * @param: queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/01/08 15:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        boolean[] result;
        result = method_01(n, nums, maxDiff, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 查询路径是否存在, 使用并查集
     * 2. 合并时的时间复杂度是 O(n^2), 优化为只合并下一个节点
     * AC: 9ms/162.4MB
     * @param: n
     * @param: nums
     * @param: maxDiff
     * @param: queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2026/01/08 15:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean[] method_01(int n, int[] nums, int maxDiff, int[][] queries) {
        // 创建并查集
        UnionFind uf = new UnionFind(n);
        // 遍历nums
        for (int i = 0; i < nums.length - 1; i++) {
            // 优化, 只用合并下一个即可
            if (nums[i + 1] - nums[i] <= maxDiff) {
                uf.union(i, i + 1);
            }
        }
        int m = queries.length;
        boolean[] ans = new boolean[m];
        for (int i = 0; i < m; i++) {
            int u = queries[i][0];
            int v = queries[i][1];
            ans[i] = uf.isConnected(u, v);
        }
        return ans;
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
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    root[rootY] = rootX;
                } else {
                    root[rootX] = rootY;
                    if (rank[rootX] == rank[rootY]) {
                        rank[rootY]++;
                    }
                }
                count--;
            }
        }

        public int getCount() {
            return count;
        }

        public boolean isConnected(int x, int y) {
            return find(x) == find(y);
        }
    }

}
