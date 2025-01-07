package com.marks.leetcode.graph_theory_algorithm.Floyd_algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/7 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2959 {
    
    /**
     * @Description: [
     * 一个公司在全国有 n 个分部，它们之间有的有道路连接。一开始，所有分部通过这些道路两两之间互相可以到达。
     *
     * 公司意识到在分部之间旅行花费了太多时间，所以它们决定关闭一些分部（也可能不关闭任何分部），同时保证剩下的分部之间两两互相可以到达且最远距离不超过 maxDistance 。
     *
     * 两个分部之间的 距离 是通过道路长度之和的 最小值 。
     *
     * 给你整数 n ，maxDistance 和下标从 0 开始的二维整数数组 roads ，其中 roads[i] = [ui, vi, wi] 表示一条从 ui 到 vi 长度为 wi的 无向 道路。
     *
     * 请你返回关闭分部的可行方案数目，满足每个方案里剩余分部之间的最远距离不超过 maxDistance。
     *
     * 注意，关闭一个分部后，与之相连的所有道路不可通行。
     *
     * 注意，两个分部之间可能会有多条道路。
     * ]
     * @param n 
     * @param maxDistance 
     * @param roads 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSets(int n, int maxDistance, int[][] roads) {
        int result;
        result = method_01(n, maxDistance, roads);
        return result;
    }
    
    /**
     * @Description: [
     * 二进制枚举所有的可能性, 因为n最大值为10, 所以所有的可能性为2^n, 即最大2^10 = 1024
     * AC:24ms/41.70MB
     * ]
     * @param n 
     * @param maxDistance 
     * @param roads 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/7 10:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int maxDistance, int[][] roads) {
        int totalCombinations = 1 << n;
        int[] open = new int[n];
        int[][] dp = new int[n][n];
        int ans = 0;
        for (int mask = 0; mask < totalCombinations; mask++) {
            for (int i = 0; i < n; i++) {
                open[i] = mask & (1 << i);
            }

            // 初始化任意两点之间距离最大
            for (int i = 0; i < n; i++) {
                Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
            }

            for (int[] road : roads) {
                int x = road[0], y = road[1], value = road[2];
                if (open[x] > 0 && open[y] > 0) {
                    dp[x][y] = Math.min(dp[x][y], value);
                    dp[y][x] = Math.min(dp[y][x], value);
                }
            }

            for (int k = 0; k < n; k++) {
                if (open[k] > 0) {
                    for (int i = 0; i < n; i++) {
                        if (open[i] > 0) {
                            for (int j = i + 1; j < n; j++) {
                                if (open[j] > 0) {
                                    dp[i][j] = dp[j][i] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                                }
                            }
                        }
                    }
                }
            }
            boolean flag = true;
            for (int i = 0; i < n; i++) {
                if (open[i] > 0) {
                    for (int j = i + 1; j < n; j++) {
                        if (open[j] > 0) {
                            if (dp[i][j] > maxDistance) {
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (!flag) {
                        break;
                    }
                }
            }
            if (flag) {
                ans++;
            }
        }
        return ans;
    }
}
