package com.marks.leetcode.state_machine_DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/9/11 16:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LCP_19Test {

    @Test
    void minimumOperations() {
//        String leaves = "rrryyyrryyyrr";
//        String leaves = "yyyyryyyy";
        String leaves = "ryyryyyrryyyyyryyyrrryyyryryyyyryyrrryryyyryrryrrrryyyrrrrryryyrrrrryyyryyryrryryyryyyyryyrryrryryy";
        int result = new LCP_19().minimumOperations(leaves);
        System.out.println(result);
    }
}