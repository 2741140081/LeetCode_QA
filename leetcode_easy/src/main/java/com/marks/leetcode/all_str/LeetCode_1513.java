package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1513 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1513 {

    /**
     * @Description:
     * 给你一个二进制字符串 s（仅由 '0' 和 '1' 组成的字符串）。
     * 返回所有字符都为 1 的子字符串的数目。
     * 由于答案可能很大，请你将它对 10^9 + 7 取模后返回。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 14:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numSub(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 假设有一个长度为 x 的全1字符串, 那么这个字符串的子字符串有多少种可能? (1 + x) * x / 2
     * 2. 使用 long cnt 记录当前连续的1的个数
     * AC: 4ms/45.55MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 14:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        long ans = 0;
        int n = s.length();
        long cnt = 0;
        int MOD = 1000000007;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                cnt++;
            } else {
                ans = (ans + (cnt * (cnt + 1)) / 2) % MOD;
                cnt = 0;
            }
        }
        // 最后一个连续的1
        ans = (ans + (cnt * (cnt + 1)) / 2) % MOD;
        return (int) ans;
    }

}
