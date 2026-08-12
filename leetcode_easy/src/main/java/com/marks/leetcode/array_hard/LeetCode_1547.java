package com.marks.leetcode.array_hard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1547 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/12 14:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1547 {

    /**
     * @Description:
     * 有一根长度为 n 个单位的木棍，棍上从 0 到 n 标记了若干位置。例如，长度为 6 的棍子可以标记如下：
     * 给你一个整数数组 cuts ，其中 cuts[i] 表示你需要将棍子切开的位置。
     * 你可以按顺序完成切割，也可以根据需要更改切割的顺序。
     * 每次切割的成本都是当前要切割的棍子的长度，切棍子的总成本是历次切割成本的总和。
     * 对棍子进行切割将会把一根木棍分成两根较小的木棍（这两根木棍的长度和就是切割前木棍的长度）。
     * 请参阅第一个示例以获得更直观的解释。
     * 返回切棍子的 最小总成本 。
     * tips:
     * 2 <= n <= 10^6
     * 1 <= cuts.length <= min(n - 1, 100)
     * 1 <= cuts[i] <= n - 1
     * cuts 数组中的所有整数都 互不相同
     * @param: n
     * @param: cuts
     * @return int
     * @author marks
     * @CreateDate: 2026/08/12 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minCost(int n, int[] cuts) {
        int result;
        result = method_01(n, cuts);
        return result;
    }

    private static final long BASE = 1000001L; // 10^6 + 1
    private Map<Long, Integer> memo;
    /**
     * @Description:
     * 1. 动态规划 + 记忆化搜索, dp[0][n] 是切割, 但是这个数据范围会超空间, 所以应该使用 Map 进行离散存储
     * 2. 可以对 cuts 进行排序
     * AC: 206ms/46.95MB
     * @param: n
     * @param: cuts
     * @return int
     * @author marks
     * @CreateDate: 2026/08/12 14:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] cuts) {
        memo = new HashMap<>();
        Arrays.sort(cuts);
        int m = cuts.length;
        // 执行递归
        return dfs(0, n, cuts, 0, m - 1);
    }

    private int dfs(int left, int right, int[] cuts, int leftPoint, int rightPoint) {
        if (left >= right || leftPoint > rightPoint) { // 非法情况
            return 0;
        }
        long key = getKey(left, right);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int ans = Integer.MAX_VALUE;
        // 从 leftPoint ~ rightPoint 选择可以作为本次切割的
        for (int i = leftPoint; i <= rightPoint; i++) {
            int curr = cuts[i];
            // 本次切割成本 right - left
            int sum = (right - left) + dfs(left, curr, cuts, leftPoint, i - 1) +
                    dfs(curr, right, cuts, i + 1, rightPoint);
            ans = Math.min(ans, sum);
        }
        memo.put(key, ans);

        return ans;
    }

    private long getKey(int i, int j) {
        return (long)i * BASE + j;
    }


}
