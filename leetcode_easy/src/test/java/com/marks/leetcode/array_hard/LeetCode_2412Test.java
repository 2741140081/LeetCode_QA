package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2412Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/8 14:58
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2412Test {

    @Test
    void minimumMoney() {
        // 输入：transactions = [[2,1],[5,0],[4,2]]
        int[][] transactions = {{2,1},{5,0},{4,2}};

        LeetCode_2412 leetCode2412 = new LeetCode_2412();
        long result = leetCode2412.minimumMoney(transactions);
        System.out.println(result);
    }
}