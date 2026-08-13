package com.marks.leetcode.array;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_517 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/30 11:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_517 {

    /**
     * @Description:
     * 假设有 n 台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的。
     * 在每一步操作中，你可以选择任意 m (1 <= m <= n) 台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机。
     * 给定一个整数数组 machines 代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的 最少的操作步数 。
     * 如果不能使每台洗衣机中衣物的数量相等，则返回 -1
     *
     * tips:
     * n == machines.length
     * 1 <= n <= 10^4
     * 0 <= machines[i] <= 10^5
     * @param: machines
     * @return int
     * @author marks
     * @CreateDate: 2026/04/30 11:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int findMinMoves(int[] machines) {
        int result;
        result = method_01(machines);
        return result;
    }

    /**
     * @Description:
     * E1: [-1, -2, 3]
     * 1. 假设使用前缀和, preSum[i] = 0 表示前 i 个洗衣机中衣服的数量之和,
     * AC: 3ms/45.79MB
     * @param: machines
     * @return int
     * @author marks
     * @CreateDate: 2026/04/30 11:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int[] machines) {
        int n = machines.length;
        int sum = Arrays.stream(machines).sum();
        if (sum % n != 0) {
            return -1;
        }
        int target = sum / n; // 目标值
        // 更新 machines 数组
        int res = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            machines[i] -= target;
            res += machines[i];
            ans = Math.max(ans, Math.max(Math.abs(res), machines[i]));
        }

        return ans;
    }

}
