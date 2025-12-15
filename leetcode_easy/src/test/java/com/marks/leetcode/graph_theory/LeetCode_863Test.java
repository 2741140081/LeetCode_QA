package com.marks.leetcode.graph_theory;

import com.marks.utils.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_863Test </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/15 14:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
class LeetCode_863Test {

    @Test
    void distanceK() {
        // 输入：root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
        TreeNode root = new TreeNode(3);
        TreeNode root1 = new TreeNode(5);
        TreeNode root2 = new TreeNode(1);
        TreeNode root3 = new TreeNode(6);
        TreeNode root4 = new TreeNode(2);
        TreeNode root5 = new TreeNode(0);
        TreeNode root6 = new TreeNode(8);
        TreeNode root7 = new TreeNode(7);
        TreeNode root8 = new TreeNode(4);
        root.left = root1;
        root.right = root2;
        root1.left = root3;
        root1.right = root4;
        root2.left = root5;
        root2.right = root6;
        root4.left = root7;
        root4.right = root8;
        LeetCode_863 leetCode_863 = new LeetCode_863();
        List<Integer> ans = leetCode_863.distanceK(root, root1, 2);

    }
}