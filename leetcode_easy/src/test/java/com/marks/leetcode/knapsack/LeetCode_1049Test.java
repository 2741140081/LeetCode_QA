package com.marks.leetcode.knapsack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/2 16:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1049Test {

    @Test
    void lastStoneWeightII() {
        // stones = [2,7,4,1,8,1]
        int[] stones = new int[]{2,7,4,1,8,1};

        // stones = [31,26,33,21,40]
//        int[] stones = new int[]{31,26,33,21,40};
        int result = new LeetCode_1049().lastStoneWeightII(stones);
        System.out.println(result);
    }
}