package com.marks.leetcode.greedy_algorithm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/1 11:17
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_948Test {

    @Test
    void bagOfTokensScore() {
        int[] tokens = {100,200,300,400};
        int power = 200;
        int result = new LeetCode_948().bagOfTokensScore(tokens, power);
        System.out.println(result);
    }
}