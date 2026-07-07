package com.marks.leetcode.array;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3680 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/6 14:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3680 {

    /**
     * @Description:
     * 给你一个整数 n，表示 n 支队伍。你需要生成一个赛程，使得：
     * 每支队伍与其他队伍 正好比赛两次：一次在主场，一次在客场。
     * 每天 只有一场 比赛；赛程是一个 连续的 天数列表，schedule[i] 表示第 i 天的比赛。
     * 没有队伍在 连续 两天内进行比赛。
     * 返回一个 2D 整数数组 schedule，其中 schedule[i][0] 表示主队，schedule[i][1] 表示客队。如果有多个满足条件的赛程，返回 其中任意一个 。
     * 如果没有满足条件的赛程，返回空数组。
     * tips:
     * 2 <= n <= 50
     * @param: n
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/07/06 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] generateSchedule(int n) {
        int[][] result;
        result = method_01(n);
        result = method_02(n);
        return result;
    }

    private int[][] method_02(int n) {
        if (n < 5) {
            return new int[][]{};
        }

        int[][] ans = new int[n * (n - 1)][];
        int idx = 0;

        // 处理 d=2,3,...,n-2
        for (int d = 2; d < n - 1; d++) {
            for (int i = 0; i < n; i++) {
                ans[idx++] = new int[]{i, (i + d) % n};
            }
        }

        // 交错排列 d=1 与 d=n-1（或者说 d=-1）
        for (int i = 0; i < n; i++) {
            ans[idx++] = new int[]{i, (i + 1) % n};
            ans[idx++] = new int[]{(i + n - 1) % n, (i + n - 2) % n};
        }

        return ans;
    }

    /**
     * @Description:
     * 1. 当 n = 4, 先进行模拟, 分别有4支队伍, 编号 0 ~ 3;
     * 2. 0: (0,1), (0,2), (0,3); 1: (1,0), (1,2), (1,3); 2: (2,0), (2,1), (2,3); 3: (3,0), (3,1), (3,2);
     * 3. 假设第1天: (0,1); 第2天: (2,3); 第3天: (1,0); 第4天: (3:2), 无法满足条件, 返回空数组
     * 4. 模拟 n = 5时, 赛程安排: 0: (0,1), (0,2), (0,3), (0,4); 1: (1,0), (1,2), (1,3), (1,4); 2: (2,0), (2,1), (2,3), (2,4);
     * 3: (3,0), (3,1), (3,2), (3,4); 4: (4,0), (4,1), (4,2), (4,3);
     * 5. 第1天: (0,1); 第2天: (2,3); 第3天: (4,0); 第4天: (1,2); 第5天: (3,4); 第6天: (0,2); 第7天: (1,3);....
     * 6. 也就说, 会有 n * n - 1 天的赛程安排, 并且任意两天赛程的队伍互不相同
     * 7. 回溯应该是必定超时, 直接按照模拟来写代码, List<Integer>[] list = new ArrayList[n]; 表示队伍编号
     * 8. 不能直接按照顺序来处理, 应该使用优先队列, 优先安排剩余主场数更多的队伍先进行比赛
     * AC: 47ms/46.15MB
     * @param: n
     * @return int[][]
     * @author marks
     * @CreateDate: 2026/07/06 14:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(int n) {
        if (n <= 4) {
            // 不满足条件, 返回空数组
            return new int[0][];
        }
        List<Integer>[] list = new ArrayList[n];
        boolean[][] visited = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    list[i].add(j);
                } else {
                    visited[i][i] = true;
                }
            }
        }
        int m = n * (n - 1);
        int[][] ans = new int[m][2];
        int[] homeCnt = new int[n];
        Arrays.fill(homeCnt, n - 1);
        int[] awayCnt = new int[n];
        Arrays.fill(awayCnt, n - 1);
        int p1 = -1, p2 = -1; // 记录前一天参赛的队伍编号
        for (int i = 0; i < m; i++) {
            // 直接使用优先队列, 对 a[0] 进行降序排序，优先级队列的元素为 [cnt, i]
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
            for (int j = 0; j < n; j++) {
                if (p1 != j && p2 != j) {
                    pq.offer(new int[] {homeCnt[j], j});
                }
            }
            int away = -1, maxAwayCnt = 0;
            boolean flag = true; // 标记是否找到最多主场并且可以安排客场
            int home = -1;
            while (!pq.isEmpty() && flag) {
                home = pq.poll()[1];
                // 从客场队伍中选择剩余数最多的队列作为客场参赛队伍
                for (int j = 0; j < list[home].size(); j++) {
                    int currAway = list[home].get(j);
                    if (currAway != p1 && currAway != p2 && !visited[home][currAway]) {
                        if (awayCnt[currAway] > maxAwayCnt) {
                            away = currAway;
                            maxAwayCnt = awayCnt[currAway];
                        }
                    }
                }
                if (away != -1) {
                    flag = false;
                } else {
                    // 重置away 和 maxAwayCnt
                    maxAwayCnt = 0;
                }
            }
            if (home != -1 && away != -1) {
                ans[i][0] = home;
                ans[i][1] = away;
                homeCnt[home]--;
                awayCnt[away]--;
                visited[home][away] = true;
                p1 = home;
                p2 = away;
            } else {
                // 返回空数组
                return new int[0][];
            }
        }

        return ans;
    }

}
