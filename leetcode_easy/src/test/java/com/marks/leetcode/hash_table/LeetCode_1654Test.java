package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1654Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/2 9:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1654Test {

    @Test
    void minimumJumps() {
        // 输入：forbidden = [1,6,2,14,5,17,4], a = 16, b = 9, x = 7
        LeetCode_1654 leetCode_1654 = new LeetCode_1654();
        int i = leetCode_1654.minimumJumps(new int[]{1, 6, 2, 14, 5, 17, 4}, 16, 9, 7);
        System.out.println(i);
    }
}