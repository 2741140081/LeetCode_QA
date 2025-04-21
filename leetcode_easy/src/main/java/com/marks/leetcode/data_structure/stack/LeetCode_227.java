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
 * @date 2025/1/21 16:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_227 {
    /**
     * @Description:
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * 整数除法仅保留整数部分。
     * 你可以假设给定的表达式总是有效的。所有中间结果将在 [-2^31, 2^31 - 1] 的范围内。
     * 注意：不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
     *
     * tips:
     * 1 <= s.length <= 3 * 10^5
     * s 由整数和算符 ('+', '-', '*', '/') 组成，中间由一些空格隔开
     * s 表示一个 有效表达式
     * 表达式中的所有整数都是非负整数，且在范围 [0, 2^31 - 1] 内
     * 题目数据保证答案是一个 32-bit 整数
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 16:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int calculate(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * "3+2*2"
     * AC:34ms/58.17MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 16:35
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        s = s.replaceAll(" ", "");
        int n = s.length();
        Deque<String> stack = new ArrayDeque<>();
        int num1, result, num2;
        int i = 0;
        while (i < n) {
            char ch = s.charAt(i);
            switch (ch) {
                case '+':
                case '-':
                    stack.push(String.valueOf(ch));
                    i++;
                    break;
                case '*':
                    num1 = Integer.parseInt(stack.poll());
                    num2 = 0;
                    i++;
                    while (i < n && Character.isDigit(s.charAt(i))) {
                        num2 = num2 * 10 + s.charAt(i) - '0';
                        i++;
                    }
                    result = num1 * num2;
                    stack.push(String.valueOf(result));
                    break;
                case '/':
                    num1 = Integer.parseInt(stack.poll());
                    num2 = 0;
                    i++;
                    while (i < n && Character.isDigit(s.charAt(i))) {
                        num2 = num2 * 10 + s.charAt(i) - '0';
                        i++;
                    }
                    result = num1 / num2;
                    stack.push(String.valueOf(result));
                    break;
                default:
                    num2 = 0;
                    while (i < n && Character.isDigit(s.charAt(i))) {
                        num2 = num2 * 10 + s.charAt(i) - '0';
                        i++;
                    }
                    stack.push(String.valueOf(num2));
                    break;
            }
        }
        int ans = !stack.isEmpty() ? Integer.parseInt(stack.pollLast()) : 0;
        while (!stack.isEmpty()) {
            String op = stack.pollLast();
            if ("+".equals(op)) {
                num1 = Integer.parseInt(stack.pollLast()); // op == "+"
                ans += num1;
            } else {
                num1 = Integer.parseInt(stack.pollLast()); // op == "-"
                ans -= num1;
            }
        }
        return ans;
    }
}
