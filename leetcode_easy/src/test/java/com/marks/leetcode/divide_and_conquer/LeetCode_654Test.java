package com.marks.leetcode.divide_and_conquer;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_654Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/27 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_654Test {

    @Test
    void constructMaximumBinaryTree() {
        // 输入：nums = [3,2,1,6,0,5]
        // 输出：[6,3,5,null,2,0,null,null,1]
        int[] nums = new int[] {3,2,1,6,0,5};
        LeetCode_654 lc = new LeetCode_654();
        TreeNode root = lc.constructMaximumBinaryTree(nums);
        System.out.println(root);

    }
}