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
 * @date 2025/1/21 14:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_224 {
    /**
     * @Description:
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     *
     * 注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
     *
     * tips:
     * 1 <= s.length <= 3 * 10^5
     * s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
     * s 表示一个有效的表达式
     * '+' 不能用作一元运算(例如， "+1" 和 "+(2 + 3)" 无效)
     * '-' 可以用作一元运算(即 "-1" 和 "-(2 + 3)" 是有效的)
     * 输入中不存在两个连续的操作符
     * 每个数字和运行的计算将适合于一个有符号的 32位 整数
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 14:50
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int calculate(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * s = "(1+(4+5+2)-3)+(6+8)"
     * "1-(-2)"
     * 1-(0+-2)
     * AC:8ms/42.84MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/21 15:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(1);
        int sign = 1;
        int n = s.length();
        int i = 0;
        int ans = 0;
        while (i < n) {
            char ch = s.charAt(i);
            switch (ch) {
                case ' ':
                    i++;
                    break;
                case '(':
                    stack.push(sign);
                    i++;
                    break;
                case '+':
                    sign = stack.peek();
                    i++;
                    break;
                case '-':
                    sign = -stack.peek();
                    i++;
                    break;
                case ')':
                    stack.poll();
                    i++;
                default:
                    long num = 0;
                    while (i < n && Character.isDigit(s.charAt(i))) {
                        num = num * 10 + s.charAt(i) - '0';
                        i++;
                    }
                    ans += sign * num;
                    break;
            }
        }

        return ans;
    }
}
