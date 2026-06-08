package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3552 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 17:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3552 {

    /**
     * @Description:
     * 给你一个大小为 m x n 的二维字符网格 matrix，用字符串数组表示，其中 matrix[i][j] 表示第 i 行和第 j 列处的单元格。每个单元格可以是以下几种字符之一：
     * '.' 表示一个空单元格。
     * '#' 表示一个障碍物。
     * 一个大写字母（'A' 到 'Z'）表示一个传送门。
     * 你从左上角单元格 (0, 0) 出发，目标是到达右下角单元格 (m - 1, n - 1)。
     * 你可以从当前位置移动到相邻的单元格（上、下、左、右），移动后的单元格必须在网格边界内且不是障碍物。
     * 如果你踏入一个包含传送门字母的单元格，并且你之前没有使用过该传送门字母，你可以立即传送到网格中另一个具有相同字母的单元格。
     * 这次传送不计入移动次数，但每个字母对应的传送门在旅程中 最多 只能使用一次。
     * 返回到达右下角单元格所需的 最少 移动次数。如果无法到达目的地，则返回 -1。
     *
     * tips:
     * 1 <= m == matrix.length <= 10^3
     * 1 <= n == matrix[i].length <= 10^3
     * matrix[i][j] 是 '#'、'.' 或一个大写英文字母。
     * matrix[0][0] 不是障碍物。
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/06/04 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMoves(String[] matrix) {
        int result;
        result = method_01(matrix);
        return result;
    }

    /**
     * @Description:
     * 1. 需要找到原点(0, 0) 到达 右下角(m - 1, n - 1) 的最少移动次数, 找最小值使用 Dijkstra 算法
     * 2. 由于每个大小字母的传送门最多使用一次, 那么是不是遇到之后就直接使用? 这样才能最优?
     * 3. 是否可以使用 map 记录到达每个字母的最短移动次数? Map<Character, Integer> 存储到达字母的最短移动次数
     * 4. 当我们遇到一个字母时, int curr 为当前移动次数 于 map.get(currChar) 进行对比, 如果map 中不存在或者当前移动次数小于map,
     * 则 put 到 map 中, 否则更新当前移动次数 curr = map.get(currChar); 然后继续移动.
     * 5. mask 使用错误, 即当前使用了 第 i 个大写字母, 但是 map 中存入的是第一个大写字母, 这两者没有关联
     * 6. 需要处理类似于["HH"] 的情况, 由于到达任意 H 的移动次数为0, 导致在优先队列执行过程中陷入死循环
     * 7. "A..#A..##.","..#..C.C.B","A.#..#BC.." 由于存在封锁情况, 导致必须先执行传送操作
     * AC: 789ms/150.17MB
     * @param: matrix
     * @return int
     * @author marks
     * @CreateDate: 2026/06/04 17:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] matrix) {
        int m = matrix.length;
        int n = matrix[0].length();
        if (m == 1 && n == 1) {
            return 0;
        }
        if (matrix[0].charAt(0) == '#') {
            return -1;
        }
        List<int[]>[] allChars = new List[26];
        for (int i = 0; i < 26; i++) {
            allChars[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char currChar = matrix[i].charAt(j);
                if (Character.isUpperCase(currChar)) {
                    allChars[currChar - 'A'].add(new int[]{i, j});
                }
            }
        }


        int INF = Integer.MAX_VALUE / 2;
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[0][0] = 0;
        Map<Character, Integer> map = new HashMap<>();
        // 创建优先队列
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {dist, x, y} 移动次数, x, y 坐标
        pq.offer(new int[]{0, 0, 0});
        // 判断 0,0 是否是大小字母
        if (Character.isUpperCase(matrix[0].charAt(0))) {
            map.put(matrix[0].charAt(0), 0);
            for (int[] point : allChars[matrix[0].charAt(0) - 'A']) {
                int x = point[0], y = point[1];
                if (0 < dist[x][y]) {
                    dist[x][y] = 0;
                    pq.offer(new int[]{0, x, y});
                }
            }
        }
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 上下左右
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currDist = curr[0], currX = curr[1], currY = curr[2];
            if (currX == m - 1 && currY == n - 1) {
                return currDist;
            }
            for (int[] dir : dirs) {
                int nextX = currX + dir[0];
                int nextY = currY + dir[1];
                if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n || matrix[nextX].charAt(nextY) == '#') {
                    // 越界 或者 是障碍物, 则跳过
                    continue;
                }
                if (matrix[nextX].charAt(nextY) == '.') {
                    if (currDist + 1 < dist[nextX][nextY]) {
                        dist[nextX][nextY] = currDist + 1;
                        pq.offer(new int[]{currDist + 1, nextX, nextY});
                    }
                } else {
                    // 遇到大写字母, 先判断 map 中是否存在该字母
                    List<int[]> list = allChars[matrix[nextX].charAt(nextY) - 'A'];
                    if (!map.containsKey(matrix[nextX].charAt(nextY))) {
                        // 更新 map 中该字母对应的移动次数
                        map.put(matrix[nextX].charAt(nextY), currDist + 1);
                        for (int[] point : list) {
                            int x = point[0], y = point[1];
                            if (currDist + 1 < dist[x][y]) {
                                dist[x][y] = currDist + 1;
                                pq.offer(new int[]{currDist + 1, x, y});
                            }
                        }
                    } else {
                        if (currDist + 1 < dist[nextX][nextY] || map.get(matrix[nextX].charAt(nextY)) < dist[nextX][nextY]) {
                            // 可以执行传送操作, 更新 currDist
                            if (map.get(matrix[nextX].charAt(nextY)) > currDist + 1) {
                                map.put(matrix[nextX].charAt(nextY), currDist + 1);
                                for (int[] point : list) {
                                    int x = point[0], y = point[1];
                                    if (currDist + 1 < dist[x][y]) {
                                        dist[x][y] = currDist + 1;
                                        pq.offer(new int[]{currDist + 1, x, y});
                                    }
                                }
                            } else {
                                dist[nextX][nextY] = map.get(matrix[nextX].charAt(nextY));
                                map.put(matrix[nextX].charAt(nextY), dist[nextX][nextY]);
                                pq.offer(new int[]{dist[nextX][nextY], nextX, nextY});
                            }
                        }
                    }
                }
            }
        }

        return -1;
    }

}
