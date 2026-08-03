package com.marks.leetcode.array_hard;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1406 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/3 10:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1406 {

    /**
     * @Description:
     * Alice 和 Bob 继续他们的石子游戏。几堆石子 排成一行 ，每堆石子都对应一个得分，由数组 stoneValue 给出。
     * Alice 和 Bob 轮流取石子，Alice 总是先开始。
     * 在每个玩家的回合中，该玩家可以拿走剩下石子中的的前 1、2 或 3 堆石子 。
     * 比赛一直持续到所有石头都被拿走。
     * 每个玩家的最终得分为他所拿到的每堆石子的对应得分之和。
     * 每个玩家的初始分数都是 0 。
     * 比赛的目标是决出最高分，得分最高的选手将会赢得比赛，比赛也可能会出现平局。
     * 假设 Alice 和 Bob 都采取 最优策略 。
     * 如果 Alice 赢了就返回 "Alice" ，Bob 赢了就返回 "Bob"，分数相同返回 "Tie" 。
     *
     * tips:
     * 1 <= stoneValue.length <= 5 * 10^4
     * -1000 <= stoneValue[i] <= 1000
     * @param: stoneValue
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/08/03 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String stoneGameIII(int[] stoneValue) {
        String result;
        result = method_01(stoneValue);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划, 查看官方题解
     * AC: 8ms/83.72MB
     * @param: stoneValue
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/08/03 10:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(int[] stoneValue) {
        int n = stoneValue.length;

        // 1. 预处理后缀和数组 (Suffix Sum)
        // suffixSum[i] 表示从第 i 堆石子到最后一堆石子的总价值。
        // 这个数组用于快速计算某一段区间的石子总价值。
        int[] suffixSum = new int[n];
        // 初始化最后一堆的后缀和
        suffixSum[n - 1] = stoneValue[n - 1];
        // 从后向前递推计算后缀和
        // suffixSum[i] = 当前石子价值 + 之后所有石子的价值
        for (int i = n - 2; i >= 0; --i) {
            suffixSum[i] = suffixSum[i + 1] + stoneValue[i];
        }

        // 2. 动态规划数组
        // f[i] 表示：当轮到某位玩家从第 i 堆石子开始取时，该玩家能获得的最大分数。
        // 注意：这里不区分是 Alice 还是 Bob，因为双方都是理性的，都追求利益最大化。
        int[] f = new int[n + 1];

        // 边界情况：当没有石子可取时（索引为 n），分数为 0
        f[n] = 0;

        // 3. DP 状态转移（从后向前填表）
        for (int i = n - 1; i >= 0; --i) {
            // 初始化 bestj。
            // 假设当前玩家只取 1 堆（即 i+1），那么轮到对手时的起始位置是 i+1。
            // 对手从 i+1 开始能获得的最大分数是 f[i+1]。
            // bestj 的含义是：在当前玩家做出选择后，**对手**能获得的最小分数。
            // 为什么是对手的最小值？因为当前玩家希望对手得分越少越好，这样自己得分（总分 - 对手得分）才越多。
            int bestj = f[i + 1];

            // 尝试当前玩家取 2 堆和 3 堆的情况
            // j 代表当前玩家取完石子后，下一轮开始的位置（即对手的起始位置）
            for (int j = i + 2; j <= i + 3 && j <= n; ++j) {
                // 更新 bestj，寻找让对手得分最小的那个 j
                bestj = Math.min(bestj, f[j]);
            }

            // 核心状态转移方程：
            // 当前玩家得分 = 当前剩余所有石子的总价值 - 对手在最优策略下的得分
            // suffixSum[i] 是从 i 开始的所有石子价值。
            // bestj 是对手在下一轮能拿到的最大分（因为我们取了让对手最大分最小的情况，所以这是对手的最优解）。
            f[i] = suffixSum[i] - bestj;
        }

        // 4. 计算总分并判断胜负
        int total = 0;
        for (int value : stoneValue) {
            total += value;
        }

        // f[0] 表示先手玩家（Alice）从第 0 堆开始能拿到的最大分数。
        // 如果 Alice 的分数 * 2 等于总分，说明平局。
        // 如果 Alice 的分数 * 2 大于总分，说明 Alice 赢（Alice 分数 > Bob 分数）。
        // 否则 Bob 赢。
        if (f[0] * 2 == total) {
            return "Tie";
        } else {
            return f[0] * 2 > total ? "Alice" : "Bob";
        }
    }

}
