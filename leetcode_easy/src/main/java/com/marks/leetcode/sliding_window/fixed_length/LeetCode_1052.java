package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/11 17:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1052 {
    /**
     * @Description: [
     * 有一个书店老板，他的书店开了 n 分钟。每分钟都有一些顾客进入这家商店。给定一个长度为 n 的整数数组 customers ，其中 customers[i] 是在第 i 分钟开始时进入商店的顾客数量，所有这些顾客在第 i 分钟结束后离开。
     *
     * 在某些分钟内，书店老板会生气。 如果书店老板在第 i 分钟生气，那么 grumpy[i] = 1，否则 grumpy[i] = 0。
     *
     * 当书店老板生气时，那一分钟的顾客就会不满意，若老板不生气则顾客是满意的。
     *
     * 书店老板知道一个秘密技巧，能抑制自己的情绪，可以让自己连续 minutes 分钟不生气，但却只能使用一次。
     *
     * 请你返回 这一天营业下来，最多有多少客户能够感到满意 。
     *
     * tips:
     * n == customers.length == grumpy.length
     * 1 <= minutes <= n <= 2 * 10^4
     * 0 <= customers[i] <= 1000
     * grumpy[i] == 0 or 1
     * ]
     * @param customers 
     * @param grumpy 
     * @param minutes 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/11 17:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxSatisfied(int[] customers, int[] grumpy, int minutes) {
        int result = 0;
        result = method_01(customers, grumpy, minutes);
        return result;
    }
    /**
     * @Description: [
     * AC:3ms/46.34MB
     * ]
     * @param customers
     * @param grumpy
     * @param minutes
     * @return int
     * @author marks
     * @CreateDate: 2024/10/11 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] customers, int[] grumpy, int minutes) {
        int n = customers.length;
        int ans = 0;
        int count = 0;
        int sum = 0;
        // 初始一个 minutes 长度的窗口
        for (int i = 0; i < minutes; i++) {
            if (grumpy[i] == 1) {
                // 计算心情不好的顾客数量
                sum += customers[i];
            }else {
                // 计算好心情的总顾客数量
                ans += customers[i];
            }
        }
        count = sum;
        for (int i = minutes; i < n; i++) {
            if (grumpy[i] == 1) {
                // 窗口右移, 如果心情不好, 添加顾客
                sum += customers[i];
            }else {
                // 计算好心情的总顾客数量
                ans += customers[i];
            }

            if (grumpy[i - minutes] == 1) {
                // 去除窗口移动后, 如果去除的是心情不好时, 则去掉此时的顾客
                sum = sum - customers[i - minutes];
            }
            // count 记录 长度minutes 窗口中心情不好的顾客最大数量
            count = Math.max(count, sum);
        }
        ans = ans + count;
        return ans;
    }
}
