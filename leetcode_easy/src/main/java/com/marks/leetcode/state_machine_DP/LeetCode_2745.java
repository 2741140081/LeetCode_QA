package com.marks.leetcode.state_machine_DP;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/2 15:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2745 {
    /**
     * @Description: [
     * 给你三个整数 x ，y 和 z 。
     *
     * 这三个整数表示你有 x 个 "AA" 字符串，y 个 "BB" 字符串，和 z 个 "AB" 字符串。
     * 你需要选择这些字符串中的部分字符串（可以全部选择也可以一个都不选择），将它们按顺序连接得到一个新的字符串。
     * 新字符串不能包含子字符串 "AAA" 或者 "BBB" 。
     *
     * 请你返回 新字符串的最大可能长度。
     *
     * 子字符串 是一个字符串中一段连续 非空 的字符序列。
     *
     * tips:1 <= x, y, z <= 50
     * ]
     * @param x
     * @param y
     * @param z
     * @return int
     * @author marks
     * @CreateDate: 2024/9/2 15:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestString(int x, int y, int z) {
        int result = 0;
        result = method_01(x, y, z);
        return result;
    }

    /**
     * @Description: [
     * 怎么感觉纯粹就是分析题
     * if x > y
     *
     * else x <= y
     * ]
     * @param x
     * @param y
     * @param z
     * @return int
     * @author marks
     * @CreateDate: 2024/9/2 15:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int x, int y, int z) {
        if (Math.abs(x - y) <= 1) {
            return 2 * (x + y + z);
        }else if (x - y > 1) {
            return 2 * (2 * y + 1 + z);
        } else if (y - x > 1) {
            return 2 * (2 * x + 1 + z);
        }
        return 0;
    }
}
