package com.marks.leetcode.bfs_algorithm;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_749 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/23 14:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_749 {

    /**
     * @Description:
     * 病毒扩散得很快，现在你的任务是尽可能地通过安装防火墙来隔离病毒。
     *
     * 假设世界由 m x n 的二维矩阵 isInfected 组成， isInfected[i][j] == 0 表示该区域未感染病毒，而  isInfected[i][j] == 1 表示该区域已感染病毒。
     * 可以在任意 2 个相邻单元之间的共享边界上安装一个防火墙（并且只有一个防火墙）。
     *
     * 每天晚上，病毒会从被感染区域向相邻未感染区域扩散，除非被防火墙隔离。
     * 现由于资源有限，每天你只能安装一系列防火墙来隔离其中一个被病毒感染的区域（一个区域或连续的一片区域），且该感染区域对未感染区域的威胁最大且 保证唯一 。
     *
     * 你需要努力使得最后有部分区域不被病毒感染，如果可以成功，那么返回需要使用的防火墙个数;
     * 如果无法实现，则返回在世界被病毒全部感染时已安装的防火墙个数。
     * tips:
     * m == isInfected.length
     * n == isInfected[i].length
     * 1 <= m, n <= 50
     * isInfected[i][j] is either 0 or 1
     * 在整个描述的过程中，总有一个相邻的病毒区域，它将在下一轮 严格地感染更多未受污染的方块
     * @param: isInfected
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 14:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int containVirus(int[][] isInfected) {
        int result;
        result = method_01(isInfected);
        return result;
    }

    /**
     * @Description:
     * 1. 0 表示未感染, 1 表示已感染, 2 表示已感染且被防火墙隔离.
     * 2. 构建一个visited数组, 用来记录是否被访问过, false 表示未访问, true 表示已访问.
     * 找到一个被感染区域, isInfected[i][j] == 1 && !visited[i][j], 通过bfs算法计算待感染区域的数量 tempCount. int maxCount = 0;
     * if (tempCount > maxCount) { 更新坐标信息 x = i, y = j } => x,y 即为待封锁的感染区域. x, y 初始值为 -1, -1
     * 3. 如果maxCount > 0, 说明待被感染的区域, 需要进行封锁操作. 如果maxCount == 0, 说明没有待被感染的区域
     * 4. 需要在bfs过程中记录防火墙的个数。这个数量只需要计算与 值为当前单元格相邻的单元格中值为0的数量。
     * AC: 16ms/45.65MB
     * @param: isInfected
     * @return int
     * @author marks
     * @CreateDate: 2025/12/23 14:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int m;
    private int n;
    private int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    private int method_01(int[][] isInfected) {
        this.m = isInfected.length;
        this.n = isInfected[0].length;
        int ans = 0; // 防火墙个数
        boolean flag = true;
        while (flag) {
            boolean[][] visited = new boolean[m][n];
            int x = -1, y = -1;
            int maxCount = 0;
            int sum = 0; // 防火墙个数
            List<int[]> roots = new ArrayList<>(); // 感染区域的起始点
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (isInfected[i][j] == 1 && !visited[i][j]) {
                        int[] count = bfsGetCount(i, j, visited, isInfected); // c[0]:防火墙个数, c[1]:待感染区域数量
                        if (count[1] >= maxCount) {
                            maxCount = count[1];
                            x = i;
                            y = j;
                            sum = count[0];
                        }
                        roots.add(new int[]{i, j});
                    }
                }
            }
            if (maxCount > 0) {
                // 封锁操作
                bfsChange(x, y, isInfected);
                ans += sum;
                // 病毒扩散, 只能扩散一次, 但是由于上一个扩散区域与待扩散的区域进行了重叠, 导致扩散了2次
                // 需要将visited数组设置为当前扩散的全局状态, 避免重复扩散
                visited = new boolean[m][n];
                for (int[] root : roots) {
                    if (root[0] != x || root[1] != y) {
                        bfsSpread(root[0], root[1], isInfected, visited);
                    }
                }
            } else {
                ans += sum;
                flag = false;
            }
        }
        return ans;
    }

    private void bfsSpread(int i, int j, int[][] isInfected, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        visited[i][j] = true;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int newX = curr[0] + dir[0];
                int newY = curr[1] + dir[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n && !visited[newX][newY]) {
                    if (isInfected[newX][newY] == 1) {
                        queue.offer(new int[]{newX, newY});
                        visited[newX][newY] = true;
                    } else if (isInfected[newX][newY] == 0) {
                        // 扩散
                        isInfected[newX][newY] = 1;
                        visited[newX][newY] = true;
                    }
                }
            }
        }
    }

    private void bfsChange(int x, int y, int[][] isInfected) {
        int value = 2;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        isInfected[x][y] = value;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int newX = curr[0] + dir[0];
                int newY = curr[1] + dir[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    if (isInfected[newX][newY] == 1) {
                        isInfected[newX][newY] = value;
                        queue.offer(new int[]{newX, newY});
                    }
                }
            }
        }
    }

    private int[] bfsGetCount(int i, int j, boolean[][] visited, int[][] isInfected) {
        int[] ans = new int[2]; //  防火墙个数, 待感染区域数量
        Set<Integer> count = new HashSet<>(); // 待感染区域数量, 将二维 i, j 转换成一维 i * n + j

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{i, j});
        visited[i][j] = true;
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            for (int[] dir : dirs) {
                int newX = curr[0] + dir[0];
                int newY = curr[1] + dir[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n && !visited[newX][newY]) {
                    if (isInfected[newX][newY] == 0) {
                        count.add(newX * n + newY);
                        ans[0]++;
                    } else if (isInfected[newX][newY] == 1) {
                        queue.offer(new int[]{newX, newY});
                        visited[newX][newY] = true;
                    }
                }
            }
        }
        ans[1] = count.size();
        return ans;
    }

}
