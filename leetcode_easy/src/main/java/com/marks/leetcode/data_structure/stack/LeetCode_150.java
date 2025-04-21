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
 * @date 2025/1/21 10:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_150 {
    public int evalRPN(String[] tokens) {
        int result;
        result = method_01(tokens);
        return result;
    }

    /**
     * @Description:
     * AC:5ms/43.75MB
     *
     * @param tokens
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 10:55
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        int num1, num2, result;
        for (String token : tokens) {
            switch (token) {
                case "+":
                    num1 = stack.poll();
                    num2 = stack.poll();
                    result = num2 + num1;
                    stack.push(result);
                    break;
                case "-":
                    num1 = stack.poll();
                    num2 = stack.poll();
                    result = num2 - num1;
                    stack.push(result);
                    break;
                case "*":
                    num1 = stack.poll();
                    num2 = stack.poll();
                    result = num2 * num1;
                    stack.push(result);
                    break;
                case "/":
                    num1 = stack.poll();
                    num2 = stack.poll();
                    result = num2 / num1;
                    stack.push(result);
                    break;
                default:
                    stack.push(Integer.parseInt(token));
                    break;
            }
        }
        int ans = stack.isEmpty() ? 0 : stack.peek();
        return ans;
    }
}
