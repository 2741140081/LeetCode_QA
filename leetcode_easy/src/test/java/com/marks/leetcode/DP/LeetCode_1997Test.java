package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1997Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/4 10:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1997Test {

    @Test
    void firstDayBeenInAllRooms() {
        // nextVisit = [0,0,2]
        int[] nextVisit = {0,0,2};
        LeetCode_1997 leetCode_1997 = new LeetCode_1997();
        int result = leetCode_1997.firstDayBeenInAllRooms(nextVisit);
        System.out.println(result);
    }
}