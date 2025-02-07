package com.marks.leetcode.data_structure.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/2/7 15:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2398Test {

    @Test
    void maximumRobots() {
        // chargeTimes = [3,6,1,3,4], runningCosts = [2,1,3,4,5], budget = 25
        int[] chargeTimes = {3,6,1,3,4};
        int[] runningCosts = {2,1,3,4,5};
        long budget = 25;
        int result = new LeetCode_2398().maximumRobots(chargeTimes, runningCosts, budget);
        System.out.println(result);
    }
}