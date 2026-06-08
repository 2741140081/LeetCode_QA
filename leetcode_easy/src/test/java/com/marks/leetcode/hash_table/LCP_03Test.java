package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LCP_03Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/4 16:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_03Test {

    @Test
    void robot() {
        // 输入：command = "URR", obstacles = [], x = 3, y = 2
        String command = "URR";
        int[][] obstacles = {};
        int x = 3;
        int y = 2;
        LCP_03 lcp_03 = new LCP_03();
        boolean flag = lcp_03.robot(command, obstacles, x, y);
        System.out.println(flag);
    }
}