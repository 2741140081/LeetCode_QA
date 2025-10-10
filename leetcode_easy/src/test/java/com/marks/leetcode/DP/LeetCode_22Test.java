package com.marks.leetcode.DP;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/10/10 15:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_22Test {

    @Test
    void generateParenthesis() {
        LeetCode_22 leetCode_22 = new LeetCode_22();
        List<String> result = leetCode_22.generateParenthesis(3);
        System.out.println(result);
    }
}