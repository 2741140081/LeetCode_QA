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
 * @date 2025/1/17 10:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2696 {
    /**
     * @Description:
     * 给你一个仅由 大写 英文字符组成的字符串 s 。
     * 你可以对此字符串执行一些操作，在每一步操作中，你可以从 s 中删除 任一个 "AB" 或 "CD" 子字符串。
     * 通过执行操作，删除所有 "AB" 和 "CD" 子串，返回可获得的最终字符串的 最小 可能长度。
     * 注意，删除子串后，重新连接出的字符串可能会产生新的 "AB" 或 "CD" 子串。
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/17 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minLength(String s) {
        int result;
        result = method_01(s);
        result = method_02(s);
        return result;
    }

    /**
     * @Description:
     * AC:3ms/42.46MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/17 11:03
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(String s) {
        List<Character> stack = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            stack.add(s.charAt(i));
            int n = stack.size();
            if (n >= 2 && (stack.get(n - 2) == 'A' && stack.get(n - 1) == 'B' ||
                    stack.get(n - 2) == 'C' && stack.get(n - 1) == 'D')) {
                stack.remove(n - 1);
                stack.remove(n - 2);
            }
        }
        return stack.size();
    }

    /**
     * @Description:
     * AC:39ms/44.68MB
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/1/17 10:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        while (s.contains("AB") || s.contains("CD")) {
            String s0 = s.replaceAll("AB", "");
            String s1 = s0.replaceAll("CD", "");
            s = s1;
        }
        return s.length();
    }
}
