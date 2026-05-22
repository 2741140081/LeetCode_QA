package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_874Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/21 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_874Test {

    @Test
    void robotSim() {
        // commands = [6,-1,-1,6], obstacles = [[0,0]]
        int[] commands = {6, -1, -1, 6};
        int[][] obstacles = {{0, 0}};
        LeetCode_874 leetCode_874 = new LeetCode_874();
        int result = leetCode_874.robotSim(commands, obstacles);
        System.out.println(result);
    }
}