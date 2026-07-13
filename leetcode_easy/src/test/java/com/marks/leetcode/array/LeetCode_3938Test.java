package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3938Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/7 17:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3938Test {

    @Test
    void maxScore() {
        // [[-18,-5,20],[-13,4,-3]]
        int[][] grid = {{-18,-5,20},{-13,4,-3}};
        //  grid = [[4,-2,-3],[-1,-3,-1],[-4,2,-1]]
        grid = new int[][]{{4,-2,-3},{-1,-3,-1},{-4,2,-1}};
        int result = new LeetCode_3938().maxScore(grid);
        System.out.println(result);
    }
}