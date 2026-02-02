package com.marks.leetcode.data_structure.stack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2751Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/29 18:32
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2751Test {

    @Test
    void survivedRobotsHealths() {
        // 输入：positions = [3,5,2,6], healths = [10,10,15,12], directions = "RLRL"
        // 输出：[14]
        int[] positions = {3,5,2,6};
        int[] healths = {10,10,15,12};
        String directions = "RLRL";
        LeetCode_2751 leetCode_2751 = new LeetCode_2751();
        List<Integer> result = leetCode_2751.survivedRobotsHealths(positions, healths, directions);
        System.out.println( result);
    }
}