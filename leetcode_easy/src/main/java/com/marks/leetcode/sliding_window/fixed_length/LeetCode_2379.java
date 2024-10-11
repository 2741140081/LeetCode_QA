package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/11 15:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2379 {
    /**
     * @Description: [
     * 给你一个长度为 n 下标从 0 开始的字符串 blocks ，blocks[i] 要么是 'W' 要么是 'B' ，表示第 i 块的颜色。字符 'W' 和 'B' 分别表示白色和黑色。
     * 给你一个整数 k ，表示想要 连续 黑色块的数目。
     * 每一次操作中，你可以选择一个白色块将它 涂成 黑色块。
     *
     * 请你返回至少出现 一次 连续 k 个黑色块的 最少 操作次数。
     * 求长度k的窗口中, 字符“B” 出现的最大的次数maxB, 则最少操作数 = k - maxB
     * 或者, 直接求长度k的窗口中, 字符"W" 出现的最小次数minW, 则最少操作数为 = minW
     * 可以剪枝 即minW == 0时, 退出循环
     * tips:
     * n == blocks.length
     * 1 <= n <= 100
     * blocks[i] 要么是 'W' ，要么是 'B' 。
     * 1 <= k <= n
     * ]
     * @param blocks 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/11 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumRecolors(String blocks, int k) {
        int result = 0;
        result = method_01(blocks, k);
        return result;
    }
    /**
     * @Description: [
     * AC:1ms/40.53MB
     * ]
     * @param blocks
     * @param k
     * @return int
     * @author marks
     * @CreateDate: 2024/10/11 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String blocks, int k) {
        int n = blocks.length();
        int minBlock;
        int sumWhite = 0;
        for (int i = 0; i < k; i++) {
            if (blocks.charAt(i) == 'W') {
                sumWhite++;
            }
        }
        minBlock = sumWhite;
        for (int i = k; i < n; i++) {
            sumWhite = sumWhite + checkBlackOrWhite(blocks, i, k);
            minBlock = Math.min(minBlock, sumWhite);
            if (minBlock == 0) {
                return minBlock;
            }
        }
        return minBlock;
    }

    private int checkBlackOrWhite(String blocks, int i, int k) {
        int in = blocks.charAt(i) == 'W' ? 1 : 0;
        int out = blocks.charAt(i - k) == 'W' ? -1 : 0;
        return in + out;
    }
}
