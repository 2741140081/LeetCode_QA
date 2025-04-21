package com.marks.leetcode.data_structure.union_find;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/3/10 16:03
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_990Test {

    @Test
    void equationsPossible() {
        String[] equations = {"a==b","b!=c","c==a"};
        boolean result = new LeetCode_990().equationsPossible(equations);
        System.out.println(result);
    }
}