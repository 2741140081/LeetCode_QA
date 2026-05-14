package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1081 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/13 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1081 {

    /**
     * @Description:
     * 返回 s 字典序最小的子序列，该子序列包含 s 的所有不同字符，且只包含一次。
     *
     * tips:
     * 1 <= s.length <= 1000
     * s 由小写英文字母组成
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/05/13 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String smallestSubsequence(String s) {
        String result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 没有很直接的思路, 那么就先处理 字符串, 将每一个字符最后一次出现的位置, 这个位置用于在栈进行出栈操作时进行判断
     * 2. 由于每个字符只能在最后结果种出现一次, 所以对栈中元素需要使用 visited 数组进行判断
     * AC: 3ms/42.11MB
     * @param: s
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/05/13 15:00
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s) {
        int[] lastIndex = new int[26];
        Arrays.fill(lastIndex, -1);
        int n = s.length();
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i) - 'a';
            lastIndex[index] = i;
        }
        // 创建单调递增栈
        Deque<Character> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[26];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (visited[c - 'a']) { // 栈中已经存在该字符, 跳过
                continue;
            }
            while (!stack.isEmpty() && stack.peek() > c && lastIndex[stack.peek() - 'a'] > i) {
                visited[stack.peek() - 'a'] = false;
                stack.pop();
            }
            stack.push(c);
            visited[c - 'a'] = true;
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.reverse().toString();
    }

}
