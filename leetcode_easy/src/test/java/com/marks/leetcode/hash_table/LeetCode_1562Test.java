package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1562Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 15:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1562Test {

    @Test
    void findLatestStep() {
        // 输入：arr = [3,5,1,2,4], m = 1
        LeetCode_1562 leetCode_1562 = new LeetCode_1562();
        int findLatestStep = leetCode_1562.findLatestStep(new int[]{3, 5, 1, 2, 4}, 1);
        // 输入：arr = [3,1,5,4,2], m = 2
        int findLatestStep2 = leetCode_1562.findLatestStep(new int[]{3, 1, 5, 4, 2}, 2);
        System.out.println(findLatestStep);
        System.out.println(findLatestStep2);
    }
}