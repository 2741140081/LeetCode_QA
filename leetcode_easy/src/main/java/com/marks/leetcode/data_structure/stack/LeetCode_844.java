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
 * @date 2025/1/15 9:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_844 {
    /**
     * @Description:
     * 给定 s 和 t 两个字符串，当它们分别被输入到空白的文本编辑器后，如果两者相等，返回 true 。# 代表退格字符。
     *
     * 注意：如果对空文本输入退格字符，文本继续为空。
     *
     * @param s 
     * @param t
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/15 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean backspaceCompare(String s, String t) {
        boolean result;
        result = method_01(s, t);
        return result;
    }

    /**
     * @Description:
     * AC:1ms/40.89MB
     * @param s
     * @param t
     * @return boolean
     * @author marks
     * @CreateDate: 2025/1/15 9:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, String t) {
        Deque<Character> stack_s = new ArrayDeque<>();
        Deque<Character> stack_t = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch_s = s.charAt(i);
            if (ch_s != '#') {
                stack_s.push(ch_s);
            } else {
                stack_s.poll();
            }

        }
        for (int i = 0; i < t.length(); i++) {
            char ch_t = t.charAt(i);
            if (ch_t != '#') {
                stack_t.push(ch_t);
            } else {
                stack_t.poll();
            }
        }
        if (stack_s.size() != stack_t.size()) {
            return false;
        } else {
            while (!stack_s.isEmpty()) {
                char ch_s = stack_s.poll();
                char ch_t = stack_t.poll();
                if (ch_s != ch_t) {
                    return false;
                }
            }
        }
        return true;
    }
}
