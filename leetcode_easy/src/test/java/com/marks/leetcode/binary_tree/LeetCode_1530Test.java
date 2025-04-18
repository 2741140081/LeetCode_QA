package com.marks.leetcode.binary_tree;

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
 * @date 2025/4/18 11:13
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_1530Test {

    @Test
    void countPairs() {
        TreeNode root = new TreeNode(15);

        // 0 层
        buildChild(root, 66, 55);

        // 1 层
        buildChild(root.left, 97, 60);
        buildChild(root.right, 12, 56);

        // 2 层
        buildChild(root.left.left, -1, 54);
        buildChild(root.left.right, -1, 49);
        buildChild(root.right.left, -1, 9);
        buildChild(root.right.right, -1, -1);

        // 3 层
        buildChild(root.left.left.right, -1, -1);
        buildChild(root.left.right.right, -1, 90);

        int result = new LeetCode_1530().countPairs(root, 5);
        System.out.println(result);
    }

    private void buildChild(TreeNode root, int left, int right) {
        if (left != -1) {
            root.left = new TreeNode(left);
        } else {
            root.left = null;
        }

        if (right != -1) {
            root.right = new TreeNode(right);
        } else {
            root.right = null;
        }
    }
}