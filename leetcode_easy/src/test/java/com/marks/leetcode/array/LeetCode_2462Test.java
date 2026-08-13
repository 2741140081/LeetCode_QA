package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2462Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/6 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2462Test {

    @Test
    void totalCost() {
        // 输入：costs = [1,2,4,1], k = 3, candidates = 3
        int[] costs = {1,2,4,1};
        int k = 3;
        int candidates = 3;
        long result = new LeetCode_2462().totalCost(costs, k, candidates);
        System.out.println(result);
    }
}