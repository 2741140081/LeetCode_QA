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
 * @date 2025/1/15 17:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2434 {
    /**
     * @Description:
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String robotWithString(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 输入：s = "bdda"
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        int n = s.length();
        char[] array = s.toCharArray();
        int[] pre = new int[n];
        for (int i = 0; i < n; i++) {
            pre[array[i] - 'a']++;
        }
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n; i++) {
            char ch = array[i];
            pre[ch - 'a']--;
            if (stack.isEmpty()) {
                stack.push(ch);
                continue;
            } else {
                while (!stack.isEmpty() && ch > stack.peek()) {
                    char curr_ch = stack.poll();
                }
            }

        }
        return "";
    }
}
