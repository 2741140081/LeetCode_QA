package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1328 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 9:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1328 {

    /**
     * @Description:
     * 给你一个由小写英文字母组成的回文字符串 palindrome ，
     * 请你将其中 一个 字符用任意小写英文字母替换，使得结果字符串的 字典序最小 ，且 不是 回文串。
     * 请你返回结果字符串。如果无法做到，则返回一个 空串 。
     * 如果两个字符串长度相同，那么字符串 a 字典序比字符串 b 小可以这样定义：在 a 和 b 出现不同的第一个位置上，字符串 a 中的字符严格小于 b 中的对应字符。
     * 例如，"abcc” 字典序比 "abcd" 小，因为不同的第一个位置是在第四个字符，显然 'c' 比 'd' 小。
     *
     * tips:
     * 1 <= palindrome.length <= 1000
     * palindrome 只包含小写英文字母。
     * @param: palindrome
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/24 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String breakPalindrome(String palindrome) {
        String result;
        result = method_01(palindrome);
        return result;
    }

    /**
     * @Description:
     * 1. 如果字符串 p 全由 'a' 组成, 则无法修改, 使得 p 更小, 所以返回空字串
     * 2. 针对单个字符, 必定是回文串, 所以单个字符同样无法修改, 返回空字串
     * 3. 针对上述情况, 采用模拟的方式来处理, 并且使用双指针.
     * AC: 0ms/42.34MB
     * @param: palindrome
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/24 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String palindrome) {
        int n = palindrome.length();
        if (n == 1) {
            return "";
        }
        int left = 0, right = n - 1;
        String ans = "";
        int max = -1;
        // 将字符串转为 char 数组
        char[] chars = palindrome.toCharArray();
        while (left < right) {
            if (palindrome.charAt(left) != 'a') {
                // 将 最小left 处字符变成 'a'
                chars[left] = 'a';
                return String.valueOf(chars);
            } else {
                // 将最远的 right 处的字符变成后一个字符, 例如 'a' -> 'b'
                max = Math.max(max, right);
            }
            left++;
            right--;
        }
        //
        if (max != -1) {
            // 找到最远 right 处的字符, 将它变成字典序中下一个字符
            chars[max] += 1;
            return String.valueOf(chars);
        }

        return "";
    }

}
