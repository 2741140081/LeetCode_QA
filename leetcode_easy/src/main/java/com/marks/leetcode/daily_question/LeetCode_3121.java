package com.marks.leetcode.daily_question;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3121 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/27 11:47
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3121 {

    /**
     * @Description:
     * 给你一个字符串 word。如果 word 中同时出现某个字母 c 的小写形式和大写形式，并且 每个 小写形式的 c 都出现在第一个大写形式的 c 之前，则称字母 c 是一个 特殊字母 。
     * 返回 word 中 特殊字母 的数量。
     * @param: word
     * @return int
     * @author marks
     * @CreateDate: 2026/05/27 11:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int numberOfSpecialChars(String word) {
        int result;
        result = method_01(word);
        return result;
    }

    /**
     * @Description:
     * 1. 记录每一个大写字母的最早出现位置 min, 并且记录每一个小写字母最晚出现位置 max, 对比两者的值. 如果 max[i] < min[i], 则说明 word[i] 是特殊字符.
     * AC: 50ms/47.43MB
     * @param: word
     * @return int
     * @author marks
     * @CreateDate: 2026/05/27 11:48
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String word) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                // 记录大写字母的最早出现位置
                if (!map.containsKey(c)) {
                    map.put(c, i);
                }
            } else if (Character.isLowerCase(c)) {
                // 记录小写字母最晚出现位置
                map.put(c, i);
            }
        }
        int ans = 0;
        // 遍历 26 个字母
        for (int i = 0; i < 26; i++) {
            char c = (char) ('a' + i);
            if (map.containsKey(c)) {
                char uc = Character.toUpperCase(c);
                if (map.containsKey(uc)) {
                    if (map.get(uc) > map.get(c)) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }

}
