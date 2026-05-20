package com.marks.leetcode.data_structure.stack;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1008Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/5/18 10:38
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1008Test {

    @Test
    void bstFromPreorder() {
        // preorder = [8,5,1,7,10,12]
        int[] preorder = {8, 5, 1, 7, 10, 12};
        LeetCode_1008 leetCode_1008 = new LeetCode_1008();
        TreeNode result = leetCode_1008.bstFromPreorder(preorder);
        System.out.println(result);
    }
}