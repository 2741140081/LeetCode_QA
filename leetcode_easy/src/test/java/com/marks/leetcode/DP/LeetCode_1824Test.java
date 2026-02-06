package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1824Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/2/5 16:19
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1824Test {

    @Test
    void minSideJumps() {
        // 输入：obstacles = [0,1,2,3,0]
        // 输出：2
        int[] obstacles = {0,1,2,3,0};
        LeetCode_1824 leetCode_1824 = new LeetCode_1824();
        int result = leetCode_1824.minSideJumps(obstacles);
        System.out.println(result);
    }
}