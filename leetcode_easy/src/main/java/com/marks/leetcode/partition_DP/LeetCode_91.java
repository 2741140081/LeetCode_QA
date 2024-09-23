package com.marks.leetcode.partition_DP;

import java.util.ArrayList;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/23 10:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_91 {
    private ArrayList<String> list = new ArrayList<>();
    /**
     * @Description: [
     * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
     * "1" -> 'A',"2" -> 'B', ..., "25" -> 'Y', "26" -> 'Z'
     *
     * 然而，在 解码 已编码的消息时，你意识到有许多不同的方式来解码，因为有些编码被包含在其它编码当中（"2" 和 "5" 与 "25"）。
     * 例如，"11106" 可以映射为：
     * "AAJF" ，将消息分组为 (1, 1, 10, 6)
     * "KJF" ，将消息分组为 (11, 10, 6)
     * 消息不能分组为  (1, 11, 06) ，因为 "06" 不是一个合法编码（只有 "6" 是合法的）。
     * 注意，可能存在无法解码的字符串。
     * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。如果没有合法的方式解码整个字符串，返回 0。
     *
     * 题目数据保证答案肯定是一个 32 位 的整数。
     * tips:
     * 1 <= s.length <= 100
     * s 只包含数字，并且可能包含前导零。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 10:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numDecodings(String s) {
        int result = 0;
//        result = method_01(s);
        result = method_02(s);
        return result;
    }

    /**
     * @Description: [
     * 官方题解:
     * 和我之前关于定义最后一部分类似,
     * 即dp[i - 1] 和dp[i - 2], 因为只需要这两个部分, 其余部分是无效的, method_01中的for j (i - 1 ~ 0), 包含有效部分仅仅只是i - 1, i -2;
     * AC:3ms/41.61MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 11:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        int n = s.length();
        for (int i = 1; i <= 26; i++) {
            list.add(i + "");
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            if (list.contains(s.substring(i - 1, i))) {
                dp[i] += dp[i - 1];
            }
            if ( i - 2 >=0 && list.contains(s.substring(i - 2, i))) {
                dp[i] += dp[i - 2];
            }

        }
        return dp[n];
    }

    /**
     * @Description: [
     * E1:s = "226";
     * ans = 3;
     * dp[1] = 1;
     * AC:36ms/44.18MB
     *
     * for循环中的if 判断添加一个条件进行剪枝 “s.substring(j, i).length() <= 2”
     * AC:8ms/44.21MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/9/23 10:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        for (int i = 1; i <= 26; i++) {
            list.add(i + "");
        }
        int[] dp = new int[n + 1];
        dp[0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (s.substring(j, i).length() <= 2 && list.contains(s.substring(j, i))) {
                    dp[i] += dp[j];
                }
            }
        }
        return dp[n];
    }
}
