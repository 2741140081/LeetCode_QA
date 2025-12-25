package com.marks.leetcode.monotonic_stack;

import java.util.LinkedList;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_316 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/25 10:00
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_316 {

    /**
     * @Description:
     * 给你一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。
     * 需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/12/25 10:01
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String removeDuplicateLetters(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 如果要让结果的字典序最小, 那么这个栈就是一个单调递增的栈(根据字典序递增), 并且栈中不能存在重复的字符
     * 2. 但是我怎么判断栈中是否包含全部字符? 需要boolean数组 visited 判断栈中是否包含当前字符
     * 3. 什么情况下会发送出栈? 栈顶 char ch >  当前 char curr, 如果要弹出栈顶元素, 需要判断后续的字符串中该元素的剩余个数是否为0
     * 如果剩余个数为0, 那么栈顶元素不能弹出, 否则栈顶元素可以弹出
     * AC: 5ms/43.21MB
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/12/25 10:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        // 将string 转换成 char 数组
        char[] sCharArray = s.toCharArray();
        // visited 数组
        boolean[] visited = new boolean[26]; // 保存栈中字符
        // 需要一个后缀数组, 存储每个字符的剩余个数
        int[] suffix = new int[26];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            suffix[sCharArray[i] - 'a']++;
        }
        // 创建一个单调栈, 使用LinkedList
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            char curr = sCharArray[i];
            if (!visited[curr - 'a']) {
                // 栈中还没有当前元素
                while (!stack.isEmpty() && stack.peek() > curr) {
                    // 栈顶元素 > 当前元素
                    char ch = stack.peek();
                    if (suffix[ch - 'a'] == 0) {
                        // 后续没有该元素了, 不能弹出栈顶元素
                        break;
                    } else {
                        // 可以弹出栈顶元素
                        stack.pop();
                        visited[ch - 'a'] = false; // 更新visited 数组
                    }
                }
                // 添加当前元素到栈中
                stack.push(curr);
                visited[curr - 'a'] = true; // 更新visited 数组
            }
            suffix[curr - 'a']--; // 减去当前元素剩余个数
        }
        // 将栈中的元素转换成string
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString(); // 反转
    }

}
