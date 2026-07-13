package com.marks.leetcode.daily_question;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3756Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/8 11:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3756Test {

    @Test
    void sumAndMultiply() {
        // 输入： s = "10203004", queries = [[0,7],[1,3],[4,6]]
        String s = "10203004";
        int[][] queries = {{0,7},{1,3},{4,6}};
        int[] result = new LeetCode_3756().sumAndMultiply(s, queries);
        System.out.println(Arrays.toString(result));
    }
}