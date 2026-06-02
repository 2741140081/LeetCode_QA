package com.marks.leetcode.hash_table;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_822 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_822 {

    /**
     * @Description:
     * 在桌子上有 n 张卡片，每张卡片的正面和背面都写着一个正数（正面与背面上的数有可能不一样）。
     * 我们可以先翻转任意张卡片，然后选择其中一张卡片。
     * 如果选中的那张卡片背面的数字 x 与任意一张卡片的正面的数字都不同，那么这个数字是我们想要的数字。
     * 哪个数是这些想要的数字中最小的数（找到这些数中的最小值）呢？如果没有一个数字符合要求的，输出 0 。
     * 其中, fronts[i] 和 backs[i] 分别代表第 i 张卡片的正面和背面的数字。
     * 如果我们通过翻转卡片来交换正面与背面上的数，那么当初在正面的数就变成背面的数，背面的数就变成正面的数。
     *
     * @param: fronts
     * @param: backs
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int flipgame(int[] fronts, int[] backs) {
        int result;
        result = method_01(fronts, backs);
        return result;
    }

    /**
     * @Description:
     * 1. fronts[i] = backs[i], 此时数字必定不能为所需数字, 并且所选数字不能是这个数字, 可以使用 Set<Integer> disableNumbers 存储
     * 2. 然后找到最小的数字，返回
     * AC: 4ms/46.04MB
     * @param: fronts
     * @param: backs
     * @return int
     * @author marks
     * @CreateDate: 2026/06/01 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] fronts, int[] backs) {
        int n = fronts.length;
        Set<Integer> disableNumbers = new HashSet<>();

        for (int i = 0; i < n; i++) {
            int front = fronts[i];
            int back = backs[i];
            if (front == back) {
                disableNumbers.add(front);
            }
        }

        int ans = 2001;

        for (int i = 0; i < n; i++) {
            int front = fronts[i];
            int back = backs[i];
            if (!disableNumbers.contains(front)) {
                ans = Math.min(ans, front);
            }
            if (!disableNumbers.contains(back)) {
                ans = Math.min(ans, back);
            }
        }

        return ans == 2001 ? 0 : ans;
    }

}
