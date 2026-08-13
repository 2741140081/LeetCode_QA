package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3922 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3922 {

    /**
     * @Description:
     * 给你一个二进制字符串 s。
     * 如果一个字符串 不 包含 "011" 或 "110" 作为 子序列，则认为该字符串是 连贯的 。
     * 在一次操作中，你可以 翻转  s 中的任意字符（'0' 变为 '1'，或 '1' 变为 '0'）。
     * 返回一个整数，表示使 s 连贯所需的 最少 操作次数。
     * tips:
     * 1 <= s.length <= 10^5
     * s[i] 是 '0' 或 '1'。
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minFlips(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 分情况讨论, 假设开始两个是 01, 00, 10 11 这4种情况, 分别计算
     * 2. 讨论结尾是 01, 00, 10, 11
     * 3. 如果开始是 0(包含00/01) 格式, 后续需要将 oneCnt 数量减少到 1 才能满足条件.
     * 4. 如果开始是1, 10 格式, 这种只能处理 n = 3 的情况, 即 101 满足. n > 3 时无法使用.
     * 5. 格式时 11 格式, 需要后续没有0出现, 即 zeroCnt 数量减少到 0 才能满足条件.
     * AC: 9ms/46.63MB
     * @param: s
     * @return int
     * @author marks
     * @CreateDate: 2026/06/24 16:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        if (n < 3) {
            return 0;
        }
        int oneCnt = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                oneCnt++;
            }
        }
        int zeroCnt = n - oneCnt;
        if (s.charAt(0) == '1' && s.charAt(n - 1) == '1') {
            // 可以多一个1
            oneCnt--;
        }
        oneCnt--; // 允许保留一个1

        return Math.min(Math.max(oneCnt, 0), zeroCnt);
    }


}
