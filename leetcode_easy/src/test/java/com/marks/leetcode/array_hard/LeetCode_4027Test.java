package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_4027Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/21 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_4027Test {

    @Test
    void elevatorRequests() {
        // 输入： n = 7, start = 3, requests = [[0,5],[0,1],[6,3]]
        int n = 7, start = 3;
        int[][] requests = {{0,5},{0,1},{6,3}};
        LeetCode_4027 leetCode4027 = new LeetCode_4027();
        long result = leetCode4027.elevatorRequests(n, start, requests);
        System.out.println(result);
    }
}