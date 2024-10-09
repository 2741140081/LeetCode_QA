package com.marks.leetcode.sliding_window.fixed_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/9 15:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1456 {
    /**
     * @Description: [
     * 给你字符串 s 和整数 k 。
     *
     * 请返回字符串 s 中长度为 k 的单个子字符串中可能包含的最大元音字母数。
     *
     * 英文中的 元音字母 为（a, e, i, o, u）。
     *
     * tips；
     * 1 <= s.length <= 10^5
     * s 由小写英文字母组成
     * 1 <= k <= s.length
     * ]
     * @param s 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 15:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxVowels(String s, int k) {
        int result = 0;
        result = method_01(s, k);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * s = "abciiidef", k = 3
     * AC:20ms/43.52MB
     * 优化, 将判断作为一个方法isVowel(char ch)
     * AC:13ms/43.52MB
     * ]
     * @param s 
     * @param k 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/9 15:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int k) {
        int n = s.length();
        int ans = 0;
        int vowel = 0; //中间变量,记录元音字母数量
        for (int i = 0; i < n; i++) {
            char inChar = s.charAt(i);
            if (isVowel(inChar)) {
                vowel++;
            }
            if (i - k + 1 < 0) {
                // 如果窗口长度小于k, 则继续增加窗口长度
                // i < k - 1, 即i的最大值i = k - 2, 当i = k - 1时, 此时窗口长度为k, 即[0, k - 1]
                continue;
            }
            ans = Math.max(ans, vowel);
            if (ans == k) {
                return ans;
            }
            // 如果长度超出窗口, 丢弃末尾的元素, 判断末尾元素是否是元音字母
            char outChar = s.charAt(i - k + 1);
            if (isVowel(outChar)) {
                vowel--;
            }
        }
        return ans;
    }

    private boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}
