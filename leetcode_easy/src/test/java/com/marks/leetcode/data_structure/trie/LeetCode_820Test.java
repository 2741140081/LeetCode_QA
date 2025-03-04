package com.marks.leetcode.data_structure.trie;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/4 17:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_820Test {

    @Test
    void minimumLengthEncoding() {
        String[] words = {"time", "me", "bell"};
        int result = new LeetCode_820().minimumLengthEncoding(words);
        System.out.println(result);
    }
}