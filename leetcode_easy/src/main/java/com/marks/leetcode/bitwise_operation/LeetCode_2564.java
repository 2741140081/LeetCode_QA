package com.marks.leetcode.bitwise_operation;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/9/19 17:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2564 {
    private static final int[] NOT_FOUND = new int[]{-1, -1};

    
    /**
     * @Description:
     * 给你一个 二进制字符串 s 和一个整数数组 queries ，其中 queries[i] = [firsti, secondi] 。
     * 对于第 i 个查询，找到 s 的 最短子字符串 ，它对应的 十进制值 val 与 firsti 按位异或 得到 secondi ，换言之，val ^ firsti == secondi 。
     *
     * 第 i 个查询的答案是子字符串 [lefti, righti] 的两个端点（下标从 0 开始），如果不存在这样的子字符串，则答案为 [-1, -1] 。
     * 如果有多个答案，请你选择 lefti 最小的一个。
     *
     * 请你返回一个数组 ans ，其中 ans[i] = [lefti, righti] 是第 i 个查询的答案。
     *
     * 子字符串 是一个字符串中一段连续非空的字符序列。
     * tips:
     * 1 <= s.length <= 10^4
     * s[i] 要么是 '0' ，要么是 '1' 。
     * 1 <= queries.length <= 10^5
     * 0 <= firsti, secondi <= 10^9
     * @param s 
     * @param queries
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/9/19 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int[][] substringXorQueries(String s, int[][] queries) {
        int[][] result;
        result = method_01(s, queries);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：s = "101101", queries = [[0,5],[1,2]]
     * 输出：[[0,2],[2,3]]
     * 解释：第一个查询，端点为 [0,2] 的子字符串为 "101" ，对应十进制数字 5 ，且 5 ^ 0 = 5 ，所以第一个查询的答案为 [0,2]。
     * 第二个查询中，端点为 [2,3] 的子字符串为 "11" ，对应十进制数字 3 ，且 3 ^ 1 = 2 。所以第二个查询的答案为 [2,3] 。
     * 1. int t = queries[i][0] ^ queries[i][1]; String strT = Integer.toBinaryString(t);
     * 2. left = s.indexOf(strT), right = left + strT.length() - 1;
     * 3. 这个时间复杂度好像超了！
     * @param s 
     * @param queries 
     * @return int[][]
     * @author marks
     * @CreateDate: 2025/9/19 17:13
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int[][] method_01(String s, int[][] queries) {
        Map<Integer, int[]> m = new HashMap<>();
        int i = s.indexOf('0');
        if (i >= 0) {
            m.put(0, new int[]{i, i}); // 这样下面就可以直接跳过 '0' 了，效率更高
        }

        char[] chars = s.toCharArray();
        int n = chars.length;
        for (int l = 0; l < n; l++) {
            if (chars[l] == '0') {
                continue;
            }
            for (int r = l, x = 0; r < Math.min(l + 30, n); r++) {
                x = (x << 1) | (chars[r] & 1);
                m.putIfAbsent(x, new int[]{l, r});
            }
        }

        int[][] ans = new int[queries.length][];
        for (i = 0; i < queries.length; i++) {
            ans[i] = m.getOrDefault(queries[i][0] ^ queries[i][1], NOT_FOUND);
        }
        return ans;
    }

}
