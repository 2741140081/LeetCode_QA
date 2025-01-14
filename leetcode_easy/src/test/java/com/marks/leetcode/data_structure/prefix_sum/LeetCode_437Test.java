package com.marks.leetcode.data_structure.prefix_sum;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/1/14 10:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_437Test {

    @Test
    void pathSum() {
        int targetSum = 8;
        TreeNode root = new TreeNode(10);
        root.setLeft(new TreeNode(5));
        root.setRight(new TreeNode(-3));
        root.getLeft().setLeft(new TreeNode(3));
        root.getLeft().setRight(new TreeNode(2));
        root.getRight().setRight(new TreeNode(11));
        root.getLeft().getLeft().setLeft(new TreeNode(3));
        root.getLeft().getLeft().setRight(new TreeNode(-2));
        root.getLeft().getRight().setRight(new TreeNode(1));
        int result = new LeetCode_437().pathSum(root, targetSum);
        System.out.println(result);
    }
}