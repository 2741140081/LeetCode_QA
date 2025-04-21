package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/20 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1249 {
    public String minRemoveToMakeValid(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     *
     * AC:23ms/45.05MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/20 16:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        int n = s.length();
        int[] right = new int[n + 1];
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder();

        for (int i = 1; i <= n; i++) {
            right[i] = right[i - 1] + (s.charAt(i - 1) == ')' ? 1 : 0);
        }

        int count = 0;
        for (int i = 1; i <= n; i++) {
            char ch = s.charAt(i - 1);
            if (ch == '(') {
                if (right[n] - right[i] > count) {
                    stack.push(ch);
                    count++;
                }
            } else if (ch == ')') {
                if (count > 0) {
                    stack.push(ch);
                    count--;
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
