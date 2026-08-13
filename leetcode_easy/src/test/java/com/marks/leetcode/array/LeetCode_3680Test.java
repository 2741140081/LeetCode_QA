package com.marks.leetcode.array;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3680Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/7/6 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3680Test {

    @Test
    void generateSchedule() {
        int n = 5;
        LeetCode_3680 lc = new LeetCode_3680();
        int[][] result = lc.generateSchedule(n);
        // 打印数组结果, 格式为: [ [1,2], [3,4], [5,6], [7,8], [9,10] ]
        System.out.println(Arrays.deepToString(result));

    }
}