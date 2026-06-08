package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3679Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3679Test {

    @Test
    void minArrivalsToDiscard() {
        // int[] arrivals = {7,3,9,9,7,3,5,9,7,2,6,10,9,7,9,1,3,6,2,4,6,2,6,8,4,8,2,7,5,6}; int w = 10, int m = 1;
        int[] arrivals = {7,3,9,9,7,3,5,9,7,2,6,10,9,7,9,1,3,6,2,4,6,2,6,8,4,8,2,7,5,6};
        int w = 10, m = 1;
        LeetCode_3679 leetCode_3679 = new LeetCode_3679();
        System.out.println(leetCode_3679.minArrivalsToDiscard(arrivals, w, m));
    }
}