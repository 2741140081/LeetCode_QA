package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2050 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/5 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2050 {

    /**
     * @Description:
     * 给你一个整数 n ，表示有 n 节课，课程编号从 1 到 n 。
     * 同时给你一个二维整数数组 relations ，其中 relations[j] = [prevCoursej, nextCoursej] ，
     * 表示课程 prevCoursej 必须在课程 nextCoursej 之前 完成（先修课的关系）。
     * 同时给你一个下标从 0 开始的整数数组 time ，其中 time[i] 表示完成第 (i+1) 门课程需要花费的 月份 数。
     *
     * 请你根据以下规则算出完成所有课程所需要的 最少 月份数：
     *
     * 如果一门课的所有先修课都已经完成，你可以在 任意 时间开始这门课程。
     * 你可以 同时 上 任意门课程 。
     * 请你返回完成所有课程所需要的 最少 月份数。
     *
     * 注意：测试数据保证一定可以完成所有课程（也就是先修课的关系构成一个有向无环图）。
     * tips:
     * 1 <= n <= 5 * 10^4
     * 0 <= relations.length <= min(n * (n - 1) / 2, 5 * 10^4)
     * relations[j].length == 2
     * 1 <= prevCoursej, nextCoursej <= n
     * prevCoursej != nextCoursej
     * 所有的先修课程对 [prevCoursej, nextCoursej] 都是 互不相同 的。
     * time.length == n
     * 1 <= time[i] <= 10^4
     * 先修课程图是一个有向无环图。
     * @param: n
     * @param: relations
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumTime(int n, int[][] relations, int[] time) {
        int result;
        result = method_01(n, relations, time);
        return result;
    }

    /**
     * @Description:
     * 1. 还是拓扑排序, 但是需要考虑月份的问题, 也就是时间问题, 可以使用优先队列?
     * 2. 即优先队列中存储, int[] {课程编号, 课程完成时间}
     * AC: 33ms/108.98MB
     * @param: n
     * @param: relations
     * @param: time
     * @return int
     * @author marks
     * @CreateDate: 2026/01/05 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[][] relations, int[] time) {
        int ans = 0; // 当前时间
        // 创建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 构建入度表, 用于拓扑排序
        int[] inDegree = new int[n];
        for (int[] relation : relations) {
            int u = relation[0] - 1;
            int v = relation[1] - 1;
            graph[u].add(v);
            inDegree[v]++;
        }
        // 优先队列
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        // 遍历入度表, 找到入度为0的节点
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(new int[]{i, time[i]});
            }
        }
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            ans = Math.max(ans, cur[1]);
            for (int next : graph[cur[0]]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(new int[]{next, cur[1] + time[next]});
                }
            }
        }
        return ans;
    }

}
