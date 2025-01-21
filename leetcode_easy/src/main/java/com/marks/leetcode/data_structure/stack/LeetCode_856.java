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
 * @date 2025/1/20 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_856 {
    /**
     * @Description: [功能描述]
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 15:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int scoreOfParentheses(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： "(()(()))"
     * 输出： 6
     * (1(1))
     * (1 + 2)
     * (3) = 6
     * AC:1ms/40.65MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 15:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ')' && !stack.isEmpty()) {
                if (stack.peek() > 0) {
                    int value = 0;
                    while (!stack.isEmpty() && stack.peek() > 0) {
                        value += stack.poll();
                    }
                    stack.poll(); // 取出( , 相当于取出0
                    stack.push(value * 2);
                } else {
                    stack.poll();
                    stack.push(1);
                }

            } else {
                stack.push(0);
            }
        }
        int ans = 0;
        while (!stack.isEmpty()) {
            ans += stack.poll();
        }
        return ans;
    }
}
