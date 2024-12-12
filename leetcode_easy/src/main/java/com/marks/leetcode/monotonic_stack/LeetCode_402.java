package com.marks.leetcode.monotonic_stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/12/6 15:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_402 {
    /**
     * @Description: [
     * 给你一个以字符串表示的非负整数 num 和一个整数 k ，移除这个数中的 k 位数字，使得剩下的数字最小。请你以字符串形式返回这个最小的数字。
     *
     * tips:
     * 1 <= k <= num.length <= 10^5
     * num 仅由若干位数字（0 - 9）组成
     * 除了 0 本身之外，num 不含任何前导零
     * ]
     * @param num 
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/12/6 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String removeKdigits(String num, int k) {
        String result;
        result = method_01(num, k);
        return result;
    }

    /**
     * @Description: [
     * E1:
     * 输入：num = "1432219", k = 3
     * 输出："1219"
     * 解释：移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219 。
     *
     * E2:
     * 输入：num = "10200", k = 1
     * 输出："200"
     * 解释：移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
     * stack.size <= 3
     * [1], [1, 4, 3], [4, 3, 2]
     * [1, 2, 2, 1]
     * [1, 1]
     * 123456789
     * 12345678919
     *
     * AC:14ms/45.43MB
     * ]
     * @param num 
     * @param k
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2024/12/6 15:22
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String num, int k) {
        int n = num.length();
        Deque<Character> stack = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            char currChar = num.charAt(i);
            while (!stack.isEmpty() && stack.peekLast() > currChar && k > 0) {
                stack.pollLast();
                k--;
            }
            stack.offerLast(currChar);
        }
        for (int i = 0; i < k; i++) {
            stack.pollLast();
        }
        StringBuilder ans = new StringBuilder();
        boolean leadingZero = true;
        while (!stack.isEmpty()) {
            Character at = stack.pollFirst();
            if (leadingZero && at == '0') {
                continue;
            }
            leadingZero = false;
            ans.append(at);
        }
        return ans.length() == 0 ? "0" : ans.toString();
    }
}
