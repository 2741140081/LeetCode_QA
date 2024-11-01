package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 16:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_125 {
    /**
     * @Description: [
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     *
     * 字母和数字都属于字母数字字符。
     *
     * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
     *
     * tips:
     * 1 <= s.length <= 2 * 10^5
     * s 仅由可打印的 ASCII 字符组成
     * ]
     * @param s
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/1 16:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isPalindrome(String s) {
        boolean result = false;
        result = method_01(s);
        return result;
    }
    
    /**
     * @Description: [
     * 输入: s = "A man, a plan, a canal: Panama"
     * 输出：true
     * 解释："amanaplanacanalpanama" 是回文串。
     *
     * AC:4ms/41.98MB
     * ]
     * @param s
     * @return boolean
     * @author marks
     * @CreateDate: 2024/11/1 16:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s) {
        int n = s.length();
        int left = 0;
        int right = n - 1;
        while (left < right) {
            while ( left < right && !Character.isLetterOrDigit(s.charAt(left)) ) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right)) ) {
                right--;
            }
            if (left < right) {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }
}
