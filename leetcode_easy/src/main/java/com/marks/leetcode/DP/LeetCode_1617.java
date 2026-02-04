package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1617 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/4 11:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1617 {

    /**
     * @Description:
     * 给你 n 个城市，编号为从 1 到 n 。同时给你一个大小为 n-1 的数组 edges ，其中 edges[i] = [ui, vi] 表示城市 ui 和 vi 之间有一条双向边。
     * 题目保证任意城市之间只有唯一的一条路径。换句话说，所有城市形成了一棵 树 。
     * 一棵 子树 是城市的一个子集，且子集中任意城市之间可以通过子集中的其他城市和边到达。
     * 两个子树被认为不一样的条件是至少有一个城市在其中一棵子树中存在，但在另一棵子树中不存在。
     * 对于 d 从 1 到 n-1 ，请你找到城市间 最大距离 恰好为 d 的所有子树数目。
     * 请你返回一个大小为 n-1 的数组，其中第 d 个元素（下标从 1 开始）是城市间 最大距离 恰好等于 d 的子树数目。
     * 请注意，两个城市间距离定义为它们之间需要经过的边的数目。
     * @param: n
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/04 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        int[] result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 直接查看官方题解, 使用动态规划 + 状态压缩
     * @param: n
     * @param: edges
     * @return int[]
     * @author marks
     * @CreateDate: 2026/02/04 11:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int mask; // 记录子树的状态
    private int diameter; // 记录子树的最大直径
    private int[] method_01(int n, int[][] edges) {
        // 建图
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0] - 1;
            int v = edge[1] - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        int[] ans = new int[n - 1];
        for (int i = 1; i < (1 << n) ; i++) {
            // 找到子树中第一个为1的节点作为根节点, 然后进行深度优先搜索
            int root = 32 - Integer.numberOfLeadingZeros(i) - 1; // 看错了, 应该调用的是前导零的数量
            mask = i;
            diameter = 0;
            dfs(root, graph);
            if (mask == 0 && diameter > 1) {
                // 子树所有节点都被遍历到了, 并且diameter > 1
                ans[diameter - 1]++;
            }
        }

        return ans;
    }

    private int dfs(int root, List<Integer>[] graph) {
        int first = 0, second = 0;
        mask &= ~(1 << root); // 将mask中root位置的1变为0
        for (int v : graph[root]) {
            if ((mask & (1 << v)) != 0) {
                // 节点存在
                mask &= ~(1 << v); // 删除该节点
                int distance = 1 + dfs(v, graph);
                if (distance > first) {
                    second = first;
                    first = distance;
                } else if (distance > second) {
                    second = distance;
                }
            }
        }
        diameter = Math.max(diameter, first + second);
        return first;
    }

}
