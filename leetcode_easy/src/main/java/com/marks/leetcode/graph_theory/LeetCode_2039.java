package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2039 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2039 {

    /**
     * @Description:
     * 给你一个有 n 个服务器的计算机网络，服务器编号为 0 到 n - 1 。同时给你一个二维整数数组 edges ，
     * 其中 edges[i] = [ui, vi] 表示服务器 ui 和 vi 之间有一条信息线路，在 一秒 内它们之间可以传输 任意 数目的信息。
     * 再给你一个长度为 n 且下标从 0 开始的整数数组 patience 。
     *
     * 题目保证所有服务器都是 相通 的，也就是说一个信息从任意服务器出发，都可以通过这些信息线路直接或间接地到达任何其他服务器。
     * 编号为 0 的服务器是 主 服务器，其他服务器为 数据 服务器。每个数据服务器都要向主服务器发送信息，并等待回复。
     * 信息在服务器之间按 最优 线路传输，也就是说每个信息都会以 最少时间 到达主服务器。
     * 主服务器会处理 所有 新到达的信息并 立即 按照每条信息来时的路线 反方向 发送回复信息。
     *
     * 在 0 秒的开始，所有数据服务器都会发送各自需要处理的信息。从第 1 秒开始，每 一秒最 开始 时，每个数据服务器都会检查它是否收到了主服务器的回复信息（包括新发出信息的回复信息）：
     * 如果还没收到任何回复信息，那么该服务器会周期性 重发 信息。
     * 数据服务器 i 每 patience[i] 秒都会重发一条信息，也就是说，数据服务器 i 在上一次发送信息给主服务器后的 patience[i] 秒 后 会重发一条信息给主服务器。
     * 否则，该数据服务器 不会重发 信息。
     * 当没有任何信息在线路上传输或者到达某服务器时，该计算机网络变为 空闲 状态。
     * 请返回计算机网络变为 空闲 状态的 最早秒数 。
     * @param: edges
     * @param: patience
     * @return int
     * @author marks
     * @CreateDate: 2026/01/07 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int networkBecomesIdle(int[][] edges, int[] patience) {
        int result;
        result = method_01(edges, patience);
        return result;
    }

    /**
     * @Description:
     * 1. 相邻服务器之间的距离是1s, 并且可以发送任意条数据
     * 2. 从0号主服务器发送数据, 到达其他服务器的时间, 可以用一个数组来进行存储int[] times 存储服务器 0 到各个服务器的最短时间
     * 可以用广度优先搜索算法进行求解 times[]
     * 3. 由于数据服务器会周期的发送数据, 所以需要判断, 假设服务器 i 第 x 次发送数据, 此时的时间是 t = x - 1 * patience[i],
     * 判断 times[i] * 2 与 t 的关系, 分情况讨论
     * 4.1 times[i] * 2 > t && times[i] * 2 <= t + 1(下一次发送, x + 1 次发送), 总时间为 sumT = t + times[i] * 2
     * 4.2 times[i] * 2 = t, sumT = t - 1 + times[i] * 2
     * 5. 最终空闲状态即为 Math.max(sumT(i)); 的最大值.
     * AC: 85ms/205.14MB
     * @param: edges
     * @param: patience
     * @return int
     * @author marks
     * @CreateDate: 2026/01/07 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[][] edges, int[] patience) {
        // 构建邻接表
        int n = patience.length;
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(new int[]{v, 1});
            graph[v].add(new int[]{u, 1});
        }
        boolean[] visited = new boolean[n]; // 节点值是否访问过
        visited[0] = true;
        int[] times = new int[n]; // 服务器 0 到各个服务器的最短时间
        // 广度优先搜索, 队列实现
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int u = curr[0];
            int time = curr[1];
            for (int[] edge : graph[u]) {
                int v = edge[0];
                if (!visited[v]) {
                    visited[v] = true;
                    times[v] = time + edge[1];
                    queue.offer(new int[]{v, time + edge[1]});
                }
            }
        }

        int ans = 0; // 最大空闲时间
        for (int i = 1; i < n; i++) {
            // 乘以2
            times[i] *= 2;
            // 计算需要除数
            int x = (times[i] - 1) / patience[i];
            // 最后一次发送的时间
            int t = x * patience[i];
            int sumT = t + times[i];
            ans = Math.max(ans, sumT);
        }
        return ans + 1; // 最大空闲时间, 即数据到达后的1s, 需要加1s
    }

}
