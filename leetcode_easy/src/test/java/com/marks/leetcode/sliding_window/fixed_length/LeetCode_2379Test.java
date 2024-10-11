package com.marks.leetcode.sliding_window.fixed_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/11 15:56
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2379Test {

    @Test
    void minimumRecolors() {
        String blocks = "WBBWWBBWBW";
        int k = 7;
        int result = new LeetCode_2379().minimumRecolors(blocks, k);
        System.out.println(result);
    }
}