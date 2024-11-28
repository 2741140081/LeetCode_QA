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
 * @date 2024/11/28 9:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_739Test {

    @Test
    void dailyTemperatures() {
        // temperatures = [73,74,75,71,69,72,76,73]
        int[] temperatures = {73,74,75,71,69,72,76,73};
        int[] result = new LeetCode_739().dailyTemperatures(temperatures);
    }
}