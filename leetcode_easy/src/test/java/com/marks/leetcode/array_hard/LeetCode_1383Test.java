package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1383Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/7 17:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1383Test {

    @Test
    void maxPerformance() {
        // 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
        // 输出：60
        int n = 6;
        int[] speed = {2,10,3,1,5,8};
        int[] efficiency = {5,4,3,9,7,2};
        int k = 2;
        // 输入：n = 3, speed = [2,8,2], efficiency = [2,7,1], k = 2
        // 输出：56
        n = 3;
        speed = new int[]{2,8,2};
        efficiency = new int[]{2,7,1};
        k = 2;

        // 输入：n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
        // 输出：68
        n = 6;
        speed = new int[]{2,10,3,1,5,8};
        efficiency = new int[]{5,4,3,9,7,2};
        k = 3;

        int result = new LeetCode_1383().maxPerformance(n, speed, efficiency, k);
//        assertEquals(60, result);
        System.out.println(result);
    }
}