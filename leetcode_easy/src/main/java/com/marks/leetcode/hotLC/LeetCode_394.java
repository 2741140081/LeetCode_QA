package com.marks.leetcode.hotLC;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_394 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/9 17:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_394 {

    /**
     * @Description:
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     *
     * 测试用例保证输出的长度不会超过 10^5。
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/12/09 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String decodeString(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1: s = "3[a2[c]]"
     * 1. 直接使用栈来解决, 栈中存放的类型是字符串类型,遇到'[' 时, 存入当前数字, 每当遇到']' 时, 出栈
     * 2. 出栈时'[' 后面跟着的必定是一个数字.
     * 3. 用String 写的太垃圾了, 直接用Character 写
     * AC: 2ms/42.34MB
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/12/09 17:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        // 构建一个栈, 优化的栈, 不要使用Stack
        Deque<Character> stack = new ArrayDeque<>();
        int n = s.length();
        char[] array = s.toCharArray();
        for (int i = 0; i < n; i++) {
            char curr = array[i];
            if (curr == '[') {
                stack.push(curr);
            } else if (curr == ']') {
                StringBuilder temp = new StringBuilder();
                while (!stack.isEmpty() && stack.peek() != '[') {
                    temp.append(stack.poll());
                }
                // 反转
                temp.reverse();
                stack.poll(); // 弹出'['
                StringBuilder count = new StringBuilder();
                while (!stack.isEmpty() && Character.isDigit(stack.peek())) {
                    count.append(stack.poll());
                }

                count.reverse();
                int repeat = Integer.parseInt(count.toString());
                for (int j = 0; j < repeat; j++) {
                    for (int k = 0; k < temp.length(); k++) {
                        stack.push(temp.charAt(k));
                    }
                }
            } else {
                stack.push(curr);
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            ans.append(stack.poll());
        }
        // 反转
        ans.reverse();
        return ans.toString();
    }

}
