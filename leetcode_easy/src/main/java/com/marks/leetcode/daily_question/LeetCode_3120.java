package com.marks.leetcode.daily_question;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3120 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/26 10:52
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3120 {

    /**
     * @Description:
     * 给你一个字符串 word。如果 word 中同时存在某个字母的小写形式和大写形式，则称这个字母为 特殊字母 。
     * 返回 word 中 特殊字母 的数量。
     * @param: word
     * @return int
     * @author marks
     * @CreateDate: 2026/05/26 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSpecialChars(String word) {
        int result;
        result = method_01(word);
        return result;
    }

    /**
     * @Description:
     * AC: 4ms/43.4MB
     * @param: word
     * @return int
     * @author marks
     * @CreateDate: 2026/05/26 10:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String word) {
        Set<Character> ans = new HashSet<>(); // 只记录小写的特殊字母
        Set<Character> set = new HashSet<>();
        for (char c : word.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (set.contains(Character.toLowerCase(c))) {
                    ans.add(Character.toLowerCase(c));
                }
            } else {
                if (set.contains(Character.toUpperCase(c))) {
                    ans.add(c);
                }
            }
            set.add(c);
        }

        return ans.size();
    }

}
