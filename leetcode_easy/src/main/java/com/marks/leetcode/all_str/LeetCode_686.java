package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_686 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/18 16:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_686 {

    /**
     * @Description:
     * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
     * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
     * tips:
     * 1 <= a.length <= 10^4
     * 1 <= b.length <= 10^4
     * a 和 b 由小写英文字母组成
     * @param: a
     * @param: b
     * @return int
     * @author marks
     * @CreateDate: 2026/06/18 16:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int repeatedStringMatch(String a, String b) {
        int result;
        result = method_01(a, b);
        return result;
    }

    /**
     * @Description:
     * 1. 判断 b 中是否包含 x 个 a, 并且 去除 x 个 a 后剩余的 b 是 a + a 的子串
     * 2. 判断 b 是否是 a + a 的子串
     * 3. 判断 b 的起始 [0, i] 是否是 a 的子串, 如果不是, 返回 -1
     * 查看官方题解, 方法一: KMP
     * @param: a
     * @param: b
     * @return int
     * @author marks
     * @CreateDate: 2026/06/18 16:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String a, String b) {
        int an = a.length(), bn = b.length();
        int index = strStr(a, b);
        if (index == -1) {
            return -1;
        }
        if (an - index >= bn) {
            return 1;
        }
        return (bn + index - an - 1) / an + 2;
    }

    private int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) {
            return 0;
        }
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) {
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1];
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        for (int i = 0, j = 0; i - j < n; i++) { // b 开始匹配的位置是否超过第一个叠加的 a
            while (j > 0 && haystack.charAt(i % n) != needle.charAt(j)) { // haystack 是循环叠加的字符串，所以取 i % n
                j = pi[j - 1];
            }
            if (haystack.charAt(i % n) == needle.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

}
