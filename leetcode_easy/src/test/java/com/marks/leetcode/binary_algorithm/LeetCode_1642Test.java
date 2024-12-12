package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/26 15:40
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1642Test {

    @Test
    void furthestBuilding() {
        // heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
        int[] heights = {4,12,2,7,3,18,20,3,19};
        int bricks = 10, ladders = 2;
        int result = new LeetCode_1642().furthestBuilding(heights, bricks, ladders);
        System.out.println(result);
    }
}