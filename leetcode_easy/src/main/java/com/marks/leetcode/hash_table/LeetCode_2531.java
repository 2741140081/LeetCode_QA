package com.marks.leetcode.hash_table;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2531 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/15 14:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2531 {

    /**
     * @Description:
     * 给你两个下标从 0 开始的字符串 word1 和 word2 。
     * 一次 移动 由以下两个步骤组成：
     * 选中两个下标 i 和 j ，分别满足 0 <= i < word1.length 和 0 <= j < word2.length ，
     * 交换 word1[i] 和 word2[j] 。
     * 如果可以通过 恰好一次 移动，使 word1 和 word2 中不同字符的数目相等，则返回 true ；否则，返回 false 。
     *
     * tips:
     * 1 <= word1.length, word2.length <= 10^5
     * word1 和 word2 仅由小写英文字母组成。
     * @param: word1
     * @param: word2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/15 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isItPossible(String word1, String word2) {
        boolean result;
        result = method_01(word1, word2);
        return result;
    }

    /**
     * @Description:
     * 1. 分别统计 word1 和 word2 中各个字符出现的次数,
     * 2. 然后枚举 m1 和 m2 交换, 如果交换之后不同字符数目相同, 返回 true
     * AC: 52ms/47.34MB
     * @param: word1
     * @param: word2
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/15 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String word1, String word2) {
        Map<Character, Integer> m1 = new HashMap<>();
        Map<Character, Integer> m2 = new HashMap<>();
        for (char c : word1.toCharArray()) {
            m1.merge(c, 1, Integer::sum);
        }
        for (char c : word2.toCharArray()) {
            m2.merge(c, 1, Integer::sum);
        }

        List<Character> list1 = new ArrayList<>(m1.keySet());
        List<Character> list2 = new ArrayList<>(m2.keySet());
        for (Character c1 : list1) {
            delete(c1, m1);
            m2.merge(c1, 1, Integer::sum);

            for (Character c2 : list2) {
                // 添加 c2 到 m1
                m1.merge(c2, 1, Integer::sum);
                // 删除 c2 从 m2
                delete(c2, m2);
                if (m1.size() == m2.size()) {
                    return true;
                }
                // 从 m1 删除 c2
                delete(c2, m1);
                // 添加 c2 到 m2
                m2.merge(c2, 1, Integer::sum);

            }

            // 添加 c1 到 m1
            m1.merge(c1, 1, Integer::sum);
            // 删除c1 从 m2
            delete(c1, m2);
        }

        return false;
    }

    private static void delete(Character ch, Map<Character, Integer> map) {
        map.merge(ch, -1, Integer::sum);
        if (map.get(ch) == 0) {
            map.remove(ch);
        }
    }

}
