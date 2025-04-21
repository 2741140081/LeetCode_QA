package com.marks.leetcode.data_structure.stack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/17 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1209 {
    /**
     * @Description:
     * @param s
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/17 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String removeDuplicates(String s, int k) {
        String result;
        result = method_01(s, k);
        result = method_02(s, k);
        return result;
    }

    /**
     * @Description:
     * 查看官方题解:
     * AC:21ms/45.79MB
     *
     * @param s
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/17 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(String s, int k) {
        Deque<Pair> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            if (stack.isEmpty() || stack.peek().ch != s.charAt(i)) {
                stack.push(new Pair(s.charAt(i), 1));
            } else {
                stack.peek().count++;
                if (stack.peek().count == k) {
                    stack.poll();
                }
            }
        }
        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            Pair pair = stack.poll();
            for (int i = 0; i < pair.count; i++) {
                ans.append(pair.ch);
            }
        }
        return ans.reverse().toString();
    }

    class Pair {
        private Character ch;
        private int count;

        public Pair(Character ch, int count) {
            this.ch = ch;
            this.count = count;
        }
    }

    /**
     * @Description:
     * TLE: 20/21
     * @param s
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/17 15:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, int k) {
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int size = list.size();
            if (size >= k - 1 && check(list, ch, k)) {
                remove(list, size, k);
            } else {
                list.add(ch);
            }
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ans.append(list.get(i));
        }
        return ans.toString();
    }

    private void remove(List<Character> list, int n, int k) {
        for (int i = 1; i < k; i++) {
            list.remove(n - i);
        }
    }

    private boolean check(List<Character> list, char ch, int k) {
        int i = 1;
        int n = list.size();
        while (i < k) {
            if (list.get(n - i) == ch) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }
}
