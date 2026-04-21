package com.marks.leetcode.DP_II;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1000Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/4/21 15:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1000Test {

    @Test
    void mergeStones() {
        // stones = [3,5,1,2,6], K = 3
        int[] stones = {3,5,1,2,6};
        int K = 3;
        LeetCode_1000 leetCode_1000 = new LeetCode_1000();
        int i = leetCode_1000.mergeStones(stones, K);
        System.out.println(i);
    }
}