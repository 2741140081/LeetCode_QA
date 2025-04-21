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
 * @date 2025/1/20 10:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1021 {
    /**
     * @Description:
     * 有效括号字符串为空 ""、"(" + A + ")" 或 A + B ，其中 A 和 B 都是有效的括号字符串，+ 代表字符串的连接。
     *
     * 例如，""，"()"，"(())()" 和 "(()(()))" 都是有效的括号字符串。
     * 如果有效字符串 s 非空，且不存在将其拆分为 s = A + B 的方法，我们称其为原语（primitive），其中 A 和 B 都是非空有效括号字符串。
     *
     * 给出一个非空有效字符串 s，考虑将其进行原语化分解，使得：s = P_1 + P_2 + ... + P_k，其中 P_i 是有效括号字符串原语。
     *
     * 对 s 进行原语化分解，删除分解中每个原语字符串的最外层括号，返回 s 。
     * @param s 
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/20 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String removeOuterParentheses(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC:12ms/41.13MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/20 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty()) {
                ans.append(ch);
            }
            if (!stack.isEmpty() && stack.peek() == '(' && ch == ')') {
                stack.poll();
                if (stack.isEmpty()) {
                    ans.deleteCharAt(ans.length() - 1);
                }
            }else  {
                stack.push(ch);
            }
        }
        return ans.toString();
    }
}
