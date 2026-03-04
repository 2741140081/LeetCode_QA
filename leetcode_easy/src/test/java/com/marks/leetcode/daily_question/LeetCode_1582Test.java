package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1582Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 11:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1582Test {

    @Test
    void numSpecial() {
        // 输入：mat = [[1,0,0],[0,0,1],[1,0,0]]
        //输出：1
        int[][] mat = {{1, 0, 0}, {0, 0, 1}, {1, 0, 0}};
        int result = new LeetCode_1582().numSpecial(mat);
        System.out.println(result);
    }
}