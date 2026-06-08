package com.marks.leetcode.hash_table;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3458 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 14:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_3458 {

    /**
     * @Description:
     * 给你一个长度为 n 的字符串 s 和一个整数 k，判断是否可以选择 k 个互不重叠的 特殊子字符串 。
     * 特殊子字符串 是满足以下条件的子字符串：
     * 子字符串中的任何字符都不应该出现在字符串其余部分中。
     * 子字符串不能是整个字符串 s。
     * 注意：所有 k 个子字符串必须是互不重叠的，即它们不能有任何重叠部分。
     * 如果可以选择 k 个这样的互不重叠的特殊子字符串，则返回 true；否则返回 false。
     * 子字符串 是字符串中的连续、非空字符序列。
     *
     * tips:
     * 2 <= n == s.length <= 5 * 10^4
     * 0 <= k <= 26
     * s 仅由小写英文字母组成。
     * @param: s
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/04 14:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean maxSubstringLength(String s, int k) {
        boolean result;
        result = method_01(s, k);
        return result;
    }

    /**
     * @Description:
     * 1. 子串中如果包含某一个字母, 该子串必须要包含所有出现的字母[start, end] 表示字母出现的首次和最后一次出现的位置
     * 2. need todo
     * @param: s
     * @param: k
     * @return boolean
     * @author marks
     * @CreateDate: 2026/06/04 14:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(String s, int k) {
        // 特殊情况 k = 0
        if (k == 0) {
            return true;
        }
        int n = s.length();
        Map<Character, int[]> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                map.get(c)[1] = i;
            } else {
                map.put(c, new int[]{i, i});
            }
        }

        int ans = 0;


        return ans >= k;
    }

}
