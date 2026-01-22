package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3530 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/22 17:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3530 {

    /**
     * @Description:
     * 给你一个由 n 个节点组成的有向无环图（DAG），节点编号从 0 到 n - 1，
     * 通过二维数组 edges 表示，其中 edges[i] = [ui, vi] 表示一条从节点 ui 指向节点 vi 的有向边。
     * 每个节点都有一个对应的 得分 ，由数组 score 给出，其中 score[i] 表示节点 i 的得分。
     * 你需要以 有效的拓扑排序 顺序处理这些节点。每个节点在处理顺序中被分配一个编号从 1 开始的位置。
     * 将每个节点的得分乘以其在拓扑排序中的位置，然后求和，得到的值称为 利润。
     * 请返回在所有合法拓扑排序中可获得的 最大利润 。
     * 拓扑排序 是一个对 DAG 中所有节点的线性排序，使得每条有向边 u → v 中，节点 u 都出现在 v 之前。
     * tips:
     * 1 <= n == score.length <= 22
     * 1 <= score[i] <= 10^5
     * 0 <= edges.length <= n * (n - 1) / 2
     * edges[i] == [ui, vi] 表示一条从 ui 到 vi 的有向边。
     * 0 <= ui, vi < n
     * ui != vi
     * 输入图 保证 是一个 DAG。
     * 不存在重复的边。
     * @param: n
     * @param: edges
     * @param: score
     * @return int
     * @author marks
     * @CreateDate: 2026/01/22 17:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int n, int[][] edges, int[] score) {
        int result;
        result = method_01(n, edges, score);
        return result;
    }

    /**
     * @Description:
     * E1: 输入： n = 3, edges = [[0,1],[0,2]], score = [1,6,3]
     * 输出： 25
     * 1. 建立一个入度 int[] inDegree; 然后用优先队列存储 score[i] 并且 inDegree[i] == 0 的节点
     * 2. int index = 1; 编号索引
     * 3. 贪心不正确, 需要用动态规划来求解, 数据范围不是很大, 但是应该也不支持回溯, 那么能用的就是状态压缩了
     * 4. 然后怎么处理? dp[i + 1][mask] = Math.max(dp[i][mask_i0])
     * @param: n
     * @param: edges
     * @param: score
     * @return int
     * @author marks
     * @CreateDate: 2026/01/22 17:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    private int method_01(int n, int[][] edges, int[] score) {
        if (edges.length == 0) {
            // 特殊处理
            Arrays.sort(score);
            int ans = 0;
            for (int i = 0; i < n; i++) {
                ans += score[i] * (i + 1);
            }
            return ans;
        }

        // 记录每个节点的先修课（直接前驱）
        int[] pre = new int[n];
        for (int[] e : edges) {
            pre[e[1]] |= 1 << e[0];
        }

        int[] memo = new int[1 << n];
        return dfs(0, pre, score, memo);
    }

    private int dfs(int s, int[] pre, int[] score, int[] memo) {
        if (memo[s] > 0) { // 之前计算过
            return memo[s];
        }
        int res = 0;
        int i = Integer.bitCount(s); // 已学课程数
        // 枚举还没学过的课程 j，且 j 的所有先修课都学完了
        for (int j = 0; j < pre.length; j++) {
            if ((s >> j & 1) == 0 && (s | pre[j]) == s) {
                // (s >> j & 1) == 0 判断 j 是否未学习(1已学习,0未学习)
                // (s | pre[j]) == s 检测 j 的所有先修课是否已学完
                res = Math.max(res, dfs(s | 1 << j, pre, score, memo) + score[j] * (i + 1));
            }
        }
        return memo[s] = res; // 记忆化
    }

}
