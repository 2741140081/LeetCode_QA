package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1697 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/30 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1697 {

    /**
     * @Description:
     * 给你一个 n 个点组成的无向图边集 edgeList ，其中 edgeList[i] = [ui, vi, disi] 表示点 ui 和点 vi 之间有一条长度为 disi 的边。
     * 请注意，两个点之间可能有 超过一条边 。
     *
     * 给你一个查询数组queries ，其中 queries[j] = [pj, qj, limitj] ，你的任务是对于每个查询 queries[j] ，
     * 判断是否存在从 pj 到 qj 的路径，且这条路径上的每一条边都 严格小于 limitj 。
     *
     * 请你返回一个 布尔数组 answer ，其中 answer.length == queries.length ，
     * 当 queries[j] 的查询结果为 true 时， answer 第 j 个值为 true ，否则为 false 。
     * @param: n
     * @param: edgeList
     * @param: queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2025/12/30 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
        boolean[] result;
        result = method_01(n, edgeList, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 题目理解错了, 刚开始以为是求节点之间的最短距离, 但是是求节点之间是否连通
     * 2. queries[i] = [ai, bi, limiti] 是限制边的最大距离, 而不是求节点之间最短距离
     * 3. 使用并查集求解点之间的连通性
     * AC: 84ms/127.95MB
     * @param: n
     * @param: edgeList
     * @param: queries
     * @return boolean[]
     * @author marks
     * @CreateDate: 2025/12/30 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean[] method_01(int n, int[][] edgeList, int[][] queries) {
        int len = queries.length;
        int m = edgeList.length;
        boolean[] ans = new boolean[len];
        // 对 edgeList 进行排序
        Arrays.sort(edgeList, (a, b) -> a[2] - b[2]); // 升序
        Integer[] index = new Integer[len]; // 索引
        for (int i = 0; i < len; i++) {
            index[i] = i;
        }
        // 根据queries[i][2] 升序排序
        Arrays.sort(index, (a, b) -> queries[a][2] - queries[b][2]); // 升序
        int[] uf = new int[n];
        // 初始化并查集
        for (int i = 0; i < n; i++) {
            uf[i] = i;
        }
        int idx = 0;
        for (Integer i : index) {
            int limit = queries[i][2]; // 最小的query 限制
            // 将满足的边添加到并查集中
            while (idx < m && edgeList[idx][2] < limit) {
                merge(uf, edgeList[idx][0], edgeList[idx][1]);
                idx++;
            }
            ans[i] = find(uf, queries[i][0]) == find(uf, queries[i][1]);
        }
        return ans;
    }

    private void merge(int[] uf, int x, int y) {
        int rootX = find(uf, x);
        int rootY = find(uf, y);
        if (rootX != rootY) {
            uf[rootX] = rootY;
        }
    }

    private int find(int[] uf, int x) {
        if (uf[x] == x) {
            return x;
        }
        return uf[x] = find(uf, uf[x]);
    }

}
