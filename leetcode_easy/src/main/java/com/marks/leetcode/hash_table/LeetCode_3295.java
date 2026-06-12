package com.marks.leetcode.hash_table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3295 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/9 9:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3295 {

    public boolean reportSpam(String[] message, String[] bannedWords) {
        boolean result;
        result = method_01(message, bannedWords);
        return result;
    }

    // AC: 41ms/146.64MB
    private boolean method_01(String[] message, String[] bannedWords) {
        Set<String> set = new HashSet<>(Arrays.asList(bannedWords));
        int count = 0;
        for (String word : message) {
            if (set.contains(word)) {
                count++;
            }
            if (count >= 2) {
                return true;
            }
        }
        return false;
    }

}
