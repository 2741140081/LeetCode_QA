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
 * @date 2025/1/20 14:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1614 {
    /**
     * @Description:
     * 给定 有效括号字符串 s，返回 s 的 嵌套深度。嵌套深度是嵌套括号的 最大 数量。
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 14:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDepth(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * AC:1ms/40.75MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == ')') {
                if (!stack.isEmpty() && ch == ')') {
                    stack.poll();
                } else {
                    stack.push(ch);
                    ans = Math.max(ans, stack.size());
                }
            }
        }
        return ans;
    }
}
