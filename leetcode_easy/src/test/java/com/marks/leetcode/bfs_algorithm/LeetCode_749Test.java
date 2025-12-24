package com.marks.leetcode.bfs_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_749Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/23 15:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_749Test {

    @Test
    void containVirus() {
        // [[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,1,0,0],[1,0,0,0,0,0,0,0,0,0],[0,0,1,0,0,0,1,0,0,0],[0,0,0,0,0,0,1,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,1,0],[0,0,0,0,1,0,1,0,0,0],[0,0,0,0,0,0,0,0,0,0]]
        int[][] isInfected = {{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,1,0,0},{1,0,0,0,0,0,0,0,0,0},{0,0,1,0,0,0,1,0,0,0},{0,0,0,0,0,0,1,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,1,0},{0,0,0,0,1,0,1,0,0,0},{0,0,0,0,0,0,0,0,0,0}};
        LeetCode_749 leetCode_749 = new LeetCode_749();
        int result = leetCode_749.containVirus(isInfected);
        System.out.println(result);
    }
}