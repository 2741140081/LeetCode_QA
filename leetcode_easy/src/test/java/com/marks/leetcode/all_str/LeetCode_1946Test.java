package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1946Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/24 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1946Test {

    @Test
    void maximumNumber() {
        // num = "334111"
        //change = [0,9,2,3,3,2,5,5,5,5]
        String num = "334111";
        int[] change = {0,9,2,3,3,2,5,5,5,5};
        LeetCode_1946 leetCode_1946 = new LeetCode_1946();
        String result = leetCode_1946.maximumNumber(num, change);
        System.out.println(result);
    }
}