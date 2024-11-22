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
 * @date 2024/11/22 15:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_475Test {

    @Test
    void findRadius() {
        // houses = [1,2,3,4], heaters = [1,4]
        int[] houses = {1, 2, 3, 4};
        int[] heaters = {1, 4};
        int result = new LeetCode_475().findRadius(houses, heaters);
        System.out.println(result);
    }
}