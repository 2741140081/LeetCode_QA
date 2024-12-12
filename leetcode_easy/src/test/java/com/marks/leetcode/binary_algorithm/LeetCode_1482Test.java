package com.marks.leetcode.binary_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/22 16:30
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1482Test {

    @Test
    void minDays() {
        // bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
        // bloomDay = [1,10,3,10,2], m = 3, k = 1
        int[] bloomDay = {1,10,3,10,2};
        int m = 3;
        int k = 1;
        int result = new LeetCode_1482().minDays(bloomDay, m, k);
        System.out.println(result);
    }
}