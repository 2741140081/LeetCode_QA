package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1711Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 11:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1711Test {

    @Test
    void countPairs() {
        // [149,107,1,63,0,1,6867,1325,5611,2581,39,89,46,18,12,20,22,234]
        LeetCode_1711 leetCode_1711 = new LeetCode_1711();
        int countPairs = leetCode_1711.countPairs(new int[]{149, 107, 1, 63, 0, 1, 6867, 1325, 5611, 2581, 39, 89, 46, 18, 12, 20, 22, 234});
        System.out.println(countPairs);
    }
}