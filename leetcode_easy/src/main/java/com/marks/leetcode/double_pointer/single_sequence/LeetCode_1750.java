package com.marks.leetcode.double_pointer.single_sequence;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/11/1 17:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1750 {
    /**
     * @Description: [
     * 给你一个只包含字符 'a'，'b' 和 'c' 的字符串 s ，你可以执行下面这个操作（5 个步骤）任意次：
     *
     * 选择字符串 s 一个 非空 的前缀，这个前缀的所有字符都相同。
     * 选择字符串 s 一个 非空 的后缀，这个后缀的所有字符都相同。
     * 前缀和后缀在字符串中任意位置都不能有交集。
     * 前缀和后缀包含的所有字符都要相同。
     * 同时删除前缀和后缀。
     * 请你返回对字符串 s 执行上面操作任意次以后（可能 0 次），能得到的 最短长度 。
     *
     * tips:
     * 1 <= s.length <= 10^5
     * s 只包含字符 'a'，'b' 和 'c' 。
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 17:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minimumLength(String s) {
        int result = 0;
        result = method_01(s);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：s = "aabccabba"
     * 输出：3
     *
     * E2:
     * 输入：s = "cabaabac"
     * 输出：0
     *
     * AC:3ms/44.03MB
     * ]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2024/11/1 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int left = 0;
        int right = n - 1;

        while (left < right && s.charAt(left) == s.charAt(right)) {
            char ch = s.charAt(left);
            while (left <= right && s.charAt(left) == ch) {
                left++;
            }
            while (left <= right && s.charAt(right) == ch) {
                right--;
            }
        }

        return right - left + 1;
    }
}
