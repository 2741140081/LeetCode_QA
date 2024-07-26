package com.marks.leetcode.gridDP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/7/26 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1937Test {

    @Test
    void maxPoints() {
        // points = [[1,2,3],[1,5,1],[3,1,1]]
        int[][] points = {{1,2,3}, {1,5,1}, {3,1,1}};
        long result = new LeetCode_1937().maxPoints(points);
        System.out.println(result);
    }
}