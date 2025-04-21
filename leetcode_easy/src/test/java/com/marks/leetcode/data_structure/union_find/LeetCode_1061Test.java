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
 * @date 2025/3/12 10:21
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1061Test {

    @Test
    void smallestEquivalentString() {
        String s1 = "leetcode", s2 = "programs", baseStr = "sourcecode";
        String result = new LeetCode_1061().smallestEquivalentString(s1, s2, baseStr);
        System.out.println(result);
    }
}