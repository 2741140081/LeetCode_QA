package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/20 17:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_678 {
    public boolean checkValidString(String s) {
        boolean result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * [[][][][[]]]
     * ][][
     *
     * 查看题解:
     * AC:0ms/40.72MB
     * @param s
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/20 17:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s) {
        int n = s.length();
        int balance = 0; // balance 用来表示当前的括号平衡度

        // 正向遍历
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i); // 获取字符串 s 的第 i 个字符
            if (ch == '(' || ch == '*') { // 如果当前字符是左括号，或者 '*'（即可被解锁）
                balance++; // 括号平衡度加一
            } else { // 当前字符是右括号
                balance--; // 括号平衡度减一，表示匹配了一个左括号
                if (balance < 0) {
                    return false; // 如果出现右括号却没有匹配的左括号，则返回 false
                }
            }
        }

        balance = 0; // 重新初始化括号平衡度

        // 反向遍历
        for (int i = n - 1; i >= 0; i--) {
            char ch = s.charAt(i); // 获取字符串 s 的第 i 个字符（从末尾开始）
            if (ch == ')' || ch == '*') { // 如果当前字符是右括号，或者 '*'（即可被解锁）
                balance++; // 括号平衡度加一
            } else { // 当前字符是左括号
                balance--; // 括号平衡度减一，表示匹配了一个右括号
                if (balance < 0) {
                    return false; // 如果出现左括号却没有匹配的右括号，则返回 false
                }
            }
        }
        return true; // 如果正向和反向遍历均未出现问题，则返回 true，表示可以构成有效的括号序列
    }
}
