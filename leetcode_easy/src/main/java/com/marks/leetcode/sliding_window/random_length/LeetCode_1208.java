package com.marks.leetcode.sliding_window.random_length;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 10:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1208 {
    /**
     * @Description: [
     * 给你两个长度相同的字符串，s 和 t。
     * 将 s 中的第 i 个字符变到 t 中的第 i 个字符需要 |s[i] - t[i]| 的开销（开销可能为 0），也就是两个字符的 ASCII 码值的差的绝对值。
     * 用于变更字符串的最大预算是 maxCost。在转化字符串时，总开销应当小于等于该预算，这也意味着字符串的转化可能是不完全的。
     * 如果你可以将 s 的子字符串转化为它在 t 中对应的子字符串，则返回可以转化的最大长度。
     * 如果 s 中没有子字符串可以转化成 t 中对应的子字符串，则返回 0。
     *
     * tips:
     * 1 <= s.length, t.length <= 10^5
     * 0 <= maxCost <= 10^6
     * s 和 t 都只含小写英文字母。
     * ]
     * @param s
     * @param t
     * @param maxCost
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int equalSubstring(String s, String t, int maxCost) {
        int result = 0;
        result = method_01(s, t, maxCost);
        return result;
    }
    /**
     * @Description: [
     * 输入：s = "abcd", t = "bcdf", maxCost = 3
     * 输出：3
     * 解释：s 中的 "abc" 可以变为 "bcd"。开销为 3，所以最大长度为 3。
     *
     * AC:7ms/42.52MB
     * ]
     * @param s 
     * @param t 
     * @param maxCost 
     * @return int
     * @author marks
     * @CreateDate: 2024/10/14 10:49
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, String t, int maxCost) {
        int n = s.length();
        // pre[i] = |s.charAt(i) - t.charAt(i)|
        int[] pre = new int[n];
        int ans = 0;
        // left 为左边界
        int left = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int temp = Math.abs(s.charAt(i) - t.charAt(i));
            pre[i] = temp;
            sum += temp;
            while (sum > maxCost) {
                // 当超过maxCost时,需要将left向右移, 即left++
                sum = sum - pre[left];
                left++;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }
}
