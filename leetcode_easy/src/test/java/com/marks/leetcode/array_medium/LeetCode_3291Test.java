package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3291Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/16 11:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3291Test {

    @Test
    void minValidStrings() {
        // 输入： words = ["abc","aaaaa","bcdef"], target = "aabcdabc"
        String[] words = {"abc","aaaaa","bcdef"};
        String target = "aabcdabc";
        int result = new LeetCode_3291().minValidStrings(words, target);
        System.out.println(result);
    }
}