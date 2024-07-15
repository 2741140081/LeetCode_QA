package com.marks.leetcode.tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class LeetCode_97 {
    public static void main(String[] args) {
//        root = [1, null, 2, 3];
        TreeNode root = new TreeNode(1);
        TreeNode root1 = new TreeNode(2);
        root.setLeft(null);
        root.setRight(root1);

        TreeNode root2 = new TreeNode(3);
        root1.setLeft(root2);
        root1.setRight(null);

        root2.setRight(null);
        root2.setLeft(null);

        inorderTraversal(root);
    }

    private static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    private static void inorder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inorder(root.getLeft(), res);
        res.add(root.getVal());
        inorder(root.getRight(), res);
    }
}
