package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2234Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/6 14:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2234Test {

    @Test
    void maximumBeauty() {
        // flowers = [1,3,1,1], newFlowers = 7, target = 6, full = 12, partial = 1
        // flowers = [2,4,5,3], newFlowers = 10, target = 5, full = 2, partial = 6
        int[] flowers = {10,9,16,14,6,5,11,12,17,2,11,15,1};
        int newFlowers = 80, target = 14, full = 15, partial = 1;
        LeetCode_2234 leetCode_2234 = new LeetCode_2234();
        long result = leetCode_2234.maximumBeauty(flowers, newFlowers, target, full, partial);
        System.out.println(result);
    }
}