package com.marks.leetcode.graph_theory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_56 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/15 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_56 {

    /**
     * @Description:
     * 欢迎各位勇者来到力扣城，本次试炼主题为「信物传送」。
     * 本次试炼场地设有若干传送带，matrix[i][j] 表示第 i 行 j 列的传送带运作方向，"^","v","<",">" 这四种符号分别表示 上、下、左、右 四个方向。
     * 信物会随传送带的方向移动。勇者每一次施法操作，可临时变更一处传送带的方向，在物品经过后传送带恢复原方向。
     * tips:
     * matrix 中仅包含 '^'、'v'、'<'、'>'
     * 0 < matrix.length <= 100
     * 0 < matrix[i].length <= 100
     * 0 <= start[0],end[0] < matrix.length
     * 0 <= start[1],end[1] < matrix[i].length
     * @param: matrix
     * @param: start
     * @param: end
     * @return int
     * @author marks
     * @CreateDate: 2026/01/15 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int conveyorBelt(String[] matrix, int[] start, int[] end) {
        int result;
        result = method_01(matrix, start, end);
        return result;
    }

    /**
     * @Description:
     * 1. 起始点到终点的最短路径, 使用 Dijkstra 算法, 矩阵的话算是一种稀疏图(E = V), 所以使用优先队列来处理时间复杂度更优
     * AC: 13ms/43.88MB
     * @param: matrix
     * @param: start
     * @param: end
     * @return int
     * @author marks
     * @CreateDate: 2026/01/15 16:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] matrix, int[] start, int[] end) {
        final int INF = Integer.MAX_VALUE / 2;
        int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        Map<Character, Integer> map = new HashMap<>(); // 与 dirs[i] 相对应
        map.put('^', 0);
        map.put('v', 1);
        map.put('>', 2);
        map.put('<', 3);
        int m = matrix.length;
        int n = matrix[0].length();
        int[][] dist = new int[m][n]; // dist[i][j] 表示从 start 到 (i, j) 的最短距离
        char[][] graph = new char[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], INF);
            graph[i] = matrix[i].toCharArray();
        }
        dist[start[0]][start[1]] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        queue.offer(new int[]{0, start[0], start[1]});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int t = curr[0];
            int i = curr[1];
            int j = curr[2];
            if (i == end[0] && j == end[1]) {
                return t;
            }
            for (int k = 0; k < 4; k++) {
                int ni = i + dirs[k][0];
                int nj = j + dirs[k][1];
                int cost = map.get(graph[i][j]) == k ? 0 : 1;
                if (ni >= 0 && ni < m && nj >= 0 && nj < n && dist[ni][nj] > t + cost) {
                    dist[ni][nj] = t + cost;
                    queue.offer(new int[]{t + cost, ni, nj});
                }
            }
        }
        return 0;
    }

}
