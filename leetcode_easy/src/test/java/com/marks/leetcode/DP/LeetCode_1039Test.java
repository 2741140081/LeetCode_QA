package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1039Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/3/24 16:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1039Test {

    @Test
    void minScoreTriangulation() {
        // 输入：values = [1,3,1,4,1,5]
        //输出：13
        int[] values = {1,3,1,4,1,5};
        LeetCode_1039 leetCode_1039 = new LeetCode_1039();
        int result = leetCode_1039.minScoreTriangulation(values);
        System.out.println(result);
    }
}