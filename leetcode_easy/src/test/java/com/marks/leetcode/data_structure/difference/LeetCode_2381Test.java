package com.marks.leetcode.data_structure.difference;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/18 15:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2381Test {

    @Test
    void shiftingLetters() {
        String s = "abc";
        int[][] shifts = {{0,1,0},{1,2,1},{0,2,1}};
        String result = new LeetCode_2381().shiftingLetters(s, shifts);
        System.out.println(result);
    }
}