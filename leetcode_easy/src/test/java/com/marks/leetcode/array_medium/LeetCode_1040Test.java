package com.marks.leetcode.array_medium;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1040Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/21 15:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1040Test {

    @Test
    void numMovesStonesII() {
        // 8,7,6,5,10
        int[] stones = {8,7,6,5,10};
        int[] result = new LeetCode_1040().numMovesStonesII(stones);
        System.out.println(Arrays.toString(result));
    }
}