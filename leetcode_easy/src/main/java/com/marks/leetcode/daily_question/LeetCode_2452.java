package com.marks.leetcode.daily_question;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2452 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/22 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2452 {

    /**
     * @Description:
     * 给你两个字符串数组 queries 和 dictionary 。数组中所有单词都只包含小写英文字母，且长度都相同。
     * 一次 编辑 中，你可以从 queries 中选择一个单词，将任意一个字母修改成任何其他字母。
     * 从 queries 中找到所有满足以下条件的字符串：不超过 两次编辑内，字符串与 dictionary 中某个字符串相同。
     * 请你返回 queries 中的单词列表，这些单词距离 dictionary 中的单词 编辑次数 不超过 两次 。
     * 单词返回的顺序需要与 queries 中原本顺序相同。
     *
     * tips:
     * 1 <= queries.length, dictionary.length <= 100
     * n == queries[i].length == dictionary[j].length
     * 1 <= n <= 100
     * 所有 queries[i] 和 dictionary[j] 都只包含小写英文字母。
     * @param: queries
     * @param: dictionary
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/04/22 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> result;
        result = method_01(queries, dictionary);
        return result;
    }

    /**
     * @Description:
     * 1. 直接双重for 循环, 判断 queries[i] 与 dictionary[j] 的不同字符数, 如果大于2, 跳过,
     * 如果小于等于2, 则添加到结果集合, 然后处理下一个
     * 2. 判断不同个数使用双指针
     * AC: 18ms/43.4MB
     * @param: queries
     * @param: dictionary
     * @return java.util.List<java.lang.String>
     * @author marks
     * @CreateDate: 2026/04/22 15:04
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<String> method_01(String[] queries, String[] dictionary) {
        List<String> ans = new ArrayList<>();
        for (String query : queries) {
            for (String dic : dictionary) {
                int count = 0;
                for (int i = 0; i < query.length(); i++) {
                    if (query.charAt(i) != dic.charAt(i)) {
                        count++;
                    }
                }
                if (count <= 2) {
                    ans.add(query);
                    break;
                }
            }
        }

        return ans;
    }

}
