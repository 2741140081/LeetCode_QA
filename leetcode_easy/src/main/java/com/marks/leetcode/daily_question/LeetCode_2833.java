package com.marks.leetcode.daily_question;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2833 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/24 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2833 {

    /**
     * @Description:
     * 给你一个长度为 n 的字符串 moves ，该字符串仅由字符 'L'、'R' 和 '_' 组成。字符串表示你在一条原点为 0 的数轴上的若干次移动。
     * 你的初始位置就在原点（0），第 i 次移动过程中，你可以根据对应字符选择移动方向：
     * 如果 moves[i] = 'L' 或 moves[i] = '_' ，可以选择向左移动一个单位距离
     * 如果 moves[i] = 'R' 或 moves[i] = '_' ，可以选择向右移动一个单位距离
     * 移动 n 次之后，请你找出可以到达的距离原点 最远 的点，并返回 从原点到这一点的距离 。
     * @param: moves
     * @return int
     * @author marks
     * @CreateDate: 2026/04/24 10:38
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int furthestDistanceFromOrigin(String moves) {
        int result;
        result = method_01(moves);
        return result;
    }

    // AC: 1ms/43.3MB
    private int method_01(String moves) {
        int n = moves.length();
        int left = 0, right = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            switch (moves.charAt(i)) {
                case 'L':
                    left++;
                    break;
                case 'R':
                    right++;
                    break;
                case '_':
                    count++;
                    break;
            }
        }

        return Math.abs(left - right) + count;
    }

}
