package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_877 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/14 17:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_877 {

    /**
     * @Description:
     * Alice 和 Bob 用几堆石子在做游戏。
     * 一共有偶数堆石子，排成一行；每堆都有 正 整数颗石子，数目为 piles[i] 。
     *
     * 游戏以谁手中的石子最多来决出胜负。石子的 总数 是 奇数 ，所以没有平局。
     *
     * Alice 和 Bob 轮流进行，Alice 先开始 。
     * 每回合，玩家从行的 开始 或 结束 处取走整堆石头。
     * 这种情况一直持续到没有更多的石子堆为止，此时手中 石子最多 的玩家 获胜 。
     *
     * 假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回 true ，当 Bob 赢得比赛时返回 false 。
     * tips:
     * 2 <= piles.length <= 500
     * piles.length 是 偶数
     * 1 <= piles[i] <= 500
     * sum(piles[i]) 是 奇数
     * @param: piles
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/14 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean stoneGame(int[] piles) {
        boolean result;
        result = method_01(piles);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：piles = [5,3,4,5]
     * 输出：true
     * 1. 什么情况下才会返回false? 1 2 3 4 5 4 3 2 100 1
     * 1 + 3 + 5 + 3 + 100
     * 2. 先取的人可以选择取奇数列还是偶数列, 并且可以决定让对手取奇数列还是偶数列, 对于剩下的偶数个的子数组,
     * 假设A取了第0个元素, 那么B只能取奇数列, 即第一个或者是最后一个,
     * @param: piles
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/14 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(int[] piles) {
        return true;
    }
}
