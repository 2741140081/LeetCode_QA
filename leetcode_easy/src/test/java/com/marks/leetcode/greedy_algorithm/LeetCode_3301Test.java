package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/28 17:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3301Test {

    @Test
    void maximumTotalSum() {
        int[] maximumHeight = {2,3,4,3};
        long result = new LeetCode_3301().maximumTotalSum(maximumHeight);
        System.out.println(result);
    }
}