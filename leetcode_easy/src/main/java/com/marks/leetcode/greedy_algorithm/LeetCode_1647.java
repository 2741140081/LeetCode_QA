package com.marks.leetcode.greedy_algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1647 {
    /**
     * @Description:
     * 如果字符串 s 中 不存在 两个不同字符 频次 相同的情况，就称 s 是 优质字符串 。
     *
     * 给你一个字符串 s，返回使 s 成为 优质字符串 需要删除的 最小 字符数。
     *
     * 字符串中字符的 频次 是该字符在字符串中的出现次数。例如，在字符串 "aab" 中，'a' 的频次是 2，而 'b' 的频次是 1 。
     * tips:
     * 1 <= s.length <= 10^5
     * s 仅含小写英文字母
     * @param s
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int minDeletions(String s) {
        int result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * 1. 先将s给收集成 Map<Character, Integer> map;
     * 2. a = 10, b = 12, c = 12, d = 11, e = 10
     * 10, 11, 12, 9, 8 => 3 + 2 = 5
     * 10， 12， 11， 9， 8
     * 1 + 2 + 2 = 5
     *
     * AC: 48ms/44.77MB
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/3/28 14:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
        }
        Set<Integer> set = new HashSet<>();
        int ans = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (set.contains(entry.getValue())) {
                int count = entry.getValue();
                while (count > 0 && set.contains(count)) {
                    count--;
                    ans++;
                }
                if (count > 0 && !set.contains(count)) {
                    set.add(count);
                }
            } else {
                set.add(entry.getValue());
            }
        }
        return ans;
    }
}
