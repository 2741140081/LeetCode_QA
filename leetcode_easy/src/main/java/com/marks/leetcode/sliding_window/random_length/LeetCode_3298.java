package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/30 11:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3298 {
    /**
     * @Description: [
     *
     * tips:
     * 1 <= word1.length <= 10^6
     * 1 <= word2.length <= 10^4
     * word1 和 word2 都只包含小写英文字母。
     * ]
     * @param word1
     * @param word2
     * @return long
     * @author marks
     * @CreateDate: 2024/10/30 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public long validSubstringCount(String word1, String word2) {
        long result = 0;
        result = method_01(word1, word2);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：word1 = "abcabc", word2 = "abc"
     *
     * 输出：10
     *
     * AC:378ms/54.92MB
     * ]
     * @param word1
     * @param word2
     * @return long
     * @author marks
     * @CreateDate: 2024/10/30 11:08
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private long method_01(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[] pre = new int[26]; // 存储word2中字符数量
        int[] cnt = new int[26]; // 存储word1中窗口中的字符数量
        char[] charArray = word1.toCharArray();
        long ans = 0;
        int left = n - 1;

        cnt[charArray[left] - 'a']++;

        for (int i = 0; i < m; i++) {
            pre[word2.charAt(i) - 'a']++;
        }
        for (int right = n - 1; right >= 0; right--) {

            while (!checkIsLegalSubString(cnt, pre) && left > 0) {
                left--;
                cnt[charArray[left] - 'a']++;
            }

            if (checkIsLegalSubString(cnt, pre)) {
                ans = ans + (left + 1);
            }

            cnt[charArray[right] - 'a']--;
        }

        return ans;
    }

    private boolean checkIsLegalSubString(int[] cnt, int[] pre) {
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] < pre[i]) {
                return false;
            }
        }
        return true;
    }
}
