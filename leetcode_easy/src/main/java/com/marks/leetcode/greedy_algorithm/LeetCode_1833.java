package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/25 17:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1833 {
    /**
     * @Description:
     * 夏日炎炎，小男孩 Tony 想买一些雪糕消消暑。
     * 商店中新到 n 支雪糕，用长度为 n 的数组 costs 表示雪糕的定价，其中 costs[i] 表示第 i 支雪糕的现金价格。
     * Tony 一共有 coins 现金可以用于消费，他想要买尽可能多的雪糕。
     *
     * 注意：Tony 可以按任意顺序购买雪糕。
     *
     * 给你价格数组 costs 和现金量 coins ，请你计算并返回 Tony 用 coins 现金能够买到的雪糕的 最大数量 。
     *
     * 你必须使用计数排序解决此问题。
     * @param costs 
     * @param coins
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxIceCream(int[] costs, int coins) {
        int result;
        result = method_01(costs, coins);
        return result;
    }


    /**
     * @Description:
     * AC: 36ms/58.04MB
     * @param costs 
     * @param coins 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/25 17:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] costs, int coins) {
        Arrays.sort(costs);
        int ans = 0;
        for (int i = 0; i < costs.length; i++) {
            if (coins >= costs[i]) {
                coins -= costs[i];
                ans++;
            }
        }
        return ans;
    }
}
