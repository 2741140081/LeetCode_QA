package com.marks.utils;

/**
 * <p>项目名称: 二叉搜索树BST </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 16:07
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class BinarySearchTree {
    private TreeNode root;  // 根节点

    public BinarySearchTree() {
        this.root = null;
    }

    // 插入节点
    public void insert(int val) {
        root = insertNode(root, val);
    }

    private TreeNode insertNode(TreeNode node, int val) {
        if (node == null) {
            return new TreeNode(val);  // 创建新节点
        }
        if (val < node.val) {
            node.left = insertNode(node.left, val);  // 递归插入左子树
        } else if (val > node.val) {
            node.right = insertNode(node.right, val);  // 递归插入右子树
        }
        return node;
    }

    // 查找节点
    public boolean search(int val) {
        return searchNode(root, val);
    }

    private boolean searchNode(TreeNode node, int val) {
        if (node == null) {
            return false;
        }
        if (val == node.val) {
            return true;
        } else if (val < node.val) {
            return searchNode(node.left, val);  // 递归查找左子树
        } else {
            return searchNode(node.right, val);  // 递归查找右子树
        }
    }

    // 中序遍历（按升序输出）
    public void inorderTraversal() {
        inorder(root);
    }

    private void inorder(TreeNode node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.val + " ");
            inorder(node.right);
        }
    }
}
