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
 * @date 2025/4/2 17:54
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_881Test {

    @Test
    void numRescueBoats() {
        // people = [3,2,2,1], limit = 3
        // people = [3,5,3,4], limit = 5
        int[] people = {3,5,3,4};
        int limit = 5;
        int result = new LeetCode_881().numRescueBoats(people, limit);
        System.out.println(result);
    }
}