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
 * @date 2025/1/20 10:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_921 {
    /**
     * @Description:
     * 只有满足下面几点之一，括号字符串才是有效的：
     *
     * 它是一个空字符串，或者
     * 它可以被写成 AB （A 与 B 连接）, 其中 A 和 B 都是有效字符串，或者
     * 它可以被写作 (A)，其中 A 是有效字符串。
     * 给定一个括号字符串 s ，在每一次操作中，你都可以在字符串的任何位置插入一个括号
     *
     * 例如，如果 s = "()))" ，你可以插入一个开始括号为 "(()))" 或结束括号为 "())))" 。
     * 返回 为使结果字符串 s 有效而必须添加的最少括号数。
     *
     * tips:
     * 1 <= s.length <= 1000
     * s 只包含 '(' 和 ')' 字符。
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minAddToMakeValid(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC:1ms/40.83MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 10:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty() && stack.peek() == '(' && ch == ')') {
                stack.poll();
            } else {
                stack.push(ch);
            }
        }
        return stack.size();
    }
}
