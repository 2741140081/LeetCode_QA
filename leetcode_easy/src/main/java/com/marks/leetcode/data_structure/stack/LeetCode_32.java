package com.marks.leetcode.data_structure.stack;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/21 10:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_32 {
    public int longestValidParentheses(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * s = ")()())"
     * i = 0, j = -1, b = -1 => b = 0, j = 0
     * i = 1, j = 0, b = 1
     * i = 2, b = 0 => i - j = 2
     * i = 3, b = 1
     * i = 4, b = 0 => i - j = 4
     * i = 5, b = -1 => b = 0, j = 5
     *
     * AC:1ms/41.28MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int left = 0, right = 0, maxlength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * right);
            } else if (right > left) {
                left = right = 0;
            }
        }
        left = right = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                right++;
            }
            if (left == right) {
                maxlength = Math.max(maxlength, 2 * left);
            } else if (left > right) {
                left = right = 0;
            }
        }
        return maxlength;
    }
}
