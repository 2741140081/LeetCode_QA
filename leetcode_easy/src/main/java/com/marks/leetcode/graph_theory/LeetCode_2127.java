package com.marks.leetcode.graph_theory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2127 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/13 11:11
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2127 {

    /**
     * @Description:
     * 一个公司准备组织一场会议，邀请名单上有 n 位员工。公司准备了一张 圆形 的桌子，可以坐下 任意数目 的员工。
     * 员工编号为 0 到 n - 1 。每位员工都有一位 喜欢 的员工，
     * 位员工 当且仅当 他被安排在喜欢员工的旁边，他才会参加会议。每位员工喜欢的员工 不会 是他自己。
     * 给你一个下标从 0 开始的整数数组 favorite ，其中 favorite[i] 表示第 i 位员工喜欢的员工。
     * 请你返回参加会议的 最多员工数目 。
     * tips:
     * n == favorite.length
     * 2 <= n <= 10^5
     * 0 <= favorite[i] <= n - 1
     * favorite[i] != i
     * @param: favorite
     * @return int
     * @author marks
     * @CreateDate: 2026/01/13 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumInvitations(int[] favorite) {
        int result;
        result = method_01(favorite);
        return result;
    }

    /**
     * @Description:
     * 1. 构建邻接表, 假设有员工i, 他被 x 位员工喜欢, 但是只能取最受欢迎的两位坐在他的两侧.
     * 2. 相当于是在图中找到一条最长的路径.
     * 3. 这是一个有向图 a -> b 表示a喜欢b. 另外由于每一个人都会有一个喜欢的人, 所以这些人会构成一个环路.
     * 4. 所以找最长路径即找到最大的环路, 然后取最大值. 怎么找最大环路?
     * 5. 也不一定是环路, 环路不一定最长, a -> b <-> c. 这种最小环需要特殊讨论.
     * 6. 也就是现在有2种情况, 1. 最大环路(节点大于2) 2. 最小环(节点等于2)
     * 查看官解后, 后续需要自行在做一次 need todo
     * AC: 19ms/87.77MB
     * @param: favorite
     * @return int
     * @author marks
     * @CreateDate: 2026/01/13 11:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] favorite) {
        // 构建邻接表
        int n = favorite.length;
        int[] inDegree = new int[n]; // 节点的入度
        for (int j : favorite) {
            inDegree[j]++;
        }
        int[] dp = new int[n]; // dp[i] 表示 i 节点 到最近的环路口的距离. dp[x] = dp[x - 1] + 1;
        boolean[] visited = new boolean[n];
        // 进行拓扑排序, 使用队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            visited[curr] = true;
            int next = favorite[curr];
            inDegree[next]--;
            dp[next] = Math.max(dp[next], dp[curr] + 1);
            if (inDegree[next] == 0) {
                queue.offer(next);
            }

        }
        int ans = 0;
        int sum = 0;
        // 现在需要处理环上面的点
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int next = favorite[i];
                if (favorite[next] == i) {
                    // 长度为2的环(最小环), 这个最小环可以上桌, 因为彼此相连
                    visited[i] = true;
                    visited[next] = true;
                    sum += (dp[i] + dp[next] + 2);
                } else {
                    // 长度大于2的环, 找到环的长度
                    int start = i;
                    int count = 0;
                    do {
                        count++;
                        visited[i] = true;
                        i = favorite[i];
                    } while (i != start);
                    ans = Math.max(ans, count);
                }
            }
        }
        return Math.max(ans, sum); // 但是长度为3及其以上的环不能与最小环坐在一起, 只能取其最大值
    }

}
