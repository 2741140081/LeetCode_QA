package com.marks.leetcode.data_structure.prefix_sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 10:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2438 {
    private final int MOD = (int) 1e9 + 7;
    /**
     * @Description:
     * 给你一个正整数 n ，你需要找到一个下标从 0 开始的数组 powers ，它包含 最少 数目的 2 的幂，且它们的和为 n 。
     * powers 数组是 非递减 顺序的。根据前面描述，构造 powers 数组的方法是唯一的。
     *
     * 同时给你一个下标从 0 开始的二维整数数组 queries ，其中 queries[i] = [lefti, righti] ，
     * 其中 queries[i] 表示请你求出满足 lefti <= j <= righti 的所有 powers[j] 的乘积。
     *
     * 请你返回一个数组 answers ，长度与 queries 的长度相同，其中 answers[i]是第 i 个查询的答案。由于查询的结果可能非常大，请你将每个 answers[i] 都对 10^9 + 7 取余 。
     * tips:
     * 1 <= n <= 10^9
     * 1 <= queries.length <= 10^5
     * 0 <= start_i <= end_i < powers.length
     * @param n 
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/13 10:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] productQueries(int n, int[][] queries) {
        int[] result;
        result = method_01(n, queries);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：n = 15, queries = [[0,1],[2,2],[0,3]]
     * 输出：[2,4,64]
     * 解释：
     * 对于 n = 15 ，得到 powers = [1,2,4,8] 。没法得到元素数目更少的数组。
     * 第 1 个查询的答案：powers[0] * powers[1] = 1 * 2 = 2 。
     * 第 2 个查询的答案：powers[2] = 4 。
     * 第 3 个查询的答案：powers[0] * powers[1] * powers[2] * powers[3] = 1 * 2 * 4 * 8 = 64 。
     * 每个答案对 10^9 + 7 得到的结果都相同，所以返回 [2,4,64] 。
     * 15 % 8 = 7 add(8)
     * 7 % 4 = 3 add(4)
     * 3 % 2 = 1 add(2)
     * 1 % 1 = 0 add(1)
     * AC:27ms/104.16MB
     * @param n
     * @param queries
     * @return int[]
     * @author marks
     * @CreateDate: 2025/1/13 10:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[] method_01(int n, int[][] queries) {
        List<Integer> list = new ArrayList<>();
        int num = n;
        for (int i = 29; i >= 0 && num > 0; i--) {
            int max_value = (int) Math.pow(2, i);
            if (max_value <= num) {
                list.add(max_value);
                num = num % max_value;
            }
        }
        Collections.sort(list);
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int right = queries[i][1];
            long sum = 1;
            for (int left = queries[i][0]; left <= right; left++) {
                sum = (sum * (long)list.get(left)) % MOD;
            }
            ans[i] = (int) sum;
        }
        return ans;
    }
}
