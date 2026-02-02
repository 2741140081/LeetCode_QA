package com.marks.leetcode.data_structure.segment_tree;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_3477Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/30 10:22
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_3477Test {

    @Test
    void numOfUnplacedFruits() {
        // 输入： fruits = [4,2,5], baskets = [3,5,4]
        int[] fruits = {4, 2, 5};
        int[] baskets = {3, 5, 4};
        LeetCode_3477 leetCode_3477 = new LeetCode_3477();
        int result = leetCode_3477.numOfUnplacedFruits(fruits, baskets);
    }
}