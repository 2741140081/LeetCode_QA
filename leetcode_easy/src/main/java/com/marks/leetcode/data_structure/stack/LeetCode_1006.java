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
 * @date 2025/1/21 11:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1006 {
    private String[] token = {"-", "*", "/", "+"};
    public int clumsy(int n) {
        int result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1
     * 11 + 7 - x1 + 3 - x2
     * AC:25ms/43.72MB
     * @param n
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 11:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(int n) {
        Deque<String> stack = new ArrayDeque<>();
        stack.push(String.valueOf(n));
        int num2, result;
        for (int i = n - 1; i > 0; i--) {
            int index = (n - i) % 4;
            switch (token[index]) {
                case "+":
                    stack.push("+");
                    stack.push(String.valueOf(i));
                    break;
                case "-":
                    stack.push("-");
                    stack.push(String.valueOf(i));
                    break;
                case "*":
                    num2 = Integer.parseInt(stack.poll());
                    result = num2 * i;
                    stack.push(String.valueOf(result));
                    break;
                case "/":
                    num2 = Integer.parseInt(stack.poll());
                    result = num2 / i;
                    stack.push(String.valueOf(result));
                    break;
                default:
                    break;
            }
        }
        int ans = !stack.isEmpty() ? Integer.parseInt(stack.pollLast()) : 0;
        while (!stack.isEmpty()) {
            String op = stack.pollLast();
            if (op == "+") {
                num2 = Integer.parseInt(stack.pollLast()); // op == "+"
                ans += num2;
            } else {
                num2 = Integer.parseInt(stack.pollLast()); // op == "-"
                ans -= num2;
            }
        }
        return ans;
    }
}
