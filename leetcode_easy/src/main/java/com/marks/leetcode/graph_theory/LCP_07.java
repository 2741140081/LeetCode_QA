package com.marks.leetcode.graph_theory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_07 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/31 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_07 {

    /**
     * @Description:
     * 小朋友 A 在和 ta 的小伙伴们玩传信息游戏，游戏规则如下：
     * 有 n 名玩家，所有玩家编号分别为 0 ～ n-1，其中小朋友 A 的编号为 0
     * 每个玩家都有固定的若干个可传信息的其他玩家（也可能没有）。传信息的关系是单向的（比如 A 可以向 B 传信息，但 B 不能向 A 传信息）。
     * 每轮信息必须需要传递给另一个人，且信息可重复经过同一个人
     * 给定总玩家数 n，以及按 [玩家编号,对应可传递玩家编号] 关系组成的二维数组 relation。
     * 返回信息从小 A (编号 0 ) 经过 k 轮传递到编号为 n-1 的小伙伴处的方案数；若不能到达，返回 0。
     * @param: n
     * @param: relation
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numWays(int n, int[][] relation, int k) {
        int result;
        result = method_01(n, relation, k);
        result = method_02(n, relation, k);
        return result;
    }

    /**
     * @Description:
     * 动态规划
     * AC: 0ms/42.32MB
     * @param: n
     * @param: relation
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 10:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[][] relation, int k) {
        int[][] dp = new int[k + 1][n];
        dp[0][0] = 1;
        for (int i = 0; i < k; i++) {
            for (int[] edge : relation) {
                int src = edge[0];
                int dst = edge[1];
                dp[i + 1][dst] += dp[i][src];
            }
        }
        return dp[k][n - 1];
    }

    /**
     * @Description:
     * 广度优先搜索
     * AC: 14ms/46.86MB
     * @param: n
     * @param: relation
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2025/12/31 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] relation, int k) {
        // 构建邻接矩阵
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] rel : relation) {
            graph[rel[0]].add(rel[1]); // 有向图
        }
        int ans = 0;
        Queue<int[]> queue = new ArrayDeque<>(); // int[] = {当前节点编号, 当前轮数}
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curNode = cur[0];
            int curK = cur[1];
            if (curK == k) {
                if (curNode == n - 1) {
                    ans++;
                }
            }
            for (int nextNode : graph[curNode]) {
                if (curK + 1 > k) {
                    break;
                }
                queue.offer(new int[]{nextNode, curK + 1});
            }
        }
        return ans;
    }

}
