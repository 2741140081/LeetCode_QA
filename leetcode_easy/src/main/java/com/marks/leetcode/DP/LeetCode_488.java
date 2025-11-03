package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_488 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/31 14:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_488 {

    /***
     * @Description:
     * 你正在参与祖玛游戏的一个变种。
     *
     * 在这个祖玛游戏变体中，桌面上有 一排 彩球，
     * 每个球的颜色可能是：红色 'R'、黄色 'Y'、蓝色 'B'、绿色 'G' 或白色 'W' 。你的手中也有一些彩球。
     *
     * 你的目标是 清空 桌面上所有的球。每一回合：
     *
     * 从你手上的彩球中选出 任意一颗 ，然后将其插入桌面上那一排球中：两球之间或这一排球的任一端。
     * 接着，如果有出现 三个或者三个以上 且 颜色相同 的球相连的话，就把它们移除掉。
     * 如果这种移除操作同样导致出现三个或者三个以上且颜色相同的球相连，则可以继续移除这些球，直到不再满足移除条件。
     * 如果桌面上所有球都被移除，则认为你赢得本场游戏。
     * 重复这个过程，直到你赢了游戏或者手中没有更多的球。
     * 给你一个字符串 board ，表示桌面上最开始的那排球。另给你一个字符串 hand ，表示手里的彩球。
     * 请你按上述操作步骤移除掉桌上所有球，计算并返回所需的 最少 球数。如果不能移除桌上所有的球，返回 -1 。
     *
     * tips:
     * 1 <= board.length <= 16
     * 1 <= hand.length <= 5
     * board 和 hand 由字符 'R'、'Y'、'B'、'G' 和 'W' 组成
     * 桌面上一开始的球中，不会有三个及三个以上颜色相同且连着的球
     * @param: board
     * @param: hand
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMinStep(String board, String hand) {
        int result;
        result = method_01(board, hand);
        return result;
    }

    /***
     * @Description:
     * 1.
     * @param: board
     * @param: hand
     * @return int
     * @author marks
     * @CreateDate: 2025/10/31 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String board, String hand) {

        return 0;
    }

}
