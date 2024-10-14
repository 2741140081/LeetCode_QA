package com.marks.leetcode.sliding_window.random_length;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/10/14 14:45
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_904Test {

    @Test
    void totalFruit() {
        // fruits = [3,3,3,1,2,1,1,2,3,3,4]
        // 0,1,1
        int[] fruits = {0,1,1};
        int result = new LeetCode_904().totalFruit(fruits);
        System.out.println(result);
    }
}