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
 * @date 2024/11/20 14:43
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1870Test {

    @Test
    void minSpeedOnTime() {
        // 输入：dist = [1,3,2], hour = 2.7
        int[] dist = {1, 3, 2};
        double hour = 2.7;
        int result = new LeetCode_1870().minSpeedOnTime(dist, hour);
        System.out.println(result);
    }
}