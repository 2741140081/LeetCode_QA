package com.marks.leetcode.data_structure.heap_advance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/7 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_871Test {

    @Test
    void minRefuelStops() {
        // target = 100, startFuel = 10, stations = [[10,60],[20,30],[30,30],[60,40]]
        int target = 200, startFuel = 50;
        int[][] stations = {{25,25}, {50,100}, {100,100}, {150,40}};
        int result = new LeetCode_871().minRefuelStops(target, startFuel, stations);
        System.out.println(result);
    }
}