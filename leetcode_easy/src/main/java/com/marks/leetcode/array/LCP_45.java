package com.marks.leetcode.array;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_45 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/30 9:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_45 {

    /**
     * @Description:
     * 「力扣挑战赛」中 N*M 大小的自行车炫技赛场的场地由一片连绵起伏的上下坡组成，
     * 场地的高度值记录于二维数组 terrain 中，场地的减速值记录于二维数组 obstacle 中。
     * 若选手骑着自行车从高度为 h1 且减速值为 o1 的位置到高度为 h2 且减速值为 o2 的相邻位置（上下左右四个方向），
     * 速度变化值为 h1-h2-o2（负值减速，正值增速）。
     * 选手初始位于坐标 position 处且初始速度为 1，请问选手可以刚好到其他哪些位置时速度依旧为 1。
     * 请以二维数组形式返回这些位置。若有多个位置则按行坐标升序排列，若有多个位置行坐标相同则按列坐标升序排列。
     * 注意： 骑行过程中速度不能为零或负值
     *
     * tips:
     * n == terrain.length == obstacle.length
     * m == terrain[i].length == obstacle[i].length
     * 1 <= n <= 100
     * 1 <= m <= 100
     * 0 <= terrain[i][j], obstacle[i][j] <= 100
     * position.length == 2
     * 0 <= position[0] < n
     * 0 <= position[1] < m
     * @param: position
     * @param: terrain
     * @param: obstacle
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/06/30 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] bicycleYard(int[] position, int[][] terrain, int[][] obstacle) {
        int[][] result;
        result = method_01(position, terrain, obstacle);
        return result;
    }

    /**
     * @Description:
     * 1. 使用广度优先搜索算法, 起始位置为 position, 队列中需要存入 位置和速度信息
     * 2. 由于过程中需要剔除速度为0或者负数的值.
     * 3. 由于从起始点开始经过不同的路径可以到达同一个点, 但是不同路径到达该点的的速度值是不同的.
     * 4. 从起始点开始, 到达第一个点, 能得到的最大速度是多少? h1 - h2 - o2, 100 - 0 - 0 = 100, 100 + 1 = 101, 所以速度最大值是 101
     * 5. 使用 boolean[][][] visited = new boolean[m][n][102]; 初始值设置为 0, 如果到达某个点的速度是 0 或者负数值, 舍弃
     * 6. 如果到达点 (i, j), 速度值是 v, 首先判断 visited[i][j][v] 是否为 false, 如果为 false, 则将 visited[i][j][v] 设置为 true, 否则舍弃
     * 7. 最后统计 visited[i][j][1] 为 true 的数量, 即为结果,
     * AC: 13ms/47.75MB
     * @param: position
     * @param: terrain
     * @param: obstacle
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/06/30 9:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int[] position, int[][] terrain, int[][] obstacle) {
        int m = terrain.length, n = terrain[0].length;
        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][][] visited = new boolean[m][n][102];
        // 在 Java 中创建队列时，‌优先推荐使用 ArrayDeque
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{position[0], position[1], 1});
        visited[position[0]][position[1]][1] = true;
        List<int[]> ans = new ArrayList<>();
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int currX = cur[0], currY = cur[1];
            int currSpeed = cur[2];
            for (int[] dir : dirs) {
                int nextX = cur[0] + dir[0];
                int nextY = cur[1] + dir[1];
                // 判断是否越界
                if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n) {
                    continue;
                }
                int nextSpeed = currSpeed + (terrain[currX][currY] - terrain[nextX][nextY] - obstacle[nextX][nextY]);
                if (nextSpeed > 0 && !visited[nextX][nextY][nextSpeed]) {
                    visited[nextX][nextY][nextSpeed] = true;
                    queue.offer(new int[]{nextX, nextY, nextSpeed});
                    if (nextSpeed == 1) {
                        ans.add(new int[]{nextX, nextY});
                    }
                }
            }
        }
        // 对 ans List 进行排序, 先按照 [0] 升序排序, 如果[0] 相同, 则按照[1]升序排序
        ans.sort((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });

        return ans.toArray(new int[ans.size()][]);
    }

}
