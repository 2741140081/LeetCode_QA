package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1140 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 11:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1140 {

    /**
     * @Description:
     * Alice 和 Bob 继续他们的石子游戏。许多堆石子 排成一行，每堆都有正整数颗石子 piles[i]。游戏以谁手中的石子最多来决出胜负。
     * Alice 和 Bob 轮流进行，Alice 先开始。最初，M = 1。
     * 在每个玩家的回合中，该玩家可以拿走剩下的 前 X 堆的所有石子，其中 1 <= X <= 2M。然后，令 M = max(M, X)。
     * 游戏一直持续到所有石子都被拿走。
     * 假设 Alice 和 Bob 都发挥出最佳水平，返回 Alice 可以得到的最大数量的石头。
     * tips:
     * 1 <= piles.length <= 100
     * 1 <= piles[i] <= 10^4
     * @param: piles
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 11:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */

    public int stoneGameII(int[] piles) {
        int result;
        result = method_01(piles);
        return result;
    }

    /**
     * @Description:
     * 1. 可以用递归进行模拟吗? 不太行, 没有思路, 看看题解吧
     * need todo
     * @param: piles
     * @return int
     * @author marks
     * @CreateDate: 2026/02/05 11:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] piles) {

        return 0;
    }

}
