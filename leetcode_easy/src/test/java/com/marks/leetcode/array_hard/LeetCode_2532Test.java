package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2532Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/12 13:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2532Test {

    @Test
    void findCrossingTime() {
        // 输入：n = 1, k = 3, time = [[1,1,2,1],[1,1,3,1],[1,1,4,1]]
        int n = 1, k = 3;
        int[][] time = new int[][]{{1,1,2,1},{1,1,3,1},{1,1,4,1}};
        LeetCode_2532 leetCode_2532 = new LeetCode_2532();
        int result = leetCode_2532.findCrossingTime(n, k, time);
        System.out.println(result);
    }
}