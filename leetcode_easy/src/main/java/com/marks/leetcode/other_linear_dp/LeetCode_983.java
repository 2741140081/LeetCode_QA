package com.marks.leetcode.other_linear_dp;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 14:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_983 {
    private int[] costs;
    private Integer[] memo = new Integer[366];
    private Set<Integer> daySet = new HashSet<>();
    private int endDay;
    /**
     * @Description: [
     * 在一个火车旅行很受欢迎的国度，你提前一年计划了一些火车旅行。在接下来的一年里，你要旅行的日子将以一个名为 days 的数组给出。每一项是一个从 1 到 365 的整数。
     *
     * 火车票有 三种不同的销售方式 ：
     *
     * 一张 为期一天 的通行证售价为 costs[0] 美元；
     * 一张 为期七天 的通行证售价为 costs[1] 美元；
     * 一张 为期三十天 的通行证售价为 costs[2] 美元。
     * 通行证允许数天无限制的旅行。 例如，如果我们在第 2 天获得一张 为期 7 天 的通行证，那么我们可以连着旅行 7 天：第 2 天、第 3 天、第 4 天、第 5 天、第 6 天、第 7 天和第 8 天。
     *
     * 返回 你想要完成在给定的列表 days 中列出的每一天的旅行所需要的最低消费 。
     *
     * tips:
     * 1 <= days.length <= 365
     * 1 <= days[i] <= 365
     * days 按顺序严格递增
     * costs.length == 3
     * 1 <= costs[i] <= 1000
     * ]
     * @param days 
     * @param costs 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 14:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int mincostTickets(int[] days, int[] costs) {
        int result = 0;
        result = method_01(days, costs);
        return result;
    }
    /**
     * @Description: [
     * 输入：days = [1,4,6,7,8,20], costs = [2,7,15]
     * 输出：11
     * 解释：
     * 例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划：
     * 在第 1 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 1 天生效。
     * 在第 3 天，你花了 costs[1] = $7 买了一张为期 7 天的通行证，它将在第 3, 4, ..., 9 天生效。
     * 在第 20 天，你花了 costs[0] = $2 买了一张为期 1 天的通行证，它将在第 20 天生效。
     * 你总共花了 $11，并完成了你计划的每一天旅行。
     *
     * AC:2ms/41.02MB
     * ]
     * @param days 
     * @param costs 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 14:24
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] days, int[] costs) {
        this.costs = costs;
        endDay = days[days.length - 1];
        for (int day : days) {
            daySet.add(day);
        }

        return dfs(1);
    }

    private int dfs(int i) {
        if (i > endDay) {
            return 0;
        }
        if (memo[i] != null) {
            return memo[i];
        }

        if (daySet.contains(i)) {
            memo[i] = Math.min(Math.min(dfs(i + 1) + costs[0], dfs(i + 7) + costs[1]), dfs(i + 30) + costs[2]);
        }else {
            memo[i] = dfs(i + 1);
        }
        return memo[i];
    }
}
