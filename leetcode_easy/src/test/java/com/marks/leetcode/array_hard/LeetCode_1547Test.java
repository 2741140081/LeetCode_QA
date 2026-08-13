package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1547Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/12 15:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1547Test {

    @Test
    void minCost() {
        // 输入：n = 7, cuts = [1,3,4,5]
        // 输入：n = 9, cuts = [5,6,1,4,2]
        int n = 9;
        int[] cuts = {5,6,1,4,2};

        LeetCode_1547 leetCode_1547 = new LeetCode_1547();
        int res = leetCode_1547.minCost(n, cuts);
        System.out.println(res);
    }
}