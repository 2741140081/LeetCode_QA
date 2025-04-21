package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 14:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_40 {
    /**
     * @Description:
     * 「力扣挑战赛」心算项目的挑战比赛中，
     * 要求选手从 N 张卡牌中选出 cnt 张卡牌，若这 cnt 张卡牌数字总和为偶数，则选手成绩「有效」且得分为 cnt 张卡牌数字总和。
     * 给定数组 cards 和 cnt，其中 cards[i] 表示第 i 张卡牌上的数字。
     * 请帮参赛选手计算最大的有效得分。
     * 若不存在获取有效得分的卡牌方案，则返回 0。
     * @param cards
     * @param cnt
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumScore(int[] cards, int cnt) {
        int result;
        result = method_01(cards, cnt);
        return result;
    }

    /**
     * @Description:
     * cards = [1,2,2,8,9], cnt = 3
     * 1. sum % 2 == 0, return sum;
     * 2. sum % 2 == 1, 则必定存在 min_odd
     * 3. 去除一个 min_odd, 添加一个 max_even(如果存在); sum - min_odd + max_even
     * 4, 去除一个 min_even(如果存在), 添加一个 max_odd(如果存在), sum - min_even + max_odd
     *
     * AC: 81ms/59.78MB
     * @param cards
     * @param cnt
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 14:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] cards, int cnt) {
        int n = cards.length;
        Arrays.sort(cards);
        int sum = 0;
        int min_odd = -1; // 在 cnt 范围内的最小奇数
        int min_even = -1; // 在cnt 范围内的最小偶数
        int max_odd = -1;
        int max_even = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (cnt > 0) {
                sum += cards[i];
                if (cards[i] % 2 == 0) {
                    min_even = cards[i];
                } else {
                    min_odd = cards[i];
                }
                cnt--;
            } else {
                if (cards[i] % 2 == 0) {
                    max_even = Math.max(max_even, cards[i]);
                } else {
                    max_odd = Math.max(max_odd, cards[i]);
                }
            }

        }

        if (sum % 2 == 0) {
            return sum;
        } else {
            int case3 = 0;
            int case4 = 0;
            if (max_even != -1) {
                case3 = sum - min_odd + max_even;
            }
            if (min_even != -1 && max_odd != -1) {
                case4 = sum - min_even + max_odd;
            }
            return Math.max(case3, case4);
        }
    }
}
