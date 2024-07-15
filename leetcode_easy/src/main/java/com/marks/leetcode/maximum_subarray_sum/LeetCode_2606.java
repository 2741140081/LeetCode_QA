package com.marks.leetcode.maximum_subarray_sum;

public class LeetCode_2606 {
    /**
     * 给你一个字符串 s ，一个字符 互不相同 的字符串 chars 和一个长度与 chars 相同的整数数组 vals 。
     * 子字符串的开销 是一个子字符串中所有字符对应价值之和。空字符串的开销是 0 。
     * 字符的价值 定义如下：
     * 如果字符不在字符串 chars 中，那么它的价值是它在字母表中的位置（下标从 1 开始）。
     * 比方说，'a' 的价值为 1 ，'b' 的价值为 2 ，以此类推，'z' 的价值为 26 。
     * 否则，如果这个字符在 chars 中的位置为 i ，那么它的价值就是 vals[i] 。
     * 请你返回字符串 s 的所有子字符串中的最大开销。
     *
     * E1:
     * 输入：s = "adaa", chars = "d", vals = [-1000]
     * 输出：2
     * 解释：字符 "a" 和 "d" 的价值分别为 1 和 -1000 。
     * 最大开销子字符串是 "aa" ，它的开销为 1 + 1 = 2 。
     * 2 是最大开销。
     *
     * E2:
     * 输入：s = "abc", chars = "abc", vals = [-1,-1,-1]
     * 输出：0
     * 解释：字符 "a" ，"b" 和 "c" 的价值分别为 -1 ，-1 和 -1 。
     * 最大开销子字符串是 "" ，它的开销为 0 。
     * 0 是最大开销。
     * @param s
     * @param chars
     * @param vals
     * @return
     */
    public int maximumCostSubstring(String s, String chars, int[] vals) {
        int result = 0;
        result = method_01(s, chars, vals);
        int result_02 = method_02(s, chars, vals);
        return result;
    }

    private int method_01(String s, String chars, int[] vals) {
        int[] value = new int[s.length()];
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            int index = chars.indexOf(charArray[i]);
            if (index == -1) {
                // 不存在与chars中, a-z 的ASCII码 97-122
                // 依据要求 a 的ASCII 97 , 它的值是97 - 96 = 1;
                int asciiCode = charArray[i];
                value[i] = asciiCode - 96;
            }else {
                // 存在与chars中, 于dp[i] 存放值
                value[i] = vals[index];
            }
        }
        // 初始化为0
        int[] dp = new int[value.length];
        dp[0] = Math.max(value[0], 0);
        int sumMax = Math.max(value[0], 0);
        for (int i = 1; i < value.length; i++) {
            dp[i] = Math.max(dp[i-1] + value[i], value[i]);
            sumMax = Math.max(dp[i], sumMax);
        }

        return sumMax;
    }


    /**
     * method 02 优化时间和空间:
     * 不需要计算s.toCharArray后的每一个值存放的values (int[])中, 只需要计算26个, 当计算时去数组中获取值
     * @param s
     * @param chars
     * @param vals
     * @return
     */
    private int method_02(String s, String chars, int[] vals) {
        int n = s.length();
        int[] map = new int[26];
        for (int i = 0; i < 26; ++i)
            map[i] = i + 1;
        for (int i = 0; i < vals.length; i++)
            map[chars.charAt(i) - 'a'] = vals[i];

        int[] dp = new int[n+1];
        dp[0] = 0;
        int max = 0;
        for(int i = 1;i<=n;i++){
            char c = s.charAt(i-1);
            dp[i] = Math.max(dp[i-1]+map[c-'a'],map[c-'a']);
            max = Math.max(max,dp[i]);
        }
        return max;
    }


}
