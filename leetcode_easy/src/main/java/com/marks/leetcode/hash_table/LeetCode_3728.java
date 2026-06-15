package com.marks.leetcode.hash_table;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3728 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3728 {

    /**
     * @Description:
     * 给你一个整数数组 capacity。
     * 当满足以下条件时，子数组 capacity[l..r] 被视为 稳定 数组：
     * 其长度 至少 为 3。
     * 首 元素与 尾 元素都等于它们之间所有元素的 和（即 capacity[l] = capacity[r] = capacity[l + 1] + capacity[l + 2] + ... + capacity[r - 1]）。
     * 返回一个整数，表示 稳定子数组 的数量。
     * 子数组 是数组中的连续且非空的元素序列。
     * tips:
     * 3 <= capacity.length <= 10^5
     * -10^9 <= capacity[i] <= 10^9
     * @param: capacity
     * @return long
     * @author marks
     * @CreateDate: 2026/06/15 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long countStableSubarrays(int[] capacity) {
        long result;
        result = method_01(capacity);
        return result;
    }

    /**
     * @Description:
     * 1. calculate the sum of the array
     * 2. 真的需要记录每一个 capacity[i] 的下标的集合吗? 如果记录集合, 那么时间复杂度是 O(n^2), 所以不可取
     * 3. 有一个前缀和数组 prefixSum, 有两个点 i, j, c[i] = c[j], c[i] = c[i + 1] + ... + c[j - 1],
     * 转换为 p[j - 1] - p[i] = c[i], p[j - 1] = c[i] + p[i]
     * 查看题解, 后续需要自行理解思考
     * AC: 163ms/123.38MB
     * @param: capacity
     * @return long
     * @author marks
     * @CreateDate: 2026/06/15 10:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(int[] capacity) {
        int n = capacity.length;
        long prefixSum = 0;
        long ans = 0;
        Map<Pair, Integer> map = new HashMap<>();
        for (int i = 1; i < n; i++) {
            ans += map.getOrDefault(new Pair(capacity[i], prefixSum), 0);
            map.merge(new Pair(capacity[i - 1], capacity[i - 1] + prefixSum), 1, Integer::sum);
            prefixSum += capacity[i];
        }
        return ans;
    }

    // record 类型会自动为你生成 equals()、hashCode()、toString() 方法，且基于所有组件字段进行语义比较‌，无需手动重写。
    private record Pair(int num, long sum) {
    }

}
