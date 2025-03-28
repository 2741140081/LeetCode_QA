package com.marks.leetcode.greedy_algorithm;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 10:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1561 {
    /**
     * @Description:
     * 有 3n 堆数目不一的硬币，你和你的朋友们打算按以下方式分硬币：
     *
     * 每一轮中，你将会选出 任意 3 堆硬币（不一定连续）。
     * Alice 将会取走硬币数量最多的那一堆。
     * 你将会取走硬币数量第二多的那一堆。
     * Bob 将会取走最后一堆。
     * 重复这个过程，直到没有更多硬币。
     * 给你一个整数数组 piles ，其中 piles[i] 是第 i 堆中硬币的数目。
     *
     * 返回你可以获得的最大硬币数目。
     * @param piles
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxCoins(int[] piles) {
        int result;
        result = method_01(piles);
        return result;
    }

    /**
     * @Description:
     * 不对, 不是拿 0, 1, 2, 3, 4, 5
     *
     * i = 5, min = 0
     * i = 3, min = 1
     * i = 1, min = 2
     *
     * AC: 33ms/55.39MB
     * @param piles
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] piles) {
        Arrays.sort(piles);
        int ans = 0;
        int minLeft = 0;
        for (int i = piles.length - 1; i >= minLeft + 2; i -= 2) {
            ans += piles[i - 1];
            minLeft++;
        }
        return ans;
    }
}
