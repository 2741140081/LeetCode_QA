package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3547 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3547 {

    /**
     * @Description:
     * 给你一个包含 n 个节点的 无向连通图，节点按从 0 到 n - 1 编号。每个节点 最多 与其他两个节点相连。
     * 图中包含 m 条边，使用一个二维数组 edges 表示，其中 edges[i] = [ai, bi] 表示节点 ai 和节点 bi 之间有一条边。
     * 你需要为每个节点分配一个从 1 到 n 的 唯一 值。边的值定义为其两端节点值的 乘积 。
     * 你的得分是图中所有边值的总和。
     * 返回你可以获得的 最大 得分
     * tips:
     * 1 <= n <= 5 * 10^4
     * m == edges.length
     * 1 <= m <= n
     * edges[i].length == 2
     * 0 <= ai, bi < n
     * ai != bi
     * 图中不存在重复边。
     * 图是连通的。
     * 每个节点最多与其他两个节点相连。
     * @param: n
     * @param: edges
     * @return long
     * @author marks
     * @CreateDate: 2026/01/15 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxScore(int n, int[][] edges) {
        long result;
        result = method_01(n, edges);
        return result;
    }

    /**
     * @Description:
     * 1. 感觉很简单, 记录图中节点的入度, 然后分配唯一值, 然后计算边值
     * 2. 不太对, 当两者入度相同情况下, 节点[i] 更靠近一个大值的节点, 这就会导致结果不正确
     * 3. 唯一能确定的是图是连通的, 并且每个节点的入度值最大为2,
     * 4. 就可以判断图是环还是链表, 如果图是环 n == edges.length. 如果图是链表 n == edges.length - 1
     * AC: 93ms/198.39MB
     * @param: n
     * @param: edges
     * @return long
     * @author marks
     * @CreateDate: 2026/01/15 17:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int n, int[][] edges) {
        long[] values = new long[n];
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        int[] inDegree = new int[n]; // 入度
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
            inDegree[u]++;
            inDegree[v]++;
        }
        if (n == edges.length) {
            // 连通图是一个环路, 所有节点入度相同, 然后通过基于广度搜索的优先队列进行节点分配唯一值
            values[0] = n;
            // 优先队列
            PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o2[1] - o1[1]); // index, value
            queue.offer(new int[]{0, n});
            int next = n - 1;
            while (!queue.isEmpty()) {
                int[] curr = queue.poll();
                int i = curr[0];
                for (int j : graph[i]) {
                    if (values[j] == 0) {
                        values[j] = next--;
                        queue.offer(new int[]{j, (int) values[j]});
                    }
                }
            }

        } else {
            // 连通图是一条直线, 从外往里处理, 先给两侧节点赋值为0,1,2,3..., 然后通过基于广度搜索的优先队列进行节点分配唯一值
            // 需要找到入度为1的两个点
            int[] oneInDegree = new int[2];
            int index = 0;
            for (int i = 0; i < n; i++) {
                if (inDegree[i] == 1) {
                    oneInDegree[index++] = i;
                }
            }
            values[oneInDegree[0]] = 1;
            values[oneInDegree[1]] = 2;
            // 可以用队列优化
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(oneInDegree[0]);
            queue.offer(oneInDegree[1]);
            int next = 3;
            while (!queue.isEmpty()) {
                int curr = queue.poll();
                for (int j : graph[curr]) {
                    if (values[j] == 0) {
                        values[j] = next++;
                        queue.offer(j);
                    }
                }
            }
        }

        long ans = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            ans += values[u] * values[v];
        }

        return ans;
    }

}
