package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/1 17:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3440Test {

    @Test
    void maxFreeTime() {
        // eventTime = 10, startTime = [0,3,7,9], endTime = [1,4,8,10]

//        int eventTime = 10;
//        int[] startTime = {0, 3, 7, 9}, endTime = {1, 4, 8, 10};
        // 0, 17_19, 24_25, 41

        //      * eventTime =405
        //     * startTime = [48,84,94,335]
        //     * endTime = [55,88,113,378]
        int eventTime = 405;
        int[] startTime = {48,84,94,335}, endTime = {55,88,113,378};
        int result = new LeetCode_3440().maxFreeTime(eventTime, startTime, endTime);
        System.out.println(result);
    }
}