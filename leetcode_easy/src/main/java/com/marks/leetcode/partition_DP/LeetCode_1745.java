package com.marks.leetcode.partition_DP;

import java.util.Arrays;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/27 14:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1745 {
    /**
     * @Description: [
     * 给你一个字符串 s ，如果可以将它分割成三个 非空 回文子字符串，那么返回 true ，否则返回 false 。
     *
     * 当一个字符串正着读和反着读是一模一样的，就称其为 回文字符串 。
     * tips:
     * 3 <= s.length <= 2000
     * s只包含小写英文字母。
     * ]
     * @param s
     * @return boolean
     * @author marks
     * @CreateDate: 2024/9/27 14:40
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean checkPartitioning(String s) {
        boolean result = false;
        result = method_01(s);
        return result;
    }
    /**
     * @Description: [
     * E1:
     * 输入：s = "abcbdd"
     * 输出：true
     * 解释："abcbdd" = "a" + "bcb" + "dd"，三个子字符串都是回文的。
     *
     * 1.预处理字符串"s"
     * AC:144ms/44.68ms
     * ]
     * @param s
     * @return boolean
     * @author marks
     * @CreateDate: 2024/9/27 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s) {
        int n = s.length();
        boolean[][] pre = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(pre[i], true); // 设置默认值为true
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                pre[i][j] = (s.charAt(i) == s.charAt(j)) && pre[i + 1][j - 1];
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (pre[0][i] && pre[i + 1][j] && pre[j + 1][n - 1]) {
                    return true;
                }
            }
        }
        return false;
    }
}
