package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/20 14:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1190 {
    public String reverseParentheses(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * s = "(ed(et(oc))el)"
     * 暴力破解 + 栈
     * AC:14ms/43.66MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/20 14:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ')') {
                if (!stack.isEmpty()) {
                    List<Character> list = new ArrayList<>();
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        list.add(stack.poll());
                    }
                    stack.poll();// 删除(
                    // 将反转后的字符压入栈中
                    for (int j = 0; j < list.size(); j++) {
                        stack.push(list.get(j));
                    }
                }
            } else {
                stack.push(ch);
            }
        }
        while (!stack.isEmpty()) {
            ans.append(stack.pollLast());
        }
        return ans.toString();
    }
}
