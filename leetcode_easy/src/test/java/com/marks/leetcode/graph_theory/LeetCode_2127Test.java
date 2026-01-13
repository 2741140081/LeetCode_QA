package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2127Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/13 14:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2127Test {

    @Test
    void maximumInvitations() {
        // [1,0,3,2,5,6,7,4,9,8,11,10,11,12,10]
        int[] favorite = {1,0,3,2,5,6,7,4,9,8,11,10,11,12,10};
        LeetCode_2127 leetCode_2127 = new LeetCode_2127();
        int result = leetCode_2127.maximumInvitations(favorite);
        System.out.println(result);
    }
}