package com.marks.leetcode.binary_tree;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/6 15:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1519 {

    private int[] ans;

    /**
     * @Description:
     * 给你一棵树（即，一个连通的无环无向图），这棵树由编号从 0  到 n - 1 的 n 个节点组成，且恰好有 n - 1 条 edges 。
     * 树的根节点为节点 0 ，树上的每一个节点都有一个标签，也就是字符串 labels 中的一个小写字符（编号为 i 的 节点的标签就是 labels[i] ）
     *
     * 边数组 edges 以 edges[i] = [ai, bi] 的形式给出，该格式表示节点 ai 和 bi 之间存在一条边。
     * 返回一个大小为 n 的数组，其中 ans[i] 表示第 i 个节点的子树中与节点 i 标签相同的节点数。
     *
     * 树 T 中的子树是由 T 中的某个节点及其所有后代节点组成的树。
     * @param n
     * @param edges
     * @param labels
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/6 15:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        int[] result;
        result = method_01(n, edges, labels);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 7, edges = [[0,1],[0,2],[1,4],[1,5],[2,3],[2,6]], labels = "abaedcd"
     * 输出：[2,1,1,1,1,1,1]
     *
     * 1. 构建邻接表, 通过dfs遍历整颗树
     * 2. visited[i] 表示节点i是否被访问过, 可以不需要create visited[] 数组, 通过dfs的参数来判断
     * 3. 之前通过map存储存在问题, 因为通过map作为参数存储节点的子树信息, map整个会存储所有节点的信息, 而不是当前节点的子树信息
     * 4. 所以需要修改map, 将map替换为通过数组来存储节点的子树信息
     * AC: 60ms(62.25%)/117.88MB(46.88%)
     * @param n
     * @param edges
     * @param labels
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/6 15:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] edges, String labels) {
        List<Integer>[] graph = buildGraph(n, edges);
        ans = new int[n];

        dfs(0, -1, graph, labels);
        return ans;
    }

    private List<Integer>[] buildGraph(int n, int[][] edges) {
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

    private int[] dfs(int node, int parent, List<Integer>[] graph, String labels) {
        // 统计当前节点子树中每个字母的数量
        int[] count = new int[26];
        char currentNodeLabel = labels.charAt(node);
        count[currentNodeLabel - 'a'] = 1; // 当前节点的标签计数为1

        // 遍历所有子节点
        for (int child : graph[node]) {
            if (child != parent) { // 避免回到父节点
                int[] childCount = dfs(child, node, graph, labels);
                // 将子节点的统计结果加到当前节点上
                for (int i = 0; i < 26; i++) {
                    count[i] += childCount[i];
                }
            }
        }

        // 设置当前节点的结果
        ans[node] = count[currentNodeLabel - 'a'];
        return count;
    }
}