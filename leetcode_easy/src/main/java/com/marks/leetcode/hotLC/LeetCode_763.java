package com.marks.leetcode.hotLC;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_763 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/11 9:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_763 {

    /**
     * @Description:
     * 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
     * 例如，字符串 "ababcc" 能够被分为 ["abab", "cc"]，但类似 ["aba", "bcc"] 或 ["ab", "ab", "cc"] 的划分是非法的。
     * 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
     * 返回一个表示每个字符串片段的长度的列表。
     * tips:
     * 1 <= s.length <= 500
     * s 仅由小写英文字母组成
     * @param: s
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/11 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> partitionLabels(String s) {
        List<Integer> result;
        result = method_01(s);
        return result;
    }

    /**
     * @Description:
     * E1:
     * 输入：s = "ababcbacadefegdehijhklij"
     * 输出：[9,7,8]
     * 解释：划分结果为 "ababcbaca"、"defegde"、"hijhklij" 。
     * 1. 有没有什么思路? 我可以先尝试把将每个字符类型和索引存储到Map<Character, Integer>中, 然后遍历整个字符串, 通过map更新每个子串的最终索引(最大的索引).
     * 2. 开始尝试看看结果如何. 使用数组 int[26] last 优化map, 并且需要记录每个分割子串的起始下标.
     * AC: 3ms/42.81MB
     * @param: s
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/11 10:02
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(String s) {
        int n = s.length();
        int[] last = new int[26];
        for (int i = 0; i < n; i++) {
            last[s.charAt(i) - 'a'] = i;
        }
        // 遍历字符串
        int start = 0, end = 0;
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                result.add(end - start + 1);
                start = i + 1;
            }
        }
        return result;
    }

}
