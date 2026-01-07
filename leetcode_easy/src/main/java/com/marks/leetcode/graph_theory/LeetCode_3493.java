package com.marks.leetcode.graph_theory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3493 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3493 {

    /**
     * @Description:
     * 给你一个二维整数数组 properties，其维度为 n x m，以及一个整数 k。
     * 定义一个函数 intersect(a, b)，它返回数组 a 和 b 中 共有的不同整数的数量 。
     * 构造一个 无向图，其中每个索引 i 对应 properties[i]。
     * 如果且仅当 intersect(properties[i], properties[j]) >= k（其中 i 和 j 的范围为 [0, n - 1] 且 i != j），节点 i 和节点 j 之间有一条边。
     * 返回结果图中 连通分量 的数量
     * tips:
     * 1 <= n == properties.length <= 100
     * 1 <= m == properties[i].length <= 100
     * 1 <= properties[i][j] <= 100
     * 1 <= k <= m
     * @param: properties
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/07 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfComponents(int[][] properties, int k) {
        int result;
        result = method_01(properties, k);
        return result;
    }

    /**
     * @Description:
     * 1. 并查集找连通分量的个数, 即 uf.getCount()
     * 2. intersect(a, b)，它返回数组 a 和 b 中 共有的不同整数的数量 。 先判断 uf.isConnected(a, b) 如果返回false,
     * 再去执行 intersect(a, b) >= k
     * 3. 怎么快速判断两个集合的交集的元素个数? 对集合进行Set 去重操作, 然后用map<Integer, Set> 存储 , 然后通过两重for 循环判断是否连接
     * AC: 130ms/46.79MB
     * @param: properties
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/01/07 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] properties, int k) {
        // map 和 set 一起进行
        Map<Integer, Set<Integer>> map = new HashMap<>();
        int n = properties.length;
        for (int i = 0; i < n; i++) {
            // 将 int[] 转为 Set
            Set<Integer> set = new HashSet<>();
            int m = properties[i].length;
            for (int j = 0; j < m; j++) {
                set.add(properties[i][j]);
            }
            map.put(i, set);
        }
        // 并查集
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (!uf.isConnected(i, j)) {
                    if (intersect(map.get(i), map.get(j)) >= k) {
                        uf.union(i, j);
                    }
                }
            }
        }
        return uf.count;
    }

    private int intersect(Set<Integer> a, Set<Integer> b) {
        int count = 0;
        // 遍历 b
        for (int num : b) {
            if (a.contains(num)) {
                count++;
            }
        }
        return count;
    }

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
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                if (rank[parentX] > rank[parentY]) {
                    root[parentY] = parentX;
                } else {
                    root[parentX] = parentY;
                    if (rank[parentX] == rank[parentY]) {
                        rank[parentY]++;
                    }
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
