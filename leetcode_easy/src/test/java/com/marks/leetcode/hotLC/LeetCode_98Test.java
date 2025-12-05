package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_98Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 14:55
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_98Test {

    @Test
    void isValidBST() {
        TreeNode root = new TreeNode(45);
        root.left = new TreeNode(42);
        root.right = null;
        root.left.left = null;
        root.left.right = new TreeNode(44);
        root.left.right.left = new TreeNode(43);
        root.left.right.right = null;
        root.left.right.left.left = new TreeNode(41);
        LeetCode_98 leetCode_98 = new LeetCode_98();
        boolean result = leetCode_98.isValidBST(root);
        assertFalse(result);
        System.out.println(result);
    }
}