package com.marks.leetcode.array_hard;

import java.util.Arrays;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2551 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/20 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2551 {

    /**
     * @Description:
     * 你有 k 个背包。给你一个下标从 0 开始的整数数组 weights ，其中 weights[i] 是第 i 个珠子的重量。同时给你整数 k 。
     * 请你按照如下规则将所有的珠子放进 k 个背包。
     * 没有背包是空的。
     * 如果第 i 个珠子和第 j 个珠子在同一个背包里，那么下标在 i 到 j 之间的所有珠子都必须在这同一个背包中。
     * 如果一个背包有下标从 i 到 j 的所有珠子，那么这个背包的价格是 weights[i] + weights[j] 。
     * 一个珠子分配方案的 分数 是所有 k 个背包的价格之和。
     * 请你返回所有分配方案中，最大分数 与 最小分数 的 差值 为多少。
     *
     * tips:
     * 1 <= k <= weights.length <= 10^5
     * 1 <= weights[i] <= 10^9
     * @param: weights
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/08/20 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long putMarbles(int[] weights, int k) {
        long result;
        result = method_01(weights, k);
        return result;
    }

    /**
     * @Description:
     * 1. 可以选择数的数量是 [k + 1 ~ Math.min(2k, n)]
     * 2. 由于首尾必定会参与计算, 先除去首尾, k - 1 和 maxCnt = Math.min(2k - 2, n - 2)
     * 3. 即当仅存在一个数时, 例如{1}, 此时分数是 1 + 1 = 2
     * AC: 37ms/74.84MB
     * @param: weights
     * @param: k
     * @return long
     * @author marks
     * @CreateDate: 2026/08/20 15:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] weights, int k) {
        int n = weights.length;
        for (int i = 0; i < n - 1; i++) {
            weights[i] += weights[i + 1];
        }
        Arrays.sort(weights, 0, n - 1); // 去掉最后一个数

        long ans = 0;
        for (int i = 0; i < k - 1; i++) {
            ans += weights[n - 2 - i] - weights[i];
        }
        return ans;
    }
}
