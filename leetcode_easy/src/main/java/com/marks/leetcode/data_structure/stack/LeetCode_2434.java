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
     * 给你一个字符串 s 和一个机器人，机器人当前有一个空字符串 t 。执行以下操作之一，直到 s 和 t 都变成空字符串：
     *
     * 删除字符串 s 的 第一个 字符，并将该字符给机器人。机器人把这个字符添加到 t 的尾部。
     * 删除字符串 t 的 最后一个 字符，并将该字符给机器人。机器人将该字符写到纸上。
     * 请你返回纸上能写出的字典序最小的字符串。
     *
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
     * E1:
     * 输入：s = "bdda"
     *
     * E2:
     * s = "bac"
     *
     * AC:91ms/54.50MB
     *
     * @param s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/15 17:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        int n = s.length();
        char[] array = s.toCharArray();
        int[] pre = new int[26];
        for (int i = 0; i < n; i++) {
            pre[array[i] - 'a']++;
        }
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder ans = new StringBuilder();
        int index = 0;
        for (int i = 0; i < n; i++) {
            char ch = array[i];
            pre[ch - 'a']--;
            while (index < 25 && pre[index] == 0) {
                index++;
            }
            stack.push(ch);
            while (!stack.isEmpty() && stack.peek() - 'a' <= index) {
                ans.append(stack.poll());
            }
        }
        return ans.toString();
    }
}
