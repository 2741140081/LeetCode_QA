package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/18 10:01
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1170Test {

    @Test
    void numSmallerByFrequency() {
        // queries = ["bbb","cc"], words = ["a","aa","aaa","aaaa"]
        String[] queries = {"bbb", "cc"};
        String[] words = {"a", "aa", "aaa", "aaaa"};
        int[] result = new LeetCode_1170().numSmallerByFrequency(queries, words);
        System.out.println(result.toString());
    }
}