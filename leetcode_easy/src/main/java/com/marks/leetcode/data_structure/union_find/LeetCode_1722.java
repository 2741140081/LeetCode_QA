package com.marks.leetcode.data_structure.union_find;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/12 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1722 {
    /**
     * @Description:
     * 给你两个整数数组 source 和 target ，长度都是 n 。
     * 还有一个数组 allowedSwaps ，其中每个 allowedSwaps[i] = [ai, bi] 表示你可以交换数组 source 中下标为 ai 和 bi（下标从 0 开始）的两个元素。
     * 注意，你可以按 任意 顺序 多次 交换一对特定下标指向的元素。
     *
     * 相同长度的两个数组 source 和 target 间的 汉明距离 是元素不同的下标数量。形式上，其值等于满足 source[i] != target[i] （下标从 0 开始）的下标 i（0 <= i <= n-1）的数量。
     *
     * 在对数组 source 执行 任意 数量的交换操作后，返回 source 和 target 间的 最小汉明距离 。
     * @param source 
     * @param target 
     * @param allowedSwaps
     * @return int
     * @author marks
     * @CreateDate: 2025/3/12 10:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int result;
        result = method_01(source, target, allowedSwaps);
        return result;
    }

    /**
     * @Description:
     * 存在一个问题
     * [1, 2, 3]
     * [1, 1, 3]
     * 感觉不可能就是简单的 并查集?
     * 输入：source = [5,1,2,4,3],
     * target = [1,5,4,2,3], allowedSwaps = [[0,4],[4,2],[1,3],[1,4]]
     * 输出：0
     * @param source 
     * @param target 
     * @param allowedSwaps 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/12 10:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;
        UnionFind uf = new UnionFind(n);
        for (int[] swap : allowedSwaps) {
            uf.union(swap[0], swap[1]);
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(target[i], k -> new ArrayList<>()).add(i); // 将每个 target[i] 中相同值的下标存储到 List 中
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!map.containsKey(source[i])) {
                ans++;
                continue;
            }

            List<Integer> list = map.get(source[i]);
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext()) {
                Integer index = iterator.next();
                if (uf.isUnion(i, index)) {
                    iterator.remove();
                    ans--;
                    break;
                }
            }
            ans++;
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
