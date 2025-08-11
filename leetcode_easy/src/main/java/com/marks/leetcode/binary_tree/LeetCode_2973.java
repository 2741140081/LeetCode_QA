package com.marks.leetcode.binary_tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/8 17:09
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2973 {

    private long[] ans;

    /**
     * @Description:
     * 给你一棵 n 个节点的 无向 树，节点编号为 0 到 n - 1 ，树的根节点在节点 0 处。
     * 同时给你一个长度为 n - 1 的二维整数数组 edges ，其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间有一条边。
     *
     * 给你一个长度为 n 下标从 0 开始的整数数组 cost ，其中 cost[i] 是第 i 个节点的 开销 。
     * 你需要在树中每个节点都放置金币，在节点 i 处的金币数目计算方法如下：
     *
     * 如果节点 i 对应的子树中的节点数目小于 3 ，那么放 1 个金币。
     * 否则，计算节点 i 对应的子树内 3 个不同节点的开销乘积的 最大值 ，并在节点 i 处放置对应数目的金币。如果最大乘积是 负数 ，那么放置 0 个金币。
     * 请你返回一个长度为 n 的数组 coin ，coin[i]是节点 i 处的金币数目。
     *
     * tips:
     * 2 <= n <= 2 * 10^4
     * edges.length == n - 1
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * cost.length == n
     * 1 <= |cost[i]| <= 10^4
     * edges 一定是一棵合法的树。
     * @param edges
     * @param cost
     * @return long[]
     * @author marks
     * @CreateDate: 2025/8/8 17:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long[] placedCoins(int[][] edges, int[] cost) {
        long[] result;
        result = method_01(edges, cost);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges = [[0,1],[0,2],[1,3],[1,4],[1,5],[2,6],[2,7],[2,8]], cost = [1,4,2,3,5,7,8,-4,2]
     * 输出：[280,140,32,1,1,1,1,1,1]
     * 1. 根据AI的提示, 我的思考有漏洞, 即没有考虑负数的情况, 需要保留两个最小的负数和3个最大的数。
     * 2. 假设数按照从小到大排序, ans = Math.max(a1 * a2 * a5, a3 * a4 * a5)
     * 3. 时间复杂度是 O(n^2 * log n), 感觉应该是超时了
     * 4. 还真通过了, AC: 299ms(9.09%)/68.03MB(9.09%)
     * @param edges
     * @param cost
     * @return long[]
     * @author marks
     * @CreateDate: 2025/8/8 17:10
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long[] method_01(int[][] edges, int[] cost) {
        List<Integer>[] graph = buildGraph(edges);
        int n = edges.length + 1;
        ans = new long[n];
        dfs(0, -1, graph, cost);
        return ans;
    }

    private List<Integer> dfs(int node, int parent, List<Integer>[] graph, int[] cost) {
        List<List<Integer>> pList = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        curr.add(cost[node]);
        pList.add(curr);

        for (int child : graph[node]) {
            if (child != parent) {
                List<Integer> childList = dfs(child, node, graph, cost);
                pList.add(childList);
            }
        }

        int[] result = mergeKSortedLists(pList);
        if (result.length > 4) {
            ans[node] = Math.max(0, Math.max((long) result[0] * result[1] * result[4], (long) result[2] * result[3] * result[4]));
        } else if (result.length == 4){
            ans[node] = Math.max(0, Math.max((long) result[0] * result[1] * result[3], (long) result[1] * result[2] * result[3]));
        } else if (result.length == 3) {
            ans[node] = Math.max(0, result[0] * result[1] * result[2]);
        } else {
            ans[node] = 1;
        }

        return Arrays.stream(result).boxed().collect(Collectors.toList());
    }

    private int[] mergeKSortedLists(List<List<Integer>> lists) {
        // 使用优先队列（最小堆）来维护各个列表的当前最小元素
        PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        // 初始化堆，将每个列表的第一个元素加入堆中
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null && !lists.get(i).isEmpty()) {
                minHeap.offer(new int[]{lists.get(i).get(0), i, 0});
            }
        }

        List<Integer> result = new ArrayList<>();

        // 当堆不为空时，不断取出最小元素，并将该元素所在列表的下一个元素加入堆中
        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int value = current[0];
            int listIndex = current[1];
            int elementIndex = current[2];

            result.add(value);

            // 如果当前列表还有下一个元素，则将其加入堆中
            if (elementIndex + 1 < lists.get(listIndex).size()) {
                minHeap.offer(new int[]{
                        lists.get(listIndex).get(elementIndex + 1),
                        listIndex,
                        elementIndex + 1
                });
            }
        }
        int[] temp;
        if (result.size() >= 5) {
            // List.of 方法是Java 9中引入的，用于创建不可变列表。当前java 8 不能使用
            // a = List.of(a.get(0), a.get(1), a.get(m - 3), a.get(m - 2), a.get(m - 1));
            temp = new int[5];
            temp[0] = result.get(0);
            temp[1] = result.get(1);
            int left = result.size() - 1;
            for (int i = 4; i > 1; i--) {
                temp[i] = result.get(left--);
            }
        } else {
            temp = result.stream().mapToInt(Integer::intValue).toArray();
        }
        return temp;
    }


    private List<Integer>[] buildGraph(int[][] edges) {
        int n = edges.length + 1;
        List<Integer>[] graph = new List[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int a = edge[0];
            int b = edge[1];
            graph[a].add(b);
            graph[b].add(a);
        }

        return graph;
    }
}
