package com.marks.leetcode.tree;


import com.marks.utils.TreeNode;

public class LeetCode_100 {
    public static void main(String[] args) {
        TreeNode p = new TreeNode(1);
        TreeNode q = new TreeNode(1);
        isSameTree(p, q);
    }

    private static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else if (p.getVal() != q.getVal()) {
            return false;
        } else {
            return isSameTree(p.getLeft(), q.getLeft()) && isSameTree(p.getRight(), q.getRight());
        }

    }

}
