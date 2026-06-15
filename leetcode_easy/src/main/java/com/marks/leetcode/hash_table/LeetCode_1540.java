package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1540 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 10:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1540 {

    /**
     * @Description:
     * 给你两个字符串 s 和 t ，你的目标是在 k 次操作以内把字符串 s 转变成 t 。
     * 在第 i 次操作时（1 <= i <= k），你可以选择进行如下操作：
     * 选择字符串 s 中满足 1 <= j <= s.length 且之前未被选过的任意下标 j （下标从 1 开始），并将此位置的字符切换 i 次。
     * 不进行任何操作。
     * 切换 1 个字符的意思是用字母表中该字母的下一个字母替换它（字母表环状接起来，所以 'z' 切换后会变成 'a'）。第 i 次操作意味着该字符应切换 i 次
     * 请记住任意一个下标 j 最多只能被操作 1 次。
     * 如果在不超过 k 次操作内可以把字符串 s 转变成 t ，那么请你返回 true ，否则请你返回 false 。
     *
     * tips:
     * 1 <= s.length, t.length <= 10^5
     * 0 <= k <= 10^9
     * s 和 t 只包含小写英文字母。
     * @param: s
     * @param: t
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/15 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean canConvertString(String s, String t, int k) {
        boolean result;
        result = method_01(s, t, k);
        return result;
    }

    /**
     * @Description:
     * 1. 统计不同的字符, 并且得到每个不同字符需要进行的切换次数, 切换次数为 x + 26 * y, 但是只需要记录余数即可, 即
     * 使用 map 存储 Map<Integer, Integer> map: key: 为 s 中字符切换到 t 所需的最小切换次数, value: 频次数量
     * 2. 由于频次数量不会超过 26, 所以只需要用数组来存储即可 int[] cnt = new int[26];
     * AC: 6ms/47.05MB
     * @param: s
     * @param: t
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/15 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, String t, int k) {
        int n = s.length();
        if (n != t.length()) {
            return false;
        }
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            int x = (t.charAt(i) + 26 - s.charAt(i)) % 26;
            cnt[x]++;
        }
        for (int i = 1; i <= 25; i++) {
            if (cnt[i] > 0) {
                int max = i + (cnt[i] - 1) * 26;
                if (max > k) {
                    return false;
                }
            }
        }

        return true;
    }

}
