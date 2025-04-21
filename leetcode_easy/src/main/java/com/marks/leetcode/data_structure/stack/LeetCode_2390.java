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
 * @date 2025/1/15 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2390 {
    /**
     * @Description:
     * 给你一个包含若干星号 * 的字符串 s 。
     * 在一步操作中，你可以：
     * 选中 s 中的一个星号。
     * 移除星号 左侧 最近的那个 非星号 字符，并移除该星号自身。
     * 返回移除 所有 星号之后的字符串。
     * 注意：
     * 生成的输入保证总是可以执行题面中描述的操作。
     * 可以证明结果字符串是唯一的。
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String removeStars(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 37ms/44.94MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 10:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char curr_ch = s.charAt(i);
            if (curr_ch == '*') {
                stack.poll();
            } else {
                stack.push(curr_ch);
            }
        }
        StringBuilder ans = new StringBuilder();
        StringBuffer test = new StringBuffer();
        while (!stack.isEmpty()) {
            ans.append(stack.poll());
        }
        return ans.reverse().toString();
    }
}
