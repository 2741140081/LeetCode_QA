package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 15:50
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_872Test {

    @Test
    void leafSimilar() {

        TreeNode root1 = new TreeNode(1);
        root1.left = new TreeNode(2);
        root1.left.left = null;
        root1.left.right = null;
        root1.right = new TreeNode(200);
        root1.right.left = null;
        root1.right.right = null;
        TreeNode root2 = new TreeNode(1);
        root2.left = new TreeNode(2);
        root2.left.left = null;
        root2.left.right = null;
        root2.right = new TreeNode(200);
        root2.right.left = null;
        root2.right.right = null;
        boolean result = new LeetCode_872().leafSimilar(root1, root2);
        System.out.println(result);

    }
}