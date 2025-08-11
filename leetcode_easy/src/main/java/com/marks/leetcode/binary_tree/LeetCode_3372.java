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
 * @date 2025/8/4 15:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3372 {
    
    /**
     * @Description:
     * 有两棵 无向 树，分别有 n 和 m 个树节点。
     * 两棵树中的节点编号分别为[0, n - 1] 和 [0, m - 1] 中的整数。
     *
     * 给你两个二维整数 edges1 和 edges2 ，长度分别为 n - 1 和 m - 1 ，
     * 其中 edges1[i] = [ai, bi] 表示第一棵树中节点 ai 和 bi 之间有一条边，edges2[i] = [ui, vi] 表示第二棵树中节点 ui 和 vi 之间有一条边。
     *
     * 同时给你一个整数 k 。
     * 如果节点 u 和节点 v 之间路径的边数小于等于 k ，那么我们称节点 u 是节点 v 的 目标节点 。注意 ，一个节点一定是它自己的 目标节点 。
     * 请你返回一个长度为 n 的整数数组 answer ，
     * answer[i] 表示将第一棵树中的一个节点与第二棵树中的一个节点连接一条边后，第一棵树中节点 i 的 目标节点 数目的 最大值 。
     *
     * 注意 ，每个查询相互独立。意味着进行下一次查询之前，你需要先把刚添加的边给删掉。
     *
     * tips:
     * 2 <= n, m <= 1000
     * edges1.length == n - 1
     * edges2.length == m - 1
     * edges1[i].length == edges2[i].length == 2
     * edges1[i] = [ai, bi]
     * 0 <= ai, bi < n
     * edges2[i] = [ui, vi]
     * 0 <= ui, vi < m
     * 输入保证 edges1 和 edges2 都表示合法的树。
     * 0 <= k <= 1000
     * @param edges1 
     * @param edges2 
     * @param k
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/4 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] maxTargetNodes(int[][] edges1, int[][] edges2, int k) {
        int[] result;
        result = method_01(edges1, edges2, k);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：edges1 = [[0,1],[0,2],[2,3],[2,4]], edges2 = [[0,1],[0,2],[0,3],[2,7],[1,4],[4,5],[4,6]], k = 2
     * 输出：[9,7,9,8,8]
     *
     * 1. 针对第二棵树, 对每个节点, 存储该节点到其它节点的路径值小于等于 k - 1的数量
     *
     * AC: 326ms(60.50%)/45.98MB(23.72%)
     * @param edges1 
     * @param edges2 
     * @param k 
     * @return int[]
     * @author marks
     * @CreateDate: 2025/8/4 15:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int[][] edges1, int[][] edges2, int k) {
        int n = edges1.length + 1;
        int m = edges2.length + 1;

        // 构建邻接表
        List<Integer>[] lists1 = new List[n];
        List<Integer>[] lists2 = new List[m];

        for (int i = 0; i < n; i++) {
            lists1[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            lists2[i] = new ArrayList<>();
        }

        for (int[] ed : edges1) {
            lists1[ed[0]].add(ed[1]);
            lists1[ed[1]].add(ed[0]);
        }

        for (int[] ed : edges2) {
            lists2[ed[0]].add(ed[1]);
            lists2[ed[1]].add(ed[0]);
        }
        int max = 0;
        for (int i = 0; i < lists2.length; i++) {
            boolean[] used = new boolean[m];
            max = Math.max(max, dfs(i, used, lists2, k - 1));
        }

        int[] ans = new int[n];
        for (int i = 0; i < lists1.length; i++) {
            boolean[] used = new boolean[n];
            ans[i] = dfs(i, used, lists1, k) + max;
        }

        return ans;
    }

    private int dfs(int root, boolean[] used, List<Integer>[] lists, int k) {
        if (k < 0 || used[root]) {
            return 0;
        }
        int count = 1; // 初始为1
        used[root] = true;

        for (int next : lists[root]) {
            if (!used[next]) {
                count += dfs(next, used, lists, k - 1);
            }
        }
        return count;
    }
}
