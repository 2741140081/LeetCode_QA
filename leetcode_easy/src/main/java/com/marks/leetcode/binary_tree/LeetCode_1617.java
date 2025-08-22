package com.marks.leetcode.binary_tree;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/14 9:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1617 {

    /**
     * @Description:
     * 给你 n 个城市，编号为从 1 到 n 。同时给你一个大小为 n-1 的数组 edges ，其中 edges[i] = [ui, vi] 表示城市 ui 和 vi 之间有一条双向边。
     * 题目保证任意城市之间只有唯一的一条路径。换句话说，所有城市形成了一棵 树 。
     *
     * 一棵 子树 是城市的一个子集，且子集中任意城市之间可以通过子集中的其他城市和边到达。
     * 两个子树被认为不一样的条件是至少有一个城市在其中一棵子树中存在，但在另一棵子树中不存在。
     *
     * 对于 d 从 1 到 n-1 ，请你找到城市间 最大距离 恰好为 d 的所有子树数目。
     *
     * 请你返回一个大小为 n-1 的数组，其中第 d 个元素（下标从 1 开始）是城市间 最大距离 恰好等于 d 的子树数目。
     *
     * 请注意，两个城市间距离定义为它们之间需要经过的边的数目。
     *
     * tips:
     * 2 <= n <= 15
     * edges.length == n-1
     * edges[i].length == 2
     * 1 <= ui, vi <= n
     * 题目保证 (ui, vi)
     * @param n 
     * @param edges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/14 9:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        int[] result;
        result = method_01(n, edges);
        result = method_02(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 用回溯法来试试看,
     *
     * @param n
     * @param edges
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/21 10:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_02(int n, int[][] edges) {
        return new int[0];
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 4, edges = [[1,2],[2,3],[2,4]]
     * 输出：[3,4,0]
     * 1. 构建二叉树, boolean[] visited, 存储访问过的节点, int[] ans, ans[0] = n - 1, 另外假设数的最大直径是 d, ans[d + 1] ~ ans[n - 1] 全部赋值为0
     * 2. 第一个for循环, 从 1开始, 进行dfs遍历, 返回值是 List<Integer> list, 存储dfs遍历过的节点, 这种感觉会有部分结果无法遍历到
     * 3. 想到一个思路是, 从各个节点开始遍历, 但是这种会重复计算节点的边, 这个方法不一定不可行, 只要解决重复问题就行, 太复杂了.
     * 4. 后续在处理吧 need todo
     * @param n 
     * @param edges 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/14 9:34
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] edges) {
        List<Integer>[] graph = buildGraph(n, edges);
        
        return new int[0];
    }

    private List<Integer>[] buildGraph(int n, int[][] edges) {
        List<Integer>[] graph = new List[n + 1];
        for (int i = 0; i < n + 1; i++) {
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
