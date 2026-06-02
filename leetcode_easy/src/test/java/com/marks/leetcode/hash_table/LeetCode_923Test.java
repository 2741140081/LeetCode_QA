package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_923Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/1 15:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_923Test {

    @Test
    void threeSumMulti() {
        // 输入：arr = [1,1,2,2,3,3,4,4,5,5], target = 8
        LeetCode_923 leetCode_923 = new LeetCode_923();
        int i = leetCode_923.threeSumMulti(new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5}, 8);
        System.out.println(i);
    }
}