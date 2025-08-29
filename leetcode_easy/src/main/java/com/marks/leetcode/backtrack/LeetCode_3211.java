package com.marks.leetcode.backtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/26 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3211 {

    /**
     * @Description:
     * 给你一个正整数 n。
     * 如果一个二进制字符串 x 的所有长度为 2 的子字符串中包含 至少 一个 "1"，则称 x 是一个 有效 字符串。
     *
     * 返回所有长度为 n 的 有效 字符串，可以以任意顺序排列。
     *
     * tips:
     * 1 <= n <= 18
     * @param n
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/26 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> validStrings(int n) {
        List<String> result;
        result = method_01(n);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入： n = 3
     * 输出： ["010","011","101","110","111"]
     *
     * AC: 2ms/45.27MB
     * @param n
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2025/8/26 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List< String> ans;
    private List<String> method_01(int n) {
        ans = new ArrayList<>();
        char[] array = new char[n];
        array[0] = '1';
        backtrack(array, 1);
        array[0] = '0';
        backtrack(array, 1);
        return ans;
    }

    private void backtrack(char[] array, int index) {
        if (index == array.length) {
            ans.add(new String(array));
            return;
        }
        array[index] = '1';
        backtrack(array, index + 1);
        if (array[index - 1] == '1') {
            array[index] = '0';
            backtrack(array, index + 1);
        }

    }

}
