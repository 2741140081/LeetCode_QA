package com.marks.leetcode.data_structure.stack;

import java.util.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/21 17:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_726 {
    /**
     * @Description:
     * 给你一个字符串化学式 formula ，返回 每种原子的数量 。
     * 原子总是以一个大写字母开始，接着跟随 0 个或任意个小写字母，表示原子的名字。
     * 如果数量大于 1，原子后会跟着数字表示原子的数量。如果数量等于 1 则不会跟数字。
     * 例如，"H2O" 和 "H2O2" 是可行的，但 "H1O2" 这个表达是不可行的。
     * 两个化学式连在一起可以构成新的化学式。
     * 例如 "H2O2He3Mg4" 也是化学式。
     * 由括号括起的化学式并佐以数字（可选择性添加）也是化学式。
     * 例如 "(H2O2)" 和 "(H2O2)3" 是化学式。
     * 返回所有原子的数量，格式为：第一个（按字典序）原子的名字，跟着它的数量（如果数量大于 1），然后是第二个原子的名字（按字典序），跟着它的数量（如果数量大于 1），以此类推。
     *
     * @param formula 
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/21 17:17
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String countOfAtoms(String formula) {
        String result;
        result = method_01(formula);
        return result;
    }

    /**
     * @Description:
     * 存在三种情况
     * 1. "H2O", 直接就是大写字母
     * 2. "(H20)2", 直接是左括号 "(" / ")"
     *
     * AC:18ms/42.15MB
     * @param formula
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2025/1/22 9:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String formula) {
        int n = formula.length();
        Deque<String> stack = new ArrayDeque<>();
        int i = 0;
        while (i < n) {
            char ch = formula.charAt(i);
            if (Character.isUpperCase(ch)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ch);
                i++;
                while (i < n && Character.isLowerCase(formula.charAt(i))) {
                    sb.append(formula.charAt(i));
                    i++;
                }
                int num = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    num = num * 10 + formula.charAt(i) - '0';
                    i++;
                }
                num = (num == 0 ? 1 : num);
                sb.append("_" + num);
                stack.push(sb.toString());
            } else if (ch == ')') {
                // 取右括号后的数字, 如果不是数字, 则设置数字为1
                i++;
                int num = 0;
                while (i < n && Character.isDigit(formula.charAt(i))) {
                    num = num * 10 + formula.charAt(i) - '0';
                    i++;
                }
                num = (num == 0 ? 1 : num);

                // 将(表达式), 将括号中表达式的原子数量 * num
                Map<String, Integer> temp = new HashMap<>();
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    String[] curr_atom = stack.poll().split("_");
                    temp.merge(curr_atom[0], Integer.parseInt(curr_atom[1]), Integer::sum);
                }
                stack.poll(); // 删除栈中的"("
                for (String key : temp.keySet()) {
                    stack.push(key + "_" + (temp.get(key) * num));
                }

            } else if (ch == '('){
                // '('
                stack.push(String.valueOf(ch));
                i++;
            }
        }
        Map<String, Integer> map = new HashMap<>();
        while (!stack.isEmpty()) {
            String[] s = stack.poll().split("_");
            map.merge(s[0], Integer.parseInt(s[1]), Integer::sum);
        }
        StringBuilder ans = new StringBuilder();
        ArrayList<String> list = new ArrayList<>(map.keySet());
        Collections.sort(list);
        for (String key : list) {
            ans.append(key);
            int value = map.get(key);
            if (value > 1) {
                ans.append(value);
            }
        }
        return ans.toString();
    }
}
