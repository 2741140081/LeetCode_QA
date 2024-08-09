package com.marks.leetcode.classic_linear_DP.LCS;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @data 2024/8/9 15:18
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_72Test {

    @Test
    void minDistance() {
        // word1 = "horse", word2 = "ros"
        String word1 = "horse";
        String word2 = "ros";
        int result = new LeetCode_72().minDistance(word1, word2);
        System.out.println(result);
    }
}