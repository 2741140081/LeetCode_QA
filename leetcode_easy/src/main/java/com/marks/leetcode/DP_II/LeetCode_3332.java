package com.marks.leetcode.DP_II;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3332 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/22 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3332 {

    /**
     * @Description:
     * 给你两个整数 n 和 k ，和两个二维整数数组 stayScore 和 travelScore 。
     * 一位旅客正在一个有 n 座城市的国家旅游，每座城市都 直接 与其他所有城市相连。
     * 这位游客会旅游 恰好 k 天（下标从 0 开始），且旅客可以选择 任意 城市作为起点。
     * 每一天，这位旅客都有两个选择：
     * 留在当前城市：如果旅客在第 i 天停留在前一天所在的城市 curr ，旅客会获得 stayScore[i][curr] 点数。
     * 前往另外一座城市：如果旅客从城市 curr 前往城市 dest ，旅客会获得 travelScore[curr][dest] 点数。
     * 请你返回这位旅客可以获得的 最多 点数。
     *
     * tips:
     * 1 <= n <= 200
     * 1 <= k <= 200
     * n == travelScore.length == travelScore[i].length == stayScore[i].length
     * k == stayScore.length
     * 1 <= stayScore[i][j] <= 100
     * 0 <= travelScore[i][j] <= 100
     * travelScore[i][i] == 0
     * @param: n
     * @param: k
     * @param: stayScore
     * @param: travelScore
     * @return int
     * @author marks
     * @CreateDate: 2026/04/22 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxScore(int n, int k, int[][] stayScore, int[][] travelScore) {
        int result;
        result = method_01(n, k, stayScore, travelScore);
        return result;
    }

    /**
     * @Description:
     * 1. dp: int[k + 1][n] 表示 第 i 天，在 j 城市的最大点数, 初始化时 dp[0][j] = 0, j 为城市编号
     * 2. 状态转移方程: dp[i][j] = Math.max(dp[i - 1][x]) + (x == j ? stayScore[i][j] : travelScore[x][j])
     * AC: 97ms/59.72MB
     * 时间复杂度: O(k * n * n), 空间复杂度: O(k * n)
     * @param: n
     * @param: k
     * @param: stayScore
     * @param: travelScore
     * @return int
     * @author marks
     * @CreateDate: 2026/04/22 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int k, int[][] stayScore, int[][] travelScore) {
        int[][] dp = new int[k + 1][n];
        int ans = 0; // 记录最大点数
        for (int i = 1; i <= k; i++) {
            for (int j = 0; j < n; j++) {
                int maxVal = 0;
                for (int x = 0; x < n; x++) {
                    if (x == j) {
                        maxVal = Math.max(maxVal, dp[i - 1][x] + stayScore[i - 1][j]);
                    } else {
                        maxVal = Math.max(maxVal, dp[i - 1][x] + travelScore[x][j]);
                    }
                }
                dp[i][j] = maxVal;
                ans = Math.max(ans, maxVal);
            }
        }


        return ans;
    }

}
