package com.marks.leetcode.data_structure.common_enum_technique;

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
 * @date 2025/1/10 16:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1930 {
    /**
     * @Description:
     * 给你一个字符串 s ，返回 s 中 长度为 3 的不同回文子序列 的个数。
     * 即便存在多种方法来构建相同的子序列，但相同的子序列只计数一次。
     * 回文 是正着读和反着读一样的字符串。
     * 子序列 是由原字符串删除其中部分字符（也可以不删除）且不改变剩余字符之间相对顺序形成的一个新字符串。
     *
     * 例如，"ace" 是 "abcde" 的一个子序列。
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/10 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countPalindromicSubsequence(String s) {
        int result;
        result = method_01(s);
        return result;
    }
    
    /**
     * @Description:
     *
     * AC:210ms/44.42MB
     * @param s 
     * @return int
     * @author marks
     * @CreateDate: 2025/1/10 16:18
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String s) {
        int n = s.length();
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < 26; i++) {
            int left = 0;
            int right = n - 1;
            char ch = (char) (i + 97);
            char test = 'a';
            if (!map.containsKey(ch) || map.get(ch) < 2) {
                continue;
            }
            while (s.charAt(left) != ch && left < n - 2) {
                left++;
            }
            while (s.charAt(right) != ch && right > 1) {
                right--;
            }
            Set<Character> set = new HashSet<>();
            for (int j = left + 1; j < right; j++) {
                set.add(s.charAt(j));
            }
            ans += set.size();
        }
        return ans;
    }
}
