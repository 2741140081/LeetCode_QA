package com.marks.leetcode.array_hard;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_38 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/10 10:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_38 {

    /**
     * @Description:
     * 城堡守卫游戏的胜利条件为使恶魔无法从出生点到达城堡。
     * 游戏地图可视作 2*N 的方格图，记作字符串数组 grid，其中：
     * "." 表示恶魔可随意通行的平地；
     * "#" 表示恶魔不可通过的障碍物，玩家可通过在 平地 上设置障碍物，即将 "." 变为 "#" 以阻挡恶魔前进；
     * "S" 表示恶魔出生点，将有大量的恶魔该点生成，恶魔可向上/向下/向左/向右移动，且无法移动至地图外；
     * "P" 表示瞬移点，移动到 "P" 点的恶魔可被传送至任意一个 "P" 点，也可选择不传送；
     * "C" 表示城堡。
     * 然而在游戏中用于建造障碍物的金钱是有限的，请返回玩家最少需要放置几个障碍物才能获得胜利。
     * 若无论怎样放置障碍物均无法获胜，请返回 -1。
     * 注意：
     * 地图上可能有一个或多个出生点
     * 地图上有且只有一个城堡
     *
     * tips:
     * grid.length == 2
     * 2 <= grid[0].length == grid[1].length <= 10^4
     * grid[i][j] 仅包含字符 "."、"#"、"C"、"P"、"S"
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/09/10 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int guardCastle(String[] grid) {
        int result;
        result = method_01(grid);
        return result;
    }

    /**
     * @Description:
     * 1. 只能在平地 "." 上放置障碍物 "#", 最多的障碍物数量是所有 "." 平地均被放置障碍物
     * 2. 如果都被放置障碍物的情况下, 恶魔仍然可以到达城堡, 则返回 -1。
     * 3. 感觉应该需要用动态规划来处理, todo
     * @param: grid
     * @return int
     * @author marks
     * @CreateDate: 2026/09/10 10:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] grid) {
        int m = grid.length;
        int n = grid[0].length();
        // 先判断从城堡出发, 不经过 "." 和 "#" 即平地全部被放置障碍物的情况下, 恶魔是否仍然可以到达城堡
        int tx = -1, ty = -1;
        List<int[]> pList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = grid[i].charAt(j);
                if (ch == 'C') {
                    tx = i;
                    ty = j;
                } else if (ch == 'S') {
                    pList.add(new int[]{i, j});
                } else if (ch == 'P') {
                    pList.add(new int[]{i, j});
                }
            }
        }
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        // 从城堡出发, 执行 BFS, 判断 -1 的情况
        boolean[][] visited = new boolean[m][n];
        visited[tx][ty] = true;
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{tx, ty});
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];
            if (grid[x].charAt(y) == 'S') {
                return -1;
            }
            // 普通移动
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny] && grid[nx].charAt(ny) != '#' && grid[nx].charAt(ny) != '.') {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
            // 瞬移移动
            if (grid[x].charAt(y) == 'P') {
                for (int[] p : pList) {
                    int px = p[0], py = p[1];
                    if (!visited[px][py]) {
                        visited[px][py] = true;
                        queue.add(new int[]{px, py});
                    }
                }
            }
        }
        // 添加障碍物为0时, 恶魔无法到达城堡
        visited = new boolean[m][n];
        queue.offer(new int[]{tx, ty});
        visited[tx][ty] = true;
        boolean flag = true;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int x = curr[0], y = curr[1];
            if (grid[x].charAt(y) == 'S') {
                flag = false;
                break;
            }
            for (int[] dir : dirs) {
                int nx = x + dir[0], ny = y + dir[1];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny] && grid[nx].charAt(ny) != '#') {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
            if (grid[x].charAt(y) == 'P') {
                for (int[] p : pList) {
                    int px = p[0], py = p[1];
                    if (!visited[px][py]) {
                        visited[px][py] = true;
                        queue.add(new int[]{px, py});
                    }
                }
            }
        }
        if (flag) {
            return 0;
        }


        return 0;
    }

}
