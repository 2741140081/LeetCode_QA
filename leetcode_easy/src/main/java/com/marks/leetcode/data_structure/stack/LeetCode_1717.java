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
 * @date 2025/1/20 9:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1717 {
    /**
     * @Description:
     * 给你一个字符串 s 和两个整数 x 和 y 。你可以执行下面两种操作任意次。
     *
     * 删除子字符串 "ab" 并得到 x 分。
     * 比方说，从 "cabxbae" 删除 ab ，得到 "cxbae" 。
     * 删除子字符串"ba" 并得到 y 分。
     * 比方说，从 "cabxbae" 删除 ba ，得到 "cabxe" 。
     * 请返回对 s 字符串执行上面操作若干次能得到的最大得分。
     * @param s
     * @param x
     * @param y
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 9:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maximumGain(String s, int x, int y) {
        int result;
        result = method_01(s, x, y);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：s = "cdbcbbaaabab", x = 4, y = 5
     * 输出：19
     * bbaa = 10
     * aabb = 8
     * baba = 9 ~ 10
     * abab = 8 ~ 9
     *
     * E2:
     * s = "aabbaaxybbaabb", x = 5, y = 4
     *
     * AC:81ms/44.93MB
     * @param s
     * @param x
     * @param y
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 9:41
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s, int x, int y) {
        int ans = 0;
        if (x >= y) {
            int num_x = getMaxGainX(s, x, y);
            ans = num_x;
        } else {
            int num_y = getMaxGainY(s, y, x);
            ans = num_y;
        }
        return ans;
    }

    /**
     * @Description:
     * 优先删除 ba, 然后删除 ab
     * @param s
     * @param y
     * @return int
     * @author marks
     * @CreateDate: 2025/1/20 10:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int getMaxGainY(String s, int y, int x) {
        Deque<Character> stack = new ArrayDeque<>();
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty() && stack.peek() == 'b' && ch == 'a') {
                res += y;
                stack.poll();
            } else {
                stack.push(ch);
            }
        }
        Deque<Character> pre_stack = new ArrayDeque<>();
        while (!stack.isEmpty()) {
            char after = stack.pollLast();
            if (!pre_stack.isEmpty() && pre_stack.peek() == 'a' && after == 'b') {
                res += x;
                pre_stack.poll();
            } else {
                pre_stack.push(after);
            }
        }
        return res;

    }

    private int getMaxGainX(String s, int x, int y) {
        Deque<Character> stack = new ArrayDeque<>();
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty() && stack.peek() == 'a' && ch == 'b') {
                res += x;
                stack.poll();
            } else {
                stack.push(ch);
            }
        }
        Deque<Character> pre_stack = new ArrayDeque<>();
        while (!stack.isEmpty()) {
            char after = stack.pollLast();
            if (!pre_stack.isEmpty() && pre_stack.peek() == 'b' && after == 'a') {
                res += y;
                pre_stack.poll();
            } else {
                pre_stack.push(after);
            }
        }
        return res;
    }
}
