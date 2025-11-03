package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_392 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/3 15:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_392 {

    /**
     * @Description:
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * 进阶：
     * 如果有大量输入的 S，称作 S1, S2, ... , Sk 其中 k >= 10亿，你需要依次检查它们是否为 T 的子序列。在这种情况下，你会怎样改变代码？
     * tips:
     * 0 <= s.length <= 100
     * 0 <= t.length <= 10^4
     * 两个字符串都只由小写字符组成。
     * @param: s
     * @param: t
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/03 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isSubsequence(String s, String t) {
        boolean result;
        result = method_01(s, t);
        return result;
    }


    /**
     * @Description:
     * 双指针
     * @param: s
     * @param: t
     * @return boolean
     * @author marks
     * @CreateDate: 2025/11/03 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, String t) {
        int ans = 0;
        if (s.isEmpty()) {
            return true;
        }
        for (int i = 0; i < t.length(); i++) {
            if (s.charAt(ans) == t.charAt(i)) {
                ans++;
            }
            if (ans == s.length()) {
                return true;
            }
        }
        return false;
    }

}
