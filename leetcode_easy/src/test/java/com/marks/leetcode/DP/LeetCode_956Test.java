package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_956Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/20 14:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_956Test {

    @Test
    void tallestBillboard() {
        // 输入：[1,2,3,4,5,6]
        int[] rods = {1,2,3,4,5,6};
        LeetCode_956 leetCode_956 = new LeetCode_956();
        int result = leetCode_956.tallestBillboard(rods);
        System.out.println(result);
    }
}