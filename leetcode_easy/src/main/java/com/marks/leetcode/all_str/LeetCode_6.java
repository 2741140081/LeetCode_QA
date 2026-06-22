package com.marks.leetcode.all_str;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_6 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/17 14:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_6 {

    /**
     * @Description:
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
     * 请你实现这个将字符串进行指定行数变换的函数：
     * string convert(string s, int numRows);
     * @param: s
     * @param: numRows
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/17 14:25
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String convert(String s, int numRows) {
        String result;
        result = method_01(s, numRows);
        result = method_02(s, numRows);
        return result;
    }

    /**
     * @Description:
     * 1. 优化, 只用一个 flag 进行标记即可
     * @param: s
     * @param: numRows
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/17 15:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_02(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }
        int idx = 0;
        int flag = -1;
        for (char c : s.toCharArray()) {
            rows.get(idx).append(c);
            if (idx == numRows - 1 || idx == 0) {
                flag = -flag;
            }
            idx += flag;
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }

        return result.toString();
    }

    /**
     * @Description:
     * 1. 通过模拟的方式, 将字符串进行Z字形排列
     * 2. 没有必要进行真实的模拟操作, 浪费空间, 可以通过计算得到 i 所在的行数.
     * 3. Z 字形可以看作是 x 个 v构成, 每个 V 包含 2 * numRows - 2 个字符
     * 4. 算了还是直接模拟
     * AC: 7ms/46.63MB
     * @param: s
     * @param: numRows
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/17 14:28
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }
        boolean canMove = false;
        int idx = 0;
        // 栈
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (canMove) {
                int pop = stack.pop();
                rows.get(pop).append(ch);
                if (stack.isEmpty()) {
                    canMove = false;
                    stack.push(0);
                    idx = 1;
                }
            } else {
                rows.get(idx).append(ch);
                if (idx < numRows - 1) {
                    stack.push(idx);
                    idx++;
                } else if (idx == numRows - 1) {
                    canMove = true;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
        return result.toString();
    }

}
