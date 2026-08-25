package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1872 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/24 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1872 {

    /**
     * @Description:
     * Alice 和 Bob 玩一个游戏，两人轮流操作， Alice 先手 。
     * 总共有 n 个石子排成一行。轮到某个玩家的回合时，如果石子的数目 大于 1 ，他将执行以下操作：
     * 选择一个整数 x > 1 ，并且 移除 最左边的 x 个石子。
     * 将 移除 的石子价值之 和 累加到该玩家的分数中。
     * 将一个 新的石子 放在最左边，且新石子的值为被移除石子值之和。
     * 当只剩下 一个 石子时，游戏结束。
     * Alice 和 Bob 的 分数之差 为 (Alice 的分数 - Bob 的分数) 。 Alice 的目标是 最大化 分数差，Bob 的目标是 最小化 分数差。
     * 给你一个长度为 n 的整数数组 stones ，其中 stones[i] 是 从左边起 第 i 个石子的价值。
     * 请你返回在双方都采用 最优 策略的情况下，Alice 和 Bob 的 分数之差 。
     * @param: stones
     * @return int
     * @author marks
     * @CreateDate: 2026/08/24 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int stoneGameVIII(int[] stones) {
        int result;
        result = method_01(stones);
        return result;
    }

    /**
     * @Description: [方法描述]
     * @param: stones
     * @return int
     * @author marks
     * @CreateDate: 2026/08/24 14:44
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] stones) {

        return 0;
    }

}
