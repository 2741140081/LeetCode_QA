package com.marks.leetcode.all_str;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2512Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/6/23 11:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2512Test {

    @Test
    void topStudents() {
        // positive_feedback =
        //["b","a","b","ba","ab"]
        //negative_feedback =
        //["aa","bb","bb","aa"]
        //report =
        //["a","bbab","aabb","b","aa"]
        //student_id =
        //[4,3,2,5,1]
        //k = 5
        LeetCode_2512 leetCode_2512 = new LeetCode_2512();
        List<Integer> result = leetCode_2512.topStudents(new String[]{"b", "a", "b", "ba", "ab"},
                new String[]{"aa", "bb", "bb", "aa"},
                new String[]{"a", "bbab", "aabb", "b", "aa"},
                new int[]{4, 3, 2, 5, 1},
                5);

        // 打印结果
        System.out.println(result);
    }
}