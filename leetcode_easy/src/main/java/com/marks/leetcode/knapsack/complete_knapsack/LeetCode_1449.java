package com.marks.leetcode.knapsack.complete_knapsack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/7 9:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1449 {
    /**
     * @Description: [
     * 给你一个整数数组 cost 和一个整数 target 。请你返回满足如下规则可以得到的 最大 整数：
     *
     * 给当前结果添加一个数位（i + 1）的成本为 cost[i] （cost 数组下标从 0 开始）。
     * 总成本必须恰好等于 target 。
     * 添加的数位中没有数字 0 。
     * 由于答案可能会很大，请你以字符串形式返回。
     *
     * 如果按照上述要求无法得到任何整数，请你返回 "0" 。
     * tips:
     * cost.length == 9
     * 1 <= cost[i] <= 5000
     * 1 <= target <= 5000
     * ]
     * @param cost
     * @param target
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/8/7 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String largestNumber(int[] cost, int target) {
        String result = "";
//        result = method_01(cost, target);
//        result = method_02(cost, target);
        result = method_03(cost, target);
        return result;
    }

    private String method_03(int[] cost, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;
        for (int c : cost) {
            for (int j = c; j <= target; j++) {
                dp[j] = Math.max(dp[j], dp[j - c] + 1);
            }
        }

        if (dp[target] < 0) {
            return "0";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 8, j = target; i >= 0; i--) {
            for (int c = cost[i]; j >= c && dp[j] == dp[j - c] + 1; j = j - c) {
                sb.append(i + 1);
            }
        }
        return sb.toString();
    }

    private String method_02(int[] cost, int target) {
        int[][] dp = new int[10][target + 1];
        for (int i = 0; i < 10; i++) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        dp[0][0] = 0;
        int[][] from = new int[10][target + 1];
        for (int i = 0; i < 9; i++) {
            int temp = cost[i];
            for (int j = 0; j <= target; j++) {
                if (j < temp) {
                    dp[i + 1][j] = dp[i][j];
                    from[i + 1][j] = j;
                }else {
                    // j >= temp
                    if (dp[i][j] > dp[i + 1][j - temp] + 1) {
                        dp[i + 1][j] = dp[i][j];
                        from[i + 1][j] = j;
                    }else {
                        dp[i + 1][j] = dp[i + 1][j - temp] + 1;
                        from[i + 1][j] = j - temp;
                    }
                }
            }
        }
        if (dp[9][target] < 0) {
            return "0";
        }

        StringBuffer sb = new StringBuffer();
        int i = 9, j = target;
        while (i > 0) {
            if (j == from[i][j]) {
                i--;
            }else {
                sb.append(i);
                j = from[i][j];
            }
        }
        return sb.toString();
    }

    /**
     * @Description: [
     * E1:
     * 输入：cost = [4,3,2,5,6,7,2,5,5], target = 9
     * 输出："7772"
     * 解释：添加数位 '7' 的成本为 2 ，添加数位 '2' 的成本为 3 。所以 "7772" 的代价为 2*3+ 3*1 = 9 。 "977" 也是满足要求的数字，但 "7772" 是较大的数字。
     *  数字     成本
     *   1  ->   4
     *   2  ->   3
     *   3  ->   2
     *   4  ->   5
     *   5  ->   6
     *   6  ->   7
     *   7  ->   2
     *   8  ->   5
     *   9  ->   5
     * 看懂题意: 需要再成本中找到集合[x1, x2, x3, x4, .... x_n]
     * 这是一个完全背包问题, 即x1~x_n 的数量不受限制
     * 重点是如何找到对应的成本
     * 1. 希望最后的结果越大越好
     * 2. 成本中存在相同的成本值
     * 3. 当成本相同时, 取数字更大的, Eg: 3  ->   2 和 7  ->   2 那么取 7  ->   2
     * 去重后的cost = [4, 3, 6, 7, 2, 5]
     * 对应数位index= [1, 2, 5, 6, 7, 9]
     * len = 6, target = 9
     * int[][] dp = new int[7][10]
     * dp[0][0] = 0;
     * 对于temp = cost[i - 1]
     * 1. 不取
     * dp[i][j] = dp[i-1][j]
     * 2. 取
     * dp[i][j] = dp[i-1][j-temp] + 1
     * 另外与硬币兑换相似, 需要最多的硬币数量(5位数肯定比4位数大)
     *
     * ]
     * @param cost
     * @param target
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/8/7 9:59
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int[] cost, int target) {
        // 去重数组
        List<Integer> list = new ArrayList<>();
        List<Integer> strList = new ArrayList<>();
        for (int i = cost.length - 1; i >= 0; i--) {
            if (!list.contains(cost[i])) {
                list.add(cost[i]);
                strList.add(i + 1);
            }
        }
        int[] cost_new = list.stream().mapToInt(Integer::intValue).toArray();
        int[] index_str = strList.stream().mapToInt(Integer::intValue).toArray();

        int n = cost_new.length;
        int[][] dp = new int[n + 1][target + 1];
        int[][] from = new int[n + 1][target + 1];
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            int temp = cost_new[i - 1];
            for (int j = 0; j <= target; j++) {
                int num = j / temp; // 最多可以拿num个coins[i - 1]
                for (int k = 0; k <= num; k++) {
                    if (j >= k * temp) {
                        if (dp[i][j] < dp[i - 1][j - k * temp] + k) {
                            dp[i][j] = dp[i - 1][j - k * temp] + k;
                            from[i][j] = j - k * temp;
                        }else {
                            dp[i][j] = dp[i][j];
                            from[i][j] = j;
                        }
                    }else {
                        dp[i][j] = dp[i - 1][j];
                        from[i][j] = j;
                    }
                }
            }
        }
        return null;
    }
}
