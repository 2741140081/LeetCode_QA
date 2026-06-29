package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3207 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/26 16:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3207 {

    /**
     * @Description:
     * 给你一个下标从 0 开始的整数数组 enemyEnergies ，它表示一个下标从 0 开始的敌人能量数组。
     * 同时给你一个整数 currentEnergy ，它表示你一开始拥有的能量值总量。
     * 你一开始的分数为 0 ，且一开始所有的敌人都未标记。
     * 你可以通过以下操作 之一 任意次（也可以 0 次）来得分：
     * 选择一个 未标记 且满足 currentEnergy >= enemyEnergies[i] 的敌人 i 。在这个操作中：
     * 你会获得 1 分。
     * 你的能量值减少 enemyEnergies[i] ，也就是说 currentEnergy = currentEnergy - enemyEnergies[i] 。
     * 如果你目前 至少 有 1 分，你可以选择一个 未标记 的敌人 i 。在这个操作中：
     * 你的能量值增加 enemyEnergies[i] ，也就是说 currentEnergy = currentEnergy + enemyEnergies[i] 。
     * 敌人 i 被标记 。
     * 请你返回通过以上操作，最多 可以获得多少分。
     * tips:
     * 1 <= enemyEnergies.length <= 10^5
     * 1 <= enemyEnergies[i] <= 10^9
     * 0 <= currentEnergy <= 10^9
     * @param: enemyEnergies
     * @param: currentEnergy
     * @return long
     * @author marks
     * @CreateDate: 2026/06/26 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long maximumPoints(int[] enemyEnergies, int currentEnergy) {
        long result;
        result = method_01(enemyEnergies, currentEnergy);
        return result;
    }

    /**
     * @Description:
     * 1. 先对 enemyEnergies 进行排序
     * 2. 每标记一个敌人能获得的分数, int min = enemyEnergies[0]; 对于每一个 i, 0 < i < n; sum += (enemyEnergies[i] - min)
     * 3. 最后得分为 long ans = sum / min 即为结果
     * 4. 理解错误, 操作2不会减少分数
     * AC: 28ms/88.55MB, 可以不使用排序, 只需要记录最小 min 即可, 优化为 O(n)
     * AC: 1ms/88.39MB
     * @param: enemyEnergies
     * @param: currentEnergy
     * @return long
     * @author marks
     * @CreateDate: 2026/06/26 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] enemyEnergies, int currentEnergy) {
        long sum = currentEnergy, min = enemyEnergies[0];
        int n = enemyEnergies.length;
        for (int enemyEnergy : enemyEnergies) {
            sum += enemyEnergy;
            min = Math.min(min, enemyEnergy);
        }
        if (currentEnergy < min) {
            return 0;
        }
        sum -= (min * n); // 需要执行 n - 1 次

        return sum / min + (n - 1);
    }

}
