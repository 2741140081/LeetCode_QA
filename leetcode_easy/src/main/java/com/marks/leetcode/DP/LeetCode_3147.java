package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3147 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/31 11:27
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3147 {

    /**
     * @Description:
     * 在神秘的地牢中，n 个魔法师站成一排。每个魔法师都拥有一个属性，这个属性可以给你提供能量。
     * 有些魔法师可能会给你负能量，即从你身上吸取能量。
     * 你被施加了一种诅咒，当你从魔法师 i 处吸收能量后，你将被立即传送到魔法师 (i + k) 处。
     * 这一过程将重复进行，直到你到达一个不存在 (i + k) 的魔法师为止。
     * 换句话说，你将选择一个起点，然后以 k 为间隔跳跃，直到到达魔法师序列的末端，在过程中吸收所有的能量。
     * 给定一个数组 energy 和一个整数k，返回你能获得的 最大 能量
     *
     * tips:
     * 1 <= energy.length <= 10^5
     * -1000 <= energy[i] <= 1000
     * 1 <= k <= energy.length - 1
     * @param: energy
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/31 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumEnergy(int[] energy, int k) {
        int result;
        result = method_01(energy, k);
        return result;
    }

    /**
     * @Description:
     * 1. 动态规划
     * 2. 能选择的只有起点, 也就是能改变的只有起点, 换个方向, 从后往前找, 就可以决定什么时候停止, 这个停止点就是选择的起点
     * 3. int[] dp = new int[k]; k个大小的数组最为dp, 因为选择从末尾开始往前遍历, n-1 ~ n - k;
     * 4. 应该直接就是动态转移方程. 初始化 dp[], 初始化 k 个值, p = n - 1 % k 取余, dp[p] = energy[n - 1];
     * 5. 当位于 i 处时, dp[i % k] = dp[i % k] + energy[i]; 并且使用 int ans 记录过程中的最大值.
     * AC: 4ms/122.5MB
     * @param: energy
     * @param: k
     * @return int
     * @author marks
     * @CreateDate: 2026/03/31 11:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] energy, int k) {
        int n = energy.length;
        int[] dp = new int[k];
        int ans = energy[n - 1];
        for (int i = n - 1; i >= n - k; i--) {
            dp[i % k] = energy[i];
            ans = Math.max(ans, dp[i % k]);
        }

        for (int i = n - k - 1; i >= 0; i--) {
            dp[i % k] += energy[i];
            ans = Math.max(ans, dp[i % k]);
        }
        return ans;
    }

}
