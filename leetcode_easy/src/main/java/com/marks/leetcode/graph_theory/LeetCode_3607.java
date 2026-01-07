package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3607 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3607 {

    /**
     * @Description:
     * 给你一个整数 c，表示 c 个电站，每个电站有一个唯一标识符 id，从 1 到 c 编号。
     * 这些电站通过 n 条 双向 电缆互相连接，表示为一个二维数组 connections，
     * 其中每个元素 connections[i] = [ui, vi] 表示电站 ui 和电站 vi 之间的连接。直接或间接连接的电站组成了一个 电网 。
     *
     * 最初，所有 电站均处于在线（正常运行）状态。
     * 另给你一个二维数组 queries，其中每个查询属于以下 两种类型之一 ：
     * [1, x]：请求对电站 x 进行维护检查。如果电站 x 在线，则它自行解决检查。如果电站 x 已离线，则检查由与 x 同一 电网 中 编号最小 的在线电站解决。
     * 如果该电网中 不存在 任何 在线 电站，则返回 -1。
     *
     * [2, x]：电站 x 离线（即变为非运行状态）。
     * 返回一个整数数组，表示按照查询中出现的顺序，所有类型为 [1, x] 的查询结果。
     * 注意：电网的结构是固定的；离线（非运行）的节点仍然属于其所在的电网，且离线操作不会改变电网的连接性。
     * @param: c
     * @param: connections
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/07 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        int[] result;
        result = method_01(c, connections, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 可能存在多个电网，且所有电站都处于运行状态
     * 2. 需要给同一个电网的电站进行标记, 例如有0号电网, 包含 n1, n2, n3 电站.
     * 3. 既然是求解连通性问题, 那么必不可少的是使用并查集, 将同一个电网的电站进行标记
     * 4. 使用map存储电站的标记, map.put(uf.find(c[i]), value), value 使用一个优先队列进行存储(小根堆)
     * 5. 将遍历过程中位2的电站添加到Set 集合中(非运行状态), 通过map.get(uf.find(c[i])) 来获取优先队列,
     * 并且如多优先队列的peek() 值存在于 set 集合中, 弹出顶部值, 直到队列不在set 或者队列为空, 如果队列为 空, 返回 -1
     * AC: 112ms/273.29MB
     * @param: c
     * @param: connections
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/07 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int c, int[][] connections, int[][] queries) {
        // 构建并查集
        UnionFind uf = new UnionFind(c + 1); // 电站从1 开始
        for (int[] connection : connections) {
            uf.union(connection[0], connection[1]);
        }
        // 遍历电站, 存储到map 中
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();
        for (int i = 1; i <= c; i++) {
            int key = uf.find(i);
            map.computeIfAbsent(key, k -> new PriorityQueue<>()).offer(i);
        }
        // set 集合 存储离线的电站
        Set<Integer> offline = new HashSet<>();
        // 结果集合
        List<Integer> ans = new ArrayList<>();
        for (int[] query : queries) {
            if (query[0] == 1) {
                int curr = query[1]; // 当前待查询的电站
                if (offline.contains(curr)) {
                    // 当前电站已离线, 需要电网中运行状态下的最小id 的电站来处理
                    int key = uf.find(curr);
                    PriorityQueue<Integer> queue = map.get(key);
                    while (!queue.isEmpty() && offline.contains(queue.peek())) {
                        queue.poll();
                    }
                    if (queue.isEmpty()) {
                        ans.add(-1);
                    } else {
                        ans.add(queue.peek());
                    }
                } else {
                    // 当前电站在线, 可以自行解决
                    ans.add(curr);
                }
            } else {
                // query[0] == 2
                offline.add(query[1]);
            }
        }
        // 将 List 转为 int[]
        return ans.stream().mapToInt(Integer::intValue).toArray();
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
            int parentX = find(x);
            int parentY = find(y);
            if (parentX != parentY) {
                if (rank[parentX] > rank[parentY]) {
                    root[parentY] = parentX;
                } else if (rank[parentX] < rank[parentY]) {
                    root[parentX] = parentY;
                } else {
                    root[parentY] = parentX;
                    rank[parentX] += 1;
                }
                count--;
            }
        }
    }

}
