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
 * @data 2024/11/13 14:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_744Test {

    @Test
    void nextGreatestLetter() {
        // 输入: letters = ["c", "f", "j"]，target = "a"

        char[] letter = new char[]{'c', 'f', 'j'};
        char target = 'a';
        char result = new LeetCode_744().nextGreatestLetter(letter, target);

    }
}