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
 * @date 2025/3/10 15:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class DinnerPlatesTest {

    @Test
    void pop() {
        DinnerPlates plates = new DinnerPlates(1);
        plates.push(1);
        plates.push(2);
        plates.push(3);
        plates.popAtStack(1);
    }
}