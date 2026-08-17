package com.marks.leetcode.array_hard;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3161Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/8/17 10:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3161Test {

    @Test
    void getResults() {
        // 输入：queries = [[1,7],[2,7,6],[1,2],[2,7,5],[2,7,6]]
        int[][] queries = {{1,7},{1,2},{2,7,5},{2,7,6}};
        LeetCode_3161 leetCode3161 = new LeetCode_3161();
        List<Boolean> results = leetCode3161.getResults(queries);
        // 将 List 输出为集合的结果形式
        System.out.println(Arrays.toString(results.toArray()));
    }
}