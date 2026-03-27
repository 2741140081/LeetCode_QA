package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2310Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/27 14:59
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2310Test {

    @Test
    void minimumNumbers() {
        // 输入：num = 58, k = 9
        //输出：2
        int num = 58;
        int k = 9;
        int result = new LeetCode_2310().minimumNumbers(num, k);
        System.out.println(result);
    }
}