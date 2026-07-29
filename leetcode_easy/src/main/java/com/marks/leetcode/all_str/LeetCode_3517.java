package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3517 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/28 10:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3517 {

    /**
     * @Description:
     * 给你一个 回文 字符串 s。
     * 返回 s 的按字典序排列的 最小 回文排列。
     * 如果一个字符串从前往后和从后往前读都相同，那么这个字符串是一个 回文 字符串。
     * 排列 是字符串中所有字符的重排。
     * 如果字符串 a 按字典序小于字符串 b，则表示在第一个不同的位置，a 中的字符比 b 中的对应字符在字母表中更靠前。
     * 如果在前 min(a.length, b.length) 个字符中没有区别，则较短的字符串按字典序更小。
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/07/28 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String smallestPalindrome(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. s 是回文字符串, 并且需要返回一个字典序最小的回文字符串, 统计 char[] cnt,
     * 然后从小到大构建最小字典序的回文串
     * 2. 只需要将奇数个数的一个字符放置在中间位置
     * AC: 26ms/47.32MB
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/07/28 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        int n = s.length();
        char[] minStr = new char[n];
        int[] cnt = new int[26];
        for (int i = 0; i < n; i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        int mid = n / 2; // 中间位置用来放置单个奇数字符
        int left = 0, right = n - 1;
        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2 == 1) {
                minStr[mid] = (char) (i + 'a');
                cnt[i]--;
            }
            for (int j = 0; j < cnt[i]; j += 2) {
                minStr[left++] = (char) (i + 'a');
                minStr[right--] = (char) (i + 'a');
            }
        }

        return new String(minStr);
    }

}
