package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_13Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/9 15:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_13Test {

    @Test
    void minimalSteps() {
        // 输入： ["S#O", "M..", "M.T"]
        String[] maze = {"S#O", "M..", "M.T"};
        System.out.println(new LCP_13().minimalSteps(maze));
    }
}