package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3568 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/5 14:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3568 {

    /**
     * @Description:
     * 给你一个 m x n 的网格图 classroom，其中一个学生志愿者负责清理散布在教室里的垃圾。网格图中的每个单元格是以下字符之一：
     * 'S' ：学生的起始位置
     * 'L' ：必须收集的垃圾（收集后，该单元格变为空白）
     * 'R' ：重置区域，可以将学生的能量恢复到最大值，无论学生当前的能量是多少（可以多次使用）
     * 'X' ：学生无法通过的障碍物
     * '.' ：空白空间
     * 同时给你一个整数 energy，表示学生的最大能量容量。学生从起始位置 'S' 开始，带着 energy 的能量出发。
     * 每次移动到相邻的单元格（上、下、左或右）会消耗 1 单位能量。如果能量为 0，学生此时只有处在 'R' 格子时可以继续移动，此区域会将能量恢复到 最大 能量值 energy。
     * 返回收集所有垃圾所需的 最少 移动次数，如果无法完成，返回 -1。
     *
     * tips:
     * 1 <= m == classroom.length <= 20
     * 1 <= n == classroom[i].length <= 20
     * classroom[i][j] 是 'S'、'L'、'R'、'X' 或 '.' 之一
     * 1 <= energy <= 50
     * 网格图中恰好有 一个 'S'。
     * 网格图中 最多 有 10 个 'L' 单元格。
     * @param: classroom
     * @param: energy
     * @return int
     * @author marks
     * @CreateDate: 2026/06/05 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minMoves(String[] classroom, int energy) {
        int result;
        result = method_01(classroom, energy);
        return result;
    }

    /**
     * @Description:
     * 1. 使用map 存储所有的 L 的坐标, 并且将二维坐标转换为一维坐标, 使用 index 标记, 例如 第 0 堆垃圾坐标是 (i, j), map.put(i * m + j, 0),
     * 这样可以方便判断是否收集所有的垃圾 int n = map.size(); mask = [0 ~ 1 << n - 1];
     * 2. 需要多少参数: 坐标 x, y, mask, remainEnergy, step(移动的步数), 使用 4 维数组 dp[x][y][mask][remainEnergy] = step;
     * 3. 使用 Dijkstra 算法 + 优先队列的方式处理
     * 超时, 这就没办法了, 通过的测试用例: 797/799
     * 4. 找到问题所在, 由于重复的能量减少, 导致不断在不同格子之间来回移动, 导致最终超时.
     * 5. 不需要使用优先队列, 使用普通队列即可, 并且减少能量维度, 将其转换为值, int[][][] dist = new int[m][n][1 << n], dist[x][y][mask] = energy;
     * 剩余的能量, 每次找剩余能量最大的, 至于步数, int size = queue.size(); 处理, 处理完成后 step++ 即可.
     * AC: 152ms/55.36MB
     * @param: classroom
     * @param: energy
     * @return int
     * @author marks
     * @CreateDate: 2026/06/05 14:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Map<Integer, Integer> map;
    private int[][][] dist;
    private int m;
    private int n;
    private int maxEnergy;
    private int method_01(String[] classroom, int energy) {
        m = classroom.length;
        n = classroom[0].length();
        maxEnergy = energy;
        int index = 0;
        map = new HashMap<>();
        int sX = 0, sY = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (classroom[i].charAt(j) == 'L') {
                    map.put(i * n + j, index);
                    index++;
                } else if (classroom[i].charAt(j) == 'S') {
                    sX = i;
                    sY = j;
                }
            }
        }
        int sum = map.size();
        if (sum == 0) {
            return 0;
        }

        dist = new int[m][n][1 << sum];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dist[i][j], -1);
            }
        }

        dist[sX][sY][0] = energy;
        // 创建队列
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[] {sX, sY, 0, energy});

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] poll = queue.poll();
                int curr_i = poll[0], curr_j = poll[1], curr_mask = poll[2], remainEnergy = poll[3];
                if (curr_mask == (1 << sum) - 1) { // 遍历完所有垃圾
                    return step;
                }
                // remainEnergy > 0
                if (remainEnergy == 0) {
                    continue;
                }
                for (int[] dir : dirs) {
                    int next_i = curr_i + dir[0];
                    int next_j = curr_j + dir[1];
                    if (next_i < 0 || next_i >= m || next_j < 0 || next_j >= n || classroom[next_i].charAt(next_j) == 'X') {
                        // 跳过障碍物 和 越界情况
                        continue;
                    }

                    dealWithNextPoint(next_i, next_j, poll, queue, classroom);
                }
            }
            step++;
        }

        return -1;
    }

    private void dealWithNextPoint(int nextI, int nextJ, int[] poll, Queue<int[]> queue, String[] classroom) {
        int curr_mask = poll[2], remainEnergy = poll[3];
        remainEnergy--;
        if (classroom[nextI].charAt(nextJ) == 'R') {
            // 重置区域
            if (dist[nextI][nextJ][curr_mask] < maxEnergy) {
                dist[nextI][nextJ][curr_mask] = maxEnergy;
                queue.offer(new int[] {nextI, nextJ, curr_mask, maxEnergy});
            }
        } else if (classroom[nextI].charAt(nextJ) == 'L') {
            // 垃圾
            int index = map.get(nextI * n + nextJ);
            // 判断 mask 是否已经收集过
            if ((curr_mask & (1 << index)) == 0) {
                // 执行收集操作
                int next_mask = curr_mask | (1 << index);
                if (dist[nextI][nextJ][next_mask] < remainEnergy) {
                    dist[nextI][nextJ][next_mask] = remainEnergy;
                    queue.offer(new int[] {nextI, nextJ, next_mask, remainEnergy});
                }
            } else {
                // 已经收集过了, 但是这个路径还是可以同行的
                if (dist[nextI][nextJ][curr_mask] < remainEnergy) {
                    dist[nextI][nextJ][curr_mask] = remainEnergy;
                    queue.offer(new int[] {nextI, nextJ, curr_mask, remainEnergy});
                }
            }
        } else {
            // 空闲区域
            if (dist[nextI][nextJ][curr_mask] < remainEnergy) {
                dist[nextI][nextJ][curr_mask] = remainEnergy;
                queue.offer(new int[] {nextI, nextJ, curr_mask, remainEnergy});
            }
        }
    }

}
