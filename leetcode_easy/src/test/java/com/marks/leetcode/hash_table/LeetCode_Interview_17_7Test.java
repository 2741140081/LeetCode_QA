package com.marks.leetcode.hash_table;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_Interview_17_7Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/8 14:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_Interview_17_7Test {

    @Test
    void trulyMostPopular() {
        // 输入：names = ["John(15)","Jon(12)","Chris(13)","Kris(4)","Christopher(19)"], synonyms = ["(Jon,John)","(John,Johnny)","(Chris,Kris)","(Chris,Christopher)"]
        String[] names = {"a(10)","c(13)"};
        String[] synonyms = {"(a,b)","(c,d)","(b,c)"};
        LeetCode_Interview_17_7 leetCode_Interview_17_7 = new LeetCode_Interview_17_7();
        System.out.println(Arrays.toString(leetCode_Interview_17_7.trulyMostPopular(names, synonyms)));
    }
}