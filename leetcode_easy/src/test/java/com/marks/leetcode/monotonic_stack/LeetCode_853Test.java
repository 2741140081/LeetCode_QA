package com.marks.leetcode.monotonic_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2024/11/29 9:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_853Test {

    @Test
    void carFleet() {
        // arget = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
        int target = 10;
        int[] position = {0, 4, 2};
        int[] speed = {2, 1, 3};
        int result = new LeetCode_853().carFleet(target, position, speed);
        System.out.println(result);
    }
}