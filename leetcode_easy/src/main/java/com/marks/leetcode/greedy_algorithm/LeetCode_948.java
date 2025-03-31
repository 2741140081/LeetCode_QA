package com.marks.leetcode.greedy_algorithm;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/31 17:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_948 {
    /**
     * @Description:
     * 你的初始 能量 为 power，初始 分数 为 0，只有一包令牌以整数数组 tokens 给出。
     * 其中 tokens[i] 是第 i 个令牌的值（下标从 0 开始）。
     *
     * 你的目标是通过有策略地使用这些令牌以 最大化 总 分数。
     * 在一次行动中，你可以用两种方式中的一种来使用一个 未被使用的 令牌（但不是对同一个令牌使用两种方式）：
     *
     * 朝上：如果你当前 至少 有 tokens[i] 点 能量 ，可以使用令牌 i ，失去 tokens[i] 点 能量 ，并得到 1 分 。
     * 朝下：如果你当前至少有 1 分 ，可以使用令牌 i ，获得 tokens[i] 点 能量 ，并失去 1 分 。
     * 在使用 任意 数量的令牌后，返回我们可以得到的最大 分数
     * @param tokens
     * @param power
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 17:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int bagOfTokensScore(int[] tokens, int power) {
        int result;
        result = method_01(tokens, power);
        return result;
    }

    /**
     * @Description:
     * 排序 + 贪心
     * power = 2
     * [1,2,3,4,5,6,7]
     * 1.1 p = 8, s = 2 => p = 3
     * 1.2 p = 7, s = 2 => p = 3
     *
     * 2.1 p = 12 s = 3,
     * 2.2 p = 11, s = 3,
     * @param tokens
     * @param power
     * @return int
     * @author marks
     * @CreateDate: 2025/3/31 17:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] tokens, int power) {
        return 0;
    }
}
