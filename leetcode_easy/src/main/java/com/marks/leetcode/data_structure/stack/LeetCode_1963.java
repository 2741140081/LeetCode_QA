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
 * @date 2025/1/20 16:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1963 {
    /**
     * @Description: [功能描述]
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minSwaps(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * [[[]]]][[]
     * ]]]][[[[
     * []]][[[]
     * ]][[
     * [][] => 4 : 1
     * ]]][[[ => 6: 2
     * []][[] =>
     * ]]]]][[[[[ => 10 => 3
     * ]]]][[[[ => 8: 2
     * ]]][[[
     * AC: 47ms/54.84MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 16:57
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == ']' && !stack.isEmpty() && stack.peek() == '[') {
                stack.poll();
            } else {
                stack.push(ch);
            }
        }
        ans = stack.size() / 4 + (stack.size() % 4 == 0 ? 0 : 1);
        return ans;
    }
}
