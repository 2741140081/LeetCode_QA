package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_118Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/29 17:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_118Test {

    @Test
    void generate() {
        LeetCode_118 leetCode_118 = new LeetCode_118();
        List<List<Integer>> result = leetCode_118.generate(5);

        LeetCode_119 leetCode_119 = new LeetCode_119();
        List<Integer> result1 = leetCode_119.getRow(5);
        System.out.println(result);
    }
}