package com.marks.leetcode.sliding_window.random_length;

import java.util.HashMap;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/25 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1234 {
    /**
     * @Description: [
     * 有一个只含有 'Q', 'W', 'E', 'R' 四种字符，且长度为 n 的字符串。
     *
     * 假如在该字符串中，这四个字符都恰好出现 n/4 次，那么它就是一个「平衡字符串」。
     * 给你一个这样的字符串 s，请通过「替换一个子串」的方式，使原字符串 s 变成一个「平衡字符串」。
     * 你可以用和「待替换子串」长度相同的 任何 其他字符串来完成替换。
     * 请返回待替换子串的最小可能长度。
     * 如果原字符串自身就是一个平衡字符串，则返回 0。
     * tips:
     * 1 <= s.length <= 10^5
     * s.length 是 4 的倍数
     * s 中只含有 'Q', 'W', 'E', 'R' 四种字符
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/25 15:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int balancedString(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }
    /**
     * @Description: [
     * int k = n / 4;
     * s[i, j] 是需要 待替换子串
     * 那么s[0, i - 1] 以及 s[j+1, n - 1]中剩余的各个字符数量均 <= k
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/10/25 15:58
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int k = n / 4; // 每个字符恰好k个
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            // 分别存储'Q','W','E','R'的数量
            cnt[s.charAt(i) - 'A']++;
        }
        int ans = n;
        if (check(cnt, k)) {
            return 0;
        }
        for (int l = 0, r = 0; l < n; l++) {
            while (r < n && !check(cnt, k)) {
                cnt[s.charAt(r) - 'A']--;
                r++;
            }
            if (!check(cnt, k)) {
                break;
            }
            ans = Math.min(ans, r - l);
            cnt[s.charAt(l) - 'A']++;
        }
        return ans;
    }

    private boolean check(int[] cnt, int k) {
        if (cnt['Q' - 'A'] > k || cnt['W' - 'A'] > k || cnt['E' - 'A'] > k || cnt['R' - 'A'] > k) {
            return false;
        }
        return true;
    }
}
