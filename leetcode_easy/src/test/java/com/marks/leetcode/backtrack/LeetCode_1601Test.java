package com.marks.leetcode.backtrack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/8/20 10:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1601Test {

    @Test
    void maximumRequests() {
        // [[1,2],[1,2],[2,2],[0,2],[2,1],[1,1],[1,2]]
        int n = 3;
        int[][] requests = new int[][]{{1,2},{1,2},{2,2},{0,2},{2,1},{1,1},{1,2}};
        LeetCode_1601 leetCode_1601 = new LeetCode_1601();
        int result = leetCode_1601.maximumRequests(n, requests);
        System.out.println(result);
        assertEquals(4, result);
    }
}