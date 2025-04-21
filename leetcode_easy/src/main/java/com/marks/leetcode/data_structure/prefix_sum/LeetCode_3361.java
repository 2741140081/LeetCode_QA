package com.marks.leetcode.data_structure.prefix_sum;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/13 10:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3361 {
    /**
     * @Description:
     * 给你两个长度相同的字符串 s 和 t ，以及两个整数数组 nextCost 和 previousCost 。
     *
     * 一次操作中，你可以选择 s 中的一个下标 i ，执行以下操作 之一 ：
     *
     * 将 s[i] 切换为字母表中的下一个字母，如果 s[i] == 'z' ，切换后得到 'a' 。操作的代价为 nextCost[j] ，其中 j 表示 s[i] 在字母表中的下标。
     * 将 s[i] 切换为字母表中的上一个字母，如果 s[i] == 'a' ，切换后得到 'z' 。操作的代价为 previousCost[j] ，其中 j 是 s[i] 在字母表中的下标。
     * 切换距离 指的是将字符串 s 变为字符串 t 的 最少 操作代价总和。
     *
     * 请你返回从 s 到 t 的 切换距离 。
     * tips:
     * 1 <= s.length == t.length <= 10^5
     * s 和 t 都只包含小写英文字母。
     * nextCost.length == previousCost.length == 26
     * 0 <= nextCost[i], previousCost[i] <= 10^9
     * @param s 
     * @param t 
     * @param nextCost 
     * @param previousCost 
     * @return long
     * @author marks
     * @CreateDate: 2025/1/13 10:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long shiftDistance(String s, String t, int[] nextCost, int[] previousCost) {
        long result;
        result = method_01(s, t, nextCost, previousCost);
        return result;
    }
    
    /**
     * @Description:
     *
     * 输入：s = "leet", t = "code",
     * nextCost = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],
     * previousCost = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]
     * 输出：31
     *
     * [1, 1, 1, 2, 1]
     * [0, 1, 2, 3, 5, 6]
     *
     * AC:14ms/44.80MB
     * @param s 
     * @param t 
     * @param nextCost 
     * @param previousCost 
     * @return long
     * @author marks
     * @CreateDate: 2025/1/13 10:56
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(String s, String t, int[] nextCost, int[] previousCost) {
        long[] nextSumCost = new long[27];
        long[] previousSumCost = new long[27];

        for (int i = 1; i <= 26; i++) {
            nextSumCost[i] = nextSumCost[i - 1] + nextCost[i - 1]; // a -> a
        }
        for (int i = 24; i >= 0; i--) {
            previousSumCost[i] = previousCost[i + 1] + previousSumCost[i + 1]; // z -> a
        }
        previousSumCost[26] = previousCost[0];
        long ans = 0;
        for (int i = 0; i < s.length(); i++) {
            int start = s.charAt(i) - 'a';
            int end = t.charAt(i) - 'a';
            if (start > end) {
                long nextValue = nextSumCost[26] - nextSumCost[start] + nextSumCost[end];
                long preValue = previousSumCost[end] - previousSumCost[start];
                ans += Math.min(nextValue, preValue);
            } else if (start < end) {
                long nextValue = nextSumCost[end] - nextSumCost[start];
                long preValue = previousSumCost[0] - previousSumCost[start] + previousSumCost[26] + previousSumCost[end];
                ans += Math.min(nextValue, preValue);
            } else {
                continue;
            }
        }
        return ans;
    }
}
