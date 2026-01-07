package com.marks.leetcode.graph_theory;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1042 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/7 17:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1042 {

    /**
     * @Description:
     * 有 n 个花园，按从 1 到 n 标记。另有数组 paths ，其中 paths[i] = [xi, yi]
     * 描述了花园 xi 到花园 yi 的双向路径。在每个花园中，你打算种下四种花之一。
     * 另外，所有花园 最多 有 3 条路径可以进入或离开.
     * 你需要为每个花园选择一种花，使得通过路径相连的任何两个花园中的花的种类互不相同。
     * 以数组形式返回 任一 可行的方案作为答案 answer，其中 answer[i] 为在第 (i+1) 个花园中种植的花的种类。
     * 花的种类用  1、2、3、4 表示。保证存在答案。
     * tips:
     * 1 <= n <= 10^4
     * 0 <= paths.length <= 2 * 10^4
     * paths[i].length == 2
     * 1 <= xi, yi <= n
     * xi != yi
     * 每个花园 最多 有 3 条路径可以进入或离开
     * @param: n
     * @param: paths
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/07 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] gardenNoAdj(int n, int[][] paths) {
        int[] result;
        result = method_01(n, paths);
        return result;
    }

    /**
     * @Description:
     * 1. int[] ans = new int[n]; boolean[] visited = new boolean[n];
     * 2. 由于每个花园最多存在3条路径, 所以Set 集合存储相邻花园种种花的情况, 然后从{1~4} 中选择一个不在Set 中的数 x, ans[i] = x, visited[x] = true;
     * 3. 遍历visited, 如果visited[i] == false, 则执行广度优先搜索, 直到遍历所有花园
     * AC: 16ms/56.56MB
     * @param: n
     * @param: paths
     * @return int[]
     * @author marks
     * @CreateDate: 2026/01/07 17:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] paths) {
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] path : paths) {
            int u = path[0] - 1;
            int v = path[1] - 1;
            graph[u].add(v);
            graph[v].add(u);
        }
        int[] ans = new int[n];
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            // 对 i 进行广度优先搜索
            if (!visited[i]) {
                BFSGarden(i, graph, ans, visited);
            }
        }
        return ans;
    }

    private void BFSGarden(int u, List<Integer>[] graph, int[] ans, boolean[] visited) {
        // 队列
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(u);
        visited[u] = true;
        while (!queue.isEmpty()) {
            int i = queue.poll();
            Set<Integer> set = new HashSet<>(); // 存储相邻花园种种花
            for (int v : graph[i]) {
                if (!visited[v]) {
                    queue.offer(v);
                    visited[v] = true;
                } else {
                    set.add(ans[v]);
                }
            }
            // 给当前 ans[i] 赋值
            for (int x = 1; x <= 4; x++) {
                if (!set.contains(x)) {
                    ans[i] = x;
                    break;
                }
            }
        }
    }

}
