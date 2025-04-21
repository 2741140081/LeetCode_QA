package com.marks.leetcode.data_structure.queue;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/22 14:35
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_950Test {

    @Test
    void deckRevealedIncreasing() {
        // int
        int[] deck = {17,13,11,2,3,5,7};
        int[] result = new LeetCode_950().deckRevealedIncreasing(deck);
        System.out.println(result.length);
    }
}