package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2924 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/31 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2924 {

    /**
     * @Description:
     * 一场比赛中共有 n 支队伍，按从 0 到  n - 1 编号。每支队伍也是 有向无环图（DAG） 上的一个节点。
     * 给你一个整数 n 和一个下标从 0 开始、长度为 m 的二维整数数组 edges 表示这个有向无环图，其中 edges[i] = [ui, vi] 表示图中存在一条从 ui 队到 vi 队的有向边。
     * 从 a 队到 b 队的有向边意味着 a 队比 b 队 强 ，也就是 b 队比 a 队 弱 。
     * 在这场比赛中，如果不存在某支强于 a 队的队伍，则认为 a 队将会是 冠军 。
     * 如果这场比赛存在 唯一 一个冠军，则返回将会成为冠军的队伍。否则，返回 -1 。
     *
     * 注意
     * 环 是形如 a1, a2, ..., an, an+1 的一个序列，且满足：节点 a1 与节点 an+1 是同一个节点；节点 a1, a2, ..., an 互不相同；
     * 对于范围 [1, n] 中的每个 i ，均存在一条从节点 ai 到节点 ai+1 的有向边。
     * 有向无环图 是不存在任何环的有向图。
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findChampion(int n, int[][] edges) {
        int result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. u -> v 的有向边, 表示 u 比 v 强, 如果用入度统计, 则 inDegree[v]++
     * 2. 只需要找到是否存在入度为 0 的点
     * AC: 1ms/47.51MB
     * @param: n
     * @param: edges
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] edges) {
        List<Integer> ans = new ArrayList<>(); // 存储入度为 0 的点
        int[] inDegree = new int[n];
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            inDegree[v]++;
        }
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                ans.add(i);
            }
        }
        return ans.size() == 1 ? ans.get(0) : -1;
    }

}
