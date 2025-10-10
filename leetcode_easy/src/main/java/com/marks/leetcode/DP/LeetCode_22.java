package com.marks.leetcode.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 15:05
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_22 {

    
    /**
     * @Description:
     * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * @param n 
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/10/10 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> generateParenthesis(int n) {
        List<String> result;
        result = method_01(n);
        return result;
    }

    
    /**
     * @Description:
     * 想到的思路是使用回溯法来枚举所有的可能性, 然后判断是否有效
     * AC: 0ms/41.81MB
     *
     * 但是怎么用动态规划来解决这个问题? 算了这题更容易用回溯法解决, 不去费时间想动态规划了
     * @param n 
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/10/10 15:06
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(int n) {
        int len = n * 2;
        char[] chars = new char[len];
        List<String> ans = new ArrayList<>();
        backTracking(chars, 0, 0, 0, ans, n);
        return ans;
    }

    private void backTracking(char[] chars, int index, int left, int right, List<String> ans, int n) {
        if (right > left) {
            // 不合法的情况
            return;
        }
        if (index == chars.length) {
            if (left == right) {
                ans.add(new String(chars));
            }
            return;
        }
        if (left < n) {
            chars[index] = '(';
            backTracking(chars, index + 1, left + 1, right, ans, n);
        }
        if (right < n) {
            chars[index] = ')';
            backTracking(chars, index + 1, left, right + 1, ans, n);
        }
    }

}
