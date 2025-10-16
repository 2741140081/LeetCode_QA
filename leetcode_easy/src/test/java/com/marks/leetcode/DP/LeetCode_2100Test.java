package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/14 16:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2100Test {

    @Test
    void goodDaysToRobBank() {
        LeetCode_2100 leetCode_2100 = new LeetCode_2100();
        List<Integer> goodDaysToRobBank = leetCode_2100.goodDaysToRobBank(new int[]{5, 3, 3, 3, 5, 6, 2}, 2);
        System.out.println(goodDaysToRobBank);
        assertEquals(List.of(2, 3), goodDaysToRobBank);
    }
}