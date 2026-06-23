package com.marks.leetcode.all_str;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2800 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/22 17:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_2800 {

    /**
     * @Description:
     * 给你三个字符串 a ，b 和 c ， 你的任务是找到长度 最短 的字符串，且这三个字符串都是它的 子字符串 。
     * 如果有多个这样的字符串，请你返回 字典序最小 的一个。
     * 请你返回满足题目要求的字符串。
     * 注意：
     * 两个长度相同的字符串 a 和 b ，如果在第一个不相同的字符处，a 的字母在字母表中比 b 的字母 靠前 ，那么字符串 a 比字符串 b 字典序小 。
     * 子字符串 是一个字符串中一段连续的字符序列。
     * tips:
     * 1 <= a.length, b.length, c.length <= 100
     * a ，b ，c 只包含小写英文字母。
     * @param: a
     * @param: b
     * @param: c
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/22 17:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public String minimumString(String a, String b, String c) {
        String result;
        result = method_01(a, b, c);
        return result;
    }

    /**
     * @Description:
     * 1.
     * @param: a
     * @param: b
     * @param: c
     * @return java.lang.String
     * @author marks
     * @CreateDate: 2026/06/22 17:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private String method_01(String a, String b, String c) {

        return null;
    }

}
