package com.marks.leetcode.daily_question;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3562 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/16 14:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3562 {

    /**
     * @Description:
     * 给你一个整数 n，表示公司中员工的数量。
     * 每位员工都分配了一个从 1 到 n 的唯一 ID ，其中员工 1 是 CEO。
     * 另给你两个下标从 1 开始的整数数组 present 和 future，两个数组的长度均为 n，具体定义如下：
     * present[i] 表示第 i 位员工今天可以购买股票的 当前价格 。
     * future[i] 表示第 i 位员工明天可以卖出股票的 预期价格 。
     * 公司的层级关系由二维整数数组 hierarchy 表示，其中 hierarchy[i] = [ui, vi] 表示员工 ui 是员工 vi 的直属上司。
     *
     * 此外，再给你一个整数 budget，表示可用于投资的总预算。
     * 公司有一项折扣政策：如果某位员工的直属上司购买了自己的股票，那么该员工可以以 半价 购买自己的股票（即 floor(present[v] / 2)）。
     *
     * 请返回在不超过给定预算的情况下可以获得的 最大利润 。
     * 注意：
     * 每只股票最多只能购买一次。
     * 不能使用股票未来的收益来增加投资预算，购买只能依赖于 budget。
     * @param: n
     * @param: present
     * @param: future
     * @param: hierarchy
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2025/12/16 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxProfit(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        int result;
        result = method_01(n, present, future, hierarchy, budget);
        result = method_02(n, present, future, hierarchy, budget);
        return result;
    }

    /**
     * @Description:
     * 1. 看看官方题解吧, 有点难写.
     * @param: n
     * @param: present
     * @param: future
     * @param: hierarchy
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2025/12/16 15:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int[] e : hierarchy) {
            g[e[0] - 1].add(e[1] - 1);
        }

        return dfs(0, present, future, g, budget).dp0[budget];
    }

    private Result dfs(int u, int[] present, int[] future, List<Integer>[] g, int budget) {
        int cost = present[u];
        int dCost = present[u] / 2;
        // dp[u][state][budget]
        // state = 0: 不购买父节点, state = 1: 必须购买父节点
        int[] dp0 = new int[budget + 1];
        int[] dp1 = new int[budget + 1];

        // subProfit[state][budget]
        // state = 0: 优惠不可用, state = 1: 优惠可用
        int[] subProfit0 = new int[budget + 1];
        int[] subProfit1 = new int[budget + 1];
        int uSize = cost;

        for (int v : g[u]) {
            Result childResult = dfs(v, present, future, g, budget);
            uSize += childResult.size;

            for (int i = budget; i >= 0; i--) {
                for (int sub = 0; sub <= Math.min(childResult.size, i); sub++) {
                    if (i - sub >= 0) {
                        subProfit0[i] = Math.max(subProfit0[i], subProfit0[i - sub] + childResult.dp0[sub]);
                        subProfit1[i] = Math.max(subProfit1[i], subProfit1[i - sub] + childResult.dp1[sub]);
                    }
                }
            }
        }

        for (int i = 0; i <= budget; i++) {
            dp0[i] = subProfit0[i];
            dp1[i] = subProfit0[i];
            if (i >= dCost) {
                dp1[i] = Math.max(subProfit0[i], subProfit1[i - dCost] + future[u] - dCost);
            }
            if (i >= cost) {
                dp0[i] = Math.max(subProfit0[i], subProfit1[i - cost] + future[u] - cost);
            }
        }

        return new Result(dp0, dp1, uSize);
    }

    class Result {
        int[] dp0;
        int[] dp1;
        int size;

        public Result(int[] dp0, int[] dp1, int size) {
            this.dp0 = dp0;
            this.dp1 = dp1;
            this.size = size;
        }
    }

    /**
     * @Description:
     * E1:
     * 输入： n = 3, present = [5,2,3], future = [8,5,6], hierarchy = [[1,2],[2,3]], budget = 7
     * 输出： 12 = 3 + (5 - 1) + (6 - 1) = 3 + 4 + 5 = 12
     * 1. 这题是动态规划, 需要判断员工 i 的直属上司是否购买了自己的股票, 假设用一个3维数组 dp[i][j][k]
     * 2. dp[i][j][k], i 表示第i位员工, j表示剩余的预算, k表示第i位员工是否购买了自己的股票(0:没有, 1:购买)
     * 3. 状态转移方程: dp[i][j][k] = max(dp[i][j][k], dp[0~i - 1][j - present[i]][0] + future[i])
     * 4. 用邻接表先将员工关系构建成图
     * @param: n
     * @param: present
     * @param: future
     * @param: hierarchy
     * @param: budget
     * @return int
     * @author marks
     * @CreateDate: 2025/12/16 14:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n, int[] present, int[] future, int[][] hierarchy, int budget) {
        // 构建邻接表
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] edge : hierarchy) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
        }

        int[][][] dp = new int[n][budget + 1][2];
        dp[0][budget - present[0]][1] = future[0] - present[0];
        // 队列
        Queue<Integer> queue = new LinkedList<>();
        while (!queue.isEmpty()) {
            int curr = queue.poll();
        }
        return 0;
    }

}
