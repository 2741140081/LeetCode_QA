package com.marks.leetcode.daily_question;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3756 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/8 10:12
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3756 {

    /**
     * @Description:
     * 给你一个长度为 m 的字符串 s，其中仅包含数字。另给你一个二维整数数组 queries，其中 queries[i] = [li, ri]。
     * 对于每个 queries[i]，提取 子串 s[li..ri]，然后执行以下操作：
     * 将子串中所有 非零数字 按照原始顺序连接起来，形成一个新的整数 x。如果没有非零数字，则 x = 0。
     * 令 sum 为 x 中所有数字的 数字和 。答案为 x * sum。
     * 返回一个整数数组 answer，其中 answer[i] 是第 i 个查询的答案。
     * 由于答案可能非常大，请返回其对 10^9 + 7 取余数的结果。
     * 子串 是字符串中的一个连续、非空 字符序列。
     *
     * tips:
     * 1 <= m == s.length <= 10^5
     * s 仅由数字组成。
     * 1 <= queries.length <= 10^5
     * queries[i] = [li, ri]
     * 0 <= li <= ri < m
     * @param: s
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/08 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[] sumAndMultiply(String s, int[][] queries) {
        int[] result;
        result = method_01(s, queries);
        return result;
    }

    /**
     * @Description:
     * 1. 查看提示后, 使用List 存储非零数字, 并且使用 map 记录 s 中下标 与 list 中下标的映射
     * 2. 由于模运算规则 (x * sum) % MOD = ((x % MOD) * (sum % MOD)) % MOD 即可以分别求 x 取余后的值与 sum 取余后的值相除
     * 3. 其中 sum 取余的值可以对 s 进行前缀和处理, O(1) 时间获取区间的 sum 值
     * 4. list 中前 i - 1 个数字组成 x, 对于 int num = list.get(i) 这个数字, 下一个 xNext = x * 10 + num;
     * 该如何求 [i, j] 组成的 x ? 并且需要在 O(1) 的时间复杂度得到.
     * 5. 先处理 List 和 map 的映射
     * 6. int l = queries[i][0], r = queries[i][1];  怎么判断 [l, r] 都是为 0的情况
     * 6.1 l == r && s.charAt(l) == '0', 此时返回 0
     * 6.2 l < r &&  x == y && s.charAt(l) == '0' 此时可以判断 [l, r] 全部为 0
     * 6.3 l < r &&  x < y
     * 7. 总和上述分析, 如果 s.charAt(l) == '0' 则 x++, 并且 如果 s.charAt(r) == '0' 则 y--;
     * if (x > y) return 0;
     * AC: 83ms/234.94MB
     * @param: s
     * @param: queries
     * @return int[]
     * @author marks
     * @CreateDate: 2026/07/08 10:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private final int MOD = 1000000007;
    private long[] prefix;
    private long[] pow10;

    private int[] method_01(String s, int[][] queries) {
        int n = s.length();
        int[] arr = new int[n];
        int m = queries.length;
        int[] ans = new int[m];
        Map<Integer, Integer> map = new HashMap<>();
        // 处理s中非0开始的下标
        int start = 0;
        while (start < n && s.charAt(start) == '0') {
            map.put(start, -1); // 映射到 0 位置上
            start++;
        }
        if (start == n) {
            return ans;
        }
        int idx = 0;
        for (int i = start; i < n; i++) {
            char c = s.charAt(i);
            if (c != '0') {
                arr[idx] = c - '0';
                map.put(i, idx);
                idx++;
            } else {
                // 将0映射到第一个非0元素上
                map.put(i, idx - 1);
            }
        }
        long[] prefixSum = new long[idx + 1];
        for (int i = 1; i <= idx; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i - 1];
        }
        // 通过 prefix[] prefix10 来得到区间[i, j] 的 x 值
        this.prefix = new long[idx + 1];
        this.pow10 = new long[idx + 1];
        pow10[0] = 1;
        for (int i = 1; i <= idx; i++) {
            prefix[i] = (prefix[i - 1] * 10 + arr[i - 1]) % MOD;
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        for (int i = 0; i < m; i++) {
            int l = queries[i][0], r = queries[i][1];
            // 没有考虑处理当区间[l, r] 如果区间元素都是0 的情况
            int x = map.get(l);
            int y = map.get(r);
            if (s.charAt(l) == '0') {
                x++;
            }

            if (x <= y) {
                long xx = getX(x, y);
                long sum = prefixSum[y + 1] - prefixSum[x];
                long result = ((xx % MOD) * (sum % MOD)) % MOD;
                ans[i] = (int) result;
            }
        }

        return ans;
    }

    private long getX(int l, int r) {
        int len = r - l + 1;
        long result = (prefix[r + 1] - prefix[l] * pow10[len] % MOD + MOD) % MOD;
        return result;
    }

}
