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
 * @date 2025/3/7 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_30Test {

    @Test
    void magicTower() {
        int[] nums = {100,100,100,-250,-60,-140,-50,-50,100,150};
        int result = new LCP_30().magicTower(nums);
        System.out.println(result);
    }
}