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
 * @date 2024/11/22 11:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3296Test {

    @Test
    void minNumberOfSeconds() {
        //  mountainHeight = 4, workerTimes = [2,1,1]
        int mountainHeight = 4;
        int[] workerTimes = {2, 1, 1};
        long result = new LeetCode_3296().minNumberOfSeconds(mountainHeight, workerTimes);
        System.out.println(result);
    }
}