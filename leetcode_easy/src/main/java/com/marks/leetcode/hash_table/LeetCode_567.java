package com.marks.leetcode.hash_table;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_567 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 9:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_567 {

    /**
     * @Description:
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的某个变位词。
     * 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。
     * @param: s1
     * @param: s2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/01 9:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean checkInclusion(String s1, String s2) {
        boolean result;
        result = method_01(s1, s2);
        return result;
    }

    /**
     * @Description:
     * 1. 由于 s1 可以进行任意组合, 并且需要s1 是 s2 的子串, 那么就需要 s1.length() <= s2.length()
     * 2. 使用滑动窗口, 窗口大小为 s1.length(), 窗口移动一次, 计算窗口中各个字符出现的次数, 如果字符出现次数与s1相同, 则返回true
     * AC: 4ms/43.28MB
     * @param: s1
     * @param: s2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/01 9:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        if (m > n) {
            return false;
        }
        // 统计 s1 中每个字符出现的次数
        int[] countS1 = new int[26];
        for (int i = 0; i < m; i++) {
            countS1[s1.charAt(i) - 'a']++;
        }
        // 统计 s2 中 窗口大小为 m 的子串中每个字符出现的次数
        int[] count = new int[26];
        for (int i = 0; i < m; i++) {
            count[s2.charAt(i) - 'a']++;
        }
        boolean result = checkIsEqual(count, countS1);
        if (result) {
            return true;
        }
        for (int i = 0; i < n - m; i++) {
            int left = s2.charAt(i) - 'a';
            int right = s2.charAt(i + m) - 'a';
            count[left]--;
            count[right]++;
            result = checkIsEqual(count, countS1);
            if (result) {
                return true;
            }
        }

        return false;
    }

    private boolean checkIsEqual(int[] count, int[] countS1) {
        boolean result = true;
        for (int i = 0; i < 26; i++) {
            if (count[i] != countS1[i]) {
                result = false;
                break;
            }
        }

        return result;
    }

}
