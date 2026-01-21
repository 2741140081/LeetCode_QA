package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2876 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 16:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2876 {

    /**
     * @Description:
     * 现有一个有向图，其中包含 n 个节点，节点编号从 0 到 n - 1 。此外，该图还包含了 n 条有向边。
     * 给你一个下标从 0 开始的数组 edges ，其中 edges[i] 表示存在一条从节点 i 到节点 edges[i] 的边。
     * 想象在图上发生以下过程：
     * 你从节点 x 开始，通过边访问其他节点，直到你在 此过程 中再次访问到之前已经访问过的节点。
     * 返回数组 answer 作为答案，其中 answer[i] 表示如果从节点 i 开始执行该过程，你可以访问到的不同节点数。
     * tips:
     * n == edges.length
     * 2 <= n <= 10^5
     * 0 <= edges[i] <= n - 1
     * edges[i] != i
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/19 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countVisitedNodes(List<Integer> edges) {
        int[] result;
        result = method_01(edges);
        return result;
    }

    /**
     * @Description:
     * 1. 会存在一种继承的关系 a -> b; 假设int[] ans; 存储节点的访问数, 那么 ans[a] += ans[b];
     * 2. 这应该是一个拓扑排序, 但是怎么删除节点? 假设节点的出度为0, 那么这个节点可以被删除. 每删除一个, 给指向改节点的节点的出度减少1,
     * 并且 ans[i] += ans[j]; 假设删除 j,
     * 3. edges[i] 表示 i -> edges[i], 并且只包含一条连接线, 那么可以推断出图必定成环
     * 4. 条件2存在问题, 并不是拓扑排序, 因为每一个节点都会有且仅有一个出度
     * 5. 就需要我们找到环的大小, 并且保存重复的节点(环的入口节点), 假设从0号节点出发, 假设 i 是环路的入口节点,
     * 0 -> i -> j -> k -> i, list[i, j, k] => ans[i] = list.size(); ans[0] += ans[i] + 1(节点本身);
     * 6. 递归 + 深度优先搜索
     * AC: 900ms/124.09MB
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/19 16:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(List<Integer> edges) {
        int n = edges.size();
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            if (ans[i] == 0) {
                boolean[] visited = new boolean[n];
                ans[i] = dfs(i, edges, ans, visited);
            }
        }
        return ans;
    }

    private int dfs(int i, List<Integer> edges, int[] ans, boolean[] visited) {
        int next = edges.get(i);
        if (ans[next] != 0) {
            return ans[i] = ans[next] + 1;
        }
        if (visited[next]) {
            return dfsCycle(ans, edges, next);
        }
        visited[i] = true;
        dfs(next, edges, ans, visited);
        if (ans[i] == 0) {
            ans[i] = ans[next] + 1;
        }

        return ans[i];
    }

    private int dfsCycle(int[] ans, List<Integer> edges, int start) {
        int i = edges.get(start);
        int count = 1;
        while (i != start) {
            i = edges.get(i);
            count++;
        }
        // 给ans[i] 赋值
        ans[start] = count;
        i = edges.get(start);
        while (i != start) {
            ans[i] = count;
            i = edges.get(i);
        }
        return count;
    }

}
