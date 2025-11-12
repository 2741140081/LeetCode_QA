package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_241 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/11 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_241 {
    static final int ADDITION = -1;
    static final int SUBTRACTION = -2;
    static final int MULTIPLICATION = -3;

    /**
     * @Description:
     * 给你一个由数字和运算符组成的字符串 expression ，按不同优先级组合数字和运算符，计算并返回所有可能组合的结果。
     * 你可以 按任意顺序 返回答案。
     *
     * 生成的测试用例满足其对应输出值符合 32 位整数范围，不同结果的数量不超过 10^4 。
     * tips:
     * 1 <= expression.length <= 20
     * expression 由数字和算符 '+'、'-' 和 '*' 组成。
     * 输入表达式中的所有整数值在范围 [0, 99]
     * 输入表达式中的所有整数都没有前导 '-' 或 '+' 表示符号。
     * @param: expression
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/11/11 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> result;
        result = method_01(expression);
        return result;
    }



    /**
     * @Description:
     * E1:输入：expression = "2-1-1"
     * 输出：[0,2]
     * 1. 3个数, 两个操作符,
     * @param: expression
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/11/11 15:30
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(String expression) {
        List<Integer> ops = new ArrayList<Integer>();
        for (int i = 0; i < expression.length();) {
            if (!Character.isDigit(expression.charAt(i))) {
                if (expression.charAt(i) == '+') {
                    ops.add(ADDITION);
                } else if (expression.charAt(i) == '-') {
                    ops.add(SUBTRACTION);
                } else {
                    ops.add(MULTIPLICATION);
                }
                i++;
            } else {
                int t = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    t = t * 10 + expression.charAt(i) - '0';
                    i++;
                }
                ops.add(t);
            }
        }
        List<Integer>[][] dp = new List[ops.size()][ops.size()];
        for (int i = 0; i < ops.size(); i++) {
            for (int j = 0; j < ops.size(); j++) {
                dp[i][j] = new ArrayList<Integer>();
            }
        }
        return dfs(dp, 0, ops.size() - 1, ops);
    }

    public List<Integer> dfs(List<Integer>[][] dp, int l, int r, List<Integer> ops) {
        if (dp[l][r].isEmpty()) {
            if (l == r) {
                dp[l][r].add(ops.get(l));
            } else {
                for (int i = l; i < r; i += 2) {
                    List<Integer> left = dfs(dp, l, i, ops);
                    List<Integer> right = dfs(dp, i + 2, r, ops);
                    for (int lv : left) {
                        for (int rv : right) {
                            if (ops.get(i + 1) == ADDITION) {
                                dp[l][r].add(lv + rv);
                            } else if (ops.get(i + 1) == SUBTRACTION) {
                                dp[l][r].add(lv - rv);
                            } else {
                                dp[l][r].add(lv * rv);
                            }
                        }
                    }
                }
            }
        }
        return dp[l][r];
    }
}
