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
 * @date 2025/1/17 11:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1544 {
    public String makeGood(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 一个简易方法判断 'A' - 'a' = -32, 'a' - 'A' = 32, 所以Math.abs('A' - 'a') = 32
     * AC:3ms/41.45MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/17 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty() && Math.abs(stack.peek() - ch) == 32) {
                stack.poll();
            }else {
                stack.push(ch);
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            ans.append(stack.pollLast());
        }
        return ans.toString();
    }
}
