package com.marks.leetcode.daily_question;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1189 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 11:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1189 {

    /**
     * @Description:
     * 给你一个字符串 text，你需要使用 text 中的字母来拼凑尽可能多的单词 "balloon"（气球）。
     * 字符串 text 中的每个字母最多只能被使用一次。请你返回最多可以拼凑出多少个单词 "balloon"。
     *
     * @param: text
     * @return int
     * @author marks
     * @CreateDate: 2026/06/22 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxNumberOfBalloons(String text) {
        int result;
        result = method_01(text);
        return result;
    }

    /**
     * @Description:
     * AC: 2ms/42.84MB
     * @param: text
     * @return int
     * @author marks
     * @CreateDate: 2026/06/22 11:19
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String text) {
        int[] cnt = new int[26];
        for (char ch : text.toCharArray()) {
            cnt[ch - 'a']++;
        }
        Map<Character, Integer> need = new HashMap<>();
        String needStr = "balloon";
        for (char ch : needStr.toCharArray()) {
            need.put(ch, need.getOrDefault(ch, 0) + 1);
        }
        int min = Integer.MAX_VALUE;
        for (char ch : need.keySet()) {
            min = Math.min(min, cnt[ch - 'a'] / need.get(ch));
        }


        return min;
    }

}
