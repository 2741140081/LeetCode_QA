package com.marks.leetcode.DP;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1668 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/3 15:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1668 {

    /**
     * @Description:
     * 给你一个字符串 sequence ，如果字符串 word 连续重复 k 次形成的字符串是 sequence 的一个子字符串，那么单词 word 的 重复值为 k 。
     * 单词 word 的 最大重复值 是单词 word 在 sequence 中最大的重复值。
     * 如果 word 不是 sequence 的子串，那么重复值 k 为 0 。
     *
     * 给你一个字符串 sequence 和 word ，请你返回 最大重复值 k 。
     * @param: sequence
     * @param: word
     * @return int
     * @author marks
     * @CreateDate: 2025/11/03 15:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxRepeating(String sequence, String word) {
        int result;
        result = method_01(sequence, word);
        return result;
    }

    /**
     * @Description:
     *
     * @param: sequence
     * @param: word
     * @return int
     * @author marks
     * @CreateDate: 2025/11/03 15:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(String sequence, String word) {

        return 0;
    }

}
