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
 * @date 2025/1/17 11:08
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1047 {
    /**
     * @Description:
     * 给出由小写字母组成的字符串 s，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
     * 在 s 上反复执行重复项删除操作，直到无法继续删除。
     * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/17 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String removeDuplicates(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC:27ms/44.59MB
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/17 11:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty() && stack.peek() == ch) {
                stack.poll();
            } else {
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
