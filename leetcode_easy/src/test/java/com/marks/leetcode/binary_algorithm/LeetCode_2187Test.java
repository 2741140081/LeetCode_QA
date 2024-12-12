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
 * @date 2024/11/20 10:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2187Test {

    @Test
    void minimumTime() {
        // 输入：time = [1,2,3], totalTrips = 5
        int[] time = {5, 10, 10};
        int totalTrips = 9;
        long result = new LeetCode_2187().minimumTime(time, totalTrips);
        System.out.println(result);
    }
}