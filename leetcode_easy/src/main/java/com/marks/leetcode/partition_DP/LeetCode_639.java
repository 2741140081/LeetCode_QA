package com.marks.leetcode.partition_DP;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 11:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_639 {
    private ArrayList<String> list = new ArrayList<>();
    private final int MOD = (int) 1e9 + 7;

    /**
     * @Description: [
     * 一条包含字母 A-Z 的消息通过以下的方式进行了 编码 ：
     *
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * 要 解码 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，"11106" 可以映射为：
     *
     * "AAJF" 对应分组 (1 1 10 6)
     * "KJF" 对应分组 (11 10 6)
     * 注意，像 (1 11 06) 这样的分组是无效的，因为 "06" 不可以映射为 'F' ，因为 "6" 与 "06" 不同。
     *
     * 除了 上面描述的数字字母映射方案，编码消息中可能包含 '*' 字符，可以表示从 '1' 到 '9' 的任一数字（不包括 '0'）。例如，编码字符串 "1*" 可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条消息。对 "1*" 进行解码，相当于解码该字符串可以表示的任何编码消息。
     *
     * 给你一个字符串 s ，由数字和 '*' 字符组成，返回 解码 该字符串的方法 数目 。
     *
     * 由于答案数目可能非常大，返回 10^9 + 7 的 模 。
     *
     * tips:
     * 1 <= s.length <= 10^5
     * s[i] 是 0 - 9 中的一位数字或字符 '*'
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 11:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numDecodings(String s) {
        int result = 0;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }
    /**
     * @Description: [官方题解:
     * AC:19ms/44.52MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int n = s.length();
        // a = f[i-2], b = f[i-1], c = f[i]
        long a = 0, b = 1, c = 0;
        for (int i = 1; i <= n; ++i) {
            c = b * check1digit(s.charAt(i - 1)) % MOD;
            if (i > 1) {
                c = (c + a * check2digits(s.charAt(i - 2), s.charAt(i - 1))) % MOD;
            }
            a = b;
            b = c;
        }
        return (int) c;
    }

    private int check1digit(char ch) {
        if (ch == '0') {
            return 0;
        }
        return ch == '*' ? 9 : 1;
    }

    private int check2digits(char c0, char c1) {
        if (c0 == '*' && c1 == '*') {
            return 15;
        }
        if (c0 == '*') {
            return c1 <= '6' ? 2 : 1;
        }
        if (c1 == '*') {
            if (c0 == '1') {
                return 9;
            }
            if (c0 == '2') {
                return 6;
            }
            return 0;
        }
        return (c0 != '0' && (c0 - '0') * 10 + (c1 - '0') <= 26) ? 1 : 0;
    }

    /**
     * @Description: [
     * 分成多种情况讨论
     * 1."**"
     * 2."1*", "2*", "3*"
     * 3."*1", "*2", "*3", "*4", "*5", "*6", "*7"
     * 4."12"
     * AC:405ms/52.49MB
     * 只能说暴力破解, 枚举所有的可能性, 待优化
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 14:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        for (int i = 1; i <= 26; i++) {
            list.add(i + "");
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            if (list.contains(s.substring(i - 1, i))) {
                dp[i] = (dp[i] + dp[i - 1]) % MOD;
            }else if (s.charAt(i - 1) == '*'){
                dp[i] = (dp[i - 1] * 9) % MOD;
            }

            if ( i - 2 >=0 && list.contains(s.substring(i - 2, i))) {
                dp[i] = (dp[i] + dp[i - 2]) % MOD;
            } else if (i - 2 >=0 && s.charAt(i - 1) == '*' && s.charAt(i - 2) == '*') {
                // 15 = (c0=1,c1=9) 9 + (c0=2,c1=6) 6
                dp[i] = (dp[i] + dp[i - 2] * 15) % MOD;
            } else if (i - 2 >=0 && s.charAt(i - 1) == '*' && s.charAt(i - 2) == '1') {
                dp[i] = (dp[i] + dp[i - 2] * 9) % MOD;
            } else if (i - 2 >=0 && s.charAt(i - 1) == '*' && s.charAt(i - 2) == '2') {
                dp[i] = (dp[i] + dp[i - 2] * 6) % MOD;
            } else if (i - 2 >= 0 && s.charAt(i - 2) == '*') {
                if (s.charAt(i - 1) <= '6') {
                    dp[i] = (dp[i] + dp[i - 2] * 2) % MOD;
                }else {
                    dp[i] = (dp[i] + dp[i - 2]) % MOD;
                }
            }
        }
        return (int) dp[n];
    }
}
