package com.marks.leetcode.daily_question;

import java.util.TreeMap;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3734 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/28 15:41
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3734 {

    /**
     * @Description:
     * 给你两个长度均为 n 的字符串 s 和目标字符串 target，它们都由小写英文字母组成。
     * 返回 字典序 最小的字符串 ，该字符串 既 是 s 的一个 回文 排列 ，又是字典序 严格 大于 target 的。
     * 如果不存在这样的排列，则返回一个空字符串。
     * 如果字符串 a 和字符串 b 长度相同，在它们首次出现不同的位置上，
     * 字符串 a 处的字母在字母表中的顺序晚于字符串 b 处的对应字母，则字符串 a 在 字典序上严格大于 字符串 b。
     * 排列 是指对字符串中所有字符的重新排列。
     * 如果一个字符串从前向后读和从后向前读都一样，则该字符串是 回文 的。
     *
     * tips:
     * 1 <= n == s.length == target.length <= 300
     * s 和 target 仅由小写英文字母组成。
     * @param: s
     * @param: target
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/08/28 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String lexPalindromicPermutation(String s, String target) {
        String result;
        result = method_01(s, target);
        return result;
    }

    /**
     * @Description:
     * 1. 先判断 s 是否存在回文串, 即 最多只能有 0 个或者1 个的字符为奇数个数
     * 2. 需要一个有序集合存储可供添加的字符, 对于 t[i], 需要有序集合中存在字符大于t[i]
     * todo
     * @param: s
     * @param: target
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/08/28 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];
        for (char ch : s.toCharArray()) {
            cnt[ch - 'a']++;
        }
        // 判断 s 是否可以构成回文串
        int sum = 0;
        TreeMap<Character, Integer> treeMap = new TreeMap<>();
        int idx = -1;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] != 0) {
                treeMap.put((char) (i + 'a'), cnt[i] / 2);
            }
            if (cnt[i] % 2 != 0) {
                idx = i;
                sum++;
            }
        }
        if (sum > 1) {
            return "";
        }
        // 可以构建回文串
        boolean isBig = false;
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n / 2; i++) {
            char t = target.charAt(i);
            Character ch = treeMap.ceilingKey(t);
            if (!isBig) {
                if (ch != null) {
                    isBig = ch > t;
                    ans.append(ch);
                    treeMap.merge(ch, -1, Integer::sum);
                    if (treeMap.get(ch) == 0) {
                        treeMap.remove(ch);
                    }
                } else {

                }
            } else {

            }
        }

        return null;
    }

}
