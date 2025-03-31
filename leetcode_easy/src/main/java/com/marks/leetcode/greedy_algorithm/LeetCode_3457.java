package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 15:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3457 {
    /**
     * @Description:
     * 给你一个长度为 n 的整数数组 pizzas，其中 pizzas[i] 表示第 i 个披萨的重量。每天你会吃 恰好 4 个披萨。
     * 由于你的新陈代谢能力惊人，当你吃重量为 W、X、Y 和 Z 的披萨（其中 W <= X <= Y <= Z）时，你只会增加 1 个披萨的重量！体重增加规则如下：
     *
     * 在 奇数天（按 1 开始计数）你会增加 Z 的重量。
     * 在 偶数天，你会增加 Y 的重量。
     * 请你设计吃掉 所有 披萨的最优方案，并计算你可以增加的 最大 总重量。
     *
     * 注意：保证 n 是 4 的倍数，并且每个披萨只吃一次。
     * @param pizzas
     * @return long
     * @author marks
     * @CreateDate: 2025/3/31 15:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maxWeight(int[] pizzas) {
        long result;
        result = method_01(pizzas);
        return result;
    }

    /**
     * @Description:
     * 和之前有一题差不多, 就是增加的是最大的, 无关的 W, X, (Y), 是最下的去掉
     * i, i-1, i-2
     * Math.max(nums[i - 2] + nums[i], nums[i - 1] + nums[i - 2])
     * 以上存在一点问题
     * 1. 我们首先先将所有的奇数天的吃完, day_odd, sum_1 [k, n - 1]
     * 2. 我们开始吃偶数天的, day_even, sum(p[k - 1],...., p[j])
     *
     * AC: 74ms/79.29MB
     * @param pizzas
     * @return long
     * @author marks
     * @CreateDate: 2025/3/31 15:07
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] pizzas) {
        Arrays.sort(pizzas);
        int n = pizzas.length;
        long ans = 0;

        int right = n - 1;
        int maxDay = n / 4;
//        int day_odd = maxDay / 2 + (maxDay % 2 == 1 ? 1 : 0); // 更好的方式是 day_odd = (maxDay + 1) / 2;
        int day_odd = (maxDay + 1) / 2;
        int day_even = maxDay - day_odd;
        while (day_odd > 0) {
            ans += pizzas[right--];
            day_odd--;
        }
        while (day_even > 0) {
            ans += pizzas[right - 1];
            right -= 2;
            day_even--;
        }
        return ans;
    }
}
