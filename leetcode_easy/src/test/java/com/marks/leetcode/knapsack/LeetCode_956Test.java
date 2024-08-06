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
 * @data 2024/8/6 14:33
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_956Test {

    @Test
    void tallestBillboard() {
//        int[] robs = new int[]{1, 2, 3, 6};
        int[] robs = new int[]{1,2,3,4,5,6};
        int result = new LeetCode_956().tallestBillboard(robs);
        System.out.println(result);
    }
}