package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1387Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/3 16:24
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1387Test {

    @Test
    void getKth() {
        // 输入：lo = 7, hi = 11, k = 4
        //输出：7
        int lo = 7, hi = 11, k = 4;
        LeetCode_1387 leetCode_1387 = new LeetCode_1387();
        int result = leetCode_1387.getKth(lo, hi, k);
        System.out.println(result);
    }
}