package com.marks.leetcode.graph_theory;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_2876Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/19 17:34
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_2876Test {

    @Test
    void countVisitedNodes() {
        // 输入：edges = [3,6,1,0,5,7,4,3]
        //输出：[3,6,1,0,5,7,4,3]
        LeetCode_2876 leetCode_2876 = new LeetCode_2876();
        int[] result = leetCode_2876.countVisitedNodes(List.of(3,6,1,0,5,7,4,3));
        System.out.println(Arrays.toString(result));
    }
}