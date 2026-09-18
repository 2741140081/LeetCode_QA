package com.marks.leetcode.array_medium;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_954Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/9/18 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_954Test {

    @Test
    void canReorderDoubled() {
        // arr = [1,2,1,-8,8,-4,4,-4,2,-2]
        int[] arr = {1,2,1,-8,8,-4,4,-4,2,-2};
        boolean result = new LeetCode_954().canReorderDoubled(arr);
        System.out.println(result);
    }

}