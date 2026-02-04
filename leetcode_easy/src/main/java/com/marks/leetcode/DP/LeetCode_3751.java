package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3751 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/4 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3751 {

    /**
     * @Description:
     * 给你两个整数 num1 和 num2，表示一个 闭 区间 [num1, num2]。
     * 一个数字的 波动值 定义为该数字中 峰 和 谷 的总数：
     *
     * 如果一个数位 严格大于 其两个相邻数位，则该数位为 峰。
     * 如果一个数位 严格小于 其两个相邻数位，则该数位为 谷。
     * 数字的第一个和最后一个数位 不能 是峰或谷。
     * 任何少于 3 位的数字，其波动值均为 0。
     * 返回范围 [num1, num2] 内所有数字的波动值之和。
     * tips:
     * 1 <= num1 <= num2 <= 10^5
     * @param: num1
     * @param: num2
     * @return int
     * @author marks
     * @CreateDate: 2026/02/04 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int totalWaviness(int num1, int num2) {
        int result;
        result = method_01(num1, num2);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 4848, 有一个波峰和一个波谷, 所以波动值为2.
     * 1. 假设 num1 < x < num2, 并且 x 是一个3位数, 那么x 的波动值之和是多少?
     * 2. x_1 = 1(中间位为1), 直接暴力破解(brute force)
     * AC: 36ms/45.82MB
     * 这题直接暴力, 和dp没有关系, 看看题解有没有dp 的解法
     * @param: num1
     * @param: num2
     * @return int
     * @author marks
     * @CreateDate: 2026/02/04 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int num1, int num2) {
        int ans = 0;
        for (int i = Math.max(num1, 100); i <= num2; i++) {
            ans += getWaviness(i);
        }
        return ans;
    }

    private int getWaviness(int num) {
        int count = 0;
        List<Integer> list = new ArrayList<>();
        while (num > 0) {
            list.add(num % 10);
            num /= 10;
        }
        int size = list.size();
        for (int i = 1; i < size - 1; i++) {
            // 峰和谷不在两个端点
            if (list.get(i) > list.get(i - 1) && list.get(i) > list.get(i + 1)) {
                count++;
            }
            if (list.get(i) < list.get(i - 1) && list.get(i) < list.get(i + 1)) {
                count++;
            }
        }
        return count;
    }

}
