package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1578Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/11/25 15:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1578Test {

    @Test
    void minCost() {
        LeetCode_1578 leetCode_1578 = new LeetCode_1578();
        String colors = "abaac";
        int[] neededTime = {1,2,3,4,5};
        int result = leetCode_1578.minCost(colors, neededTime);
        System.out.println(result);
        assertEquals(3, result);
    }
}