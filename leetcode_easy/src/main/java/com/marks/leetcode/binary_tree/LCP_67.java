package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/18 14:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LCP_67 {

    /**
     * @Description:
     * 力扣嘉年华上的 DIY 手工展位准备了一棵缩小版的 二叉 装饰树 root 和灯饰，你需要将灯饰逐一插入装饰树中，要求如下：
     *
     * 完成装饰的二叉树根结点与 root 的根结点值相同
     * 若一个节点拥有父节点，则在该节点和他的父节点之间插入一个灯饰（即插入一个值为 -1 的节点）。具体地：
     * 在一个 父节点 x 与其左子节点 y 之间添加 -1 节点， 节点 -1、节点 y 为各自父节点的左子节点，
     * 在一个 父节点 x 与其右子节点 y 之间添加 -1 节点， 节点 -1、节点 y 为各自父节点的右子节点，
     * 现给定二叉树的根节点 root ，请返回完成装饰后的树的根节点。 示例 1：
     *
     * tips:
     * 0 <= root.Val <= 1000 root 节点数量范围为 [1, 10^5]
     * @param root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/4/18 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode expandBinaryTree(TreeNode root) {
        TreeNode result;
        result = method_01(root);
        return result;
    }


    /**
     * @Description:
     *
     * AC: 1ms/55.39MB
     *
     * @param root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/4/18 14:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root) {
        dfsAddDummyNode(root);
        return root;
    }

    private void dfsAddDummyNode(TreeNode root) {
        if (root == null || (root.left == null && root.right == null )) {
            // 节点为空, 或者为叶子节点
            return;
        }

        // 当前节点为父节点, 添加 dummy(-1)的节点
        TreeNode left = root.left, right = root.right;

        // 添加装饰节点, or 虚拟节点
        if (left != null) {
            root.left = new TreeNode(-1);
            root.left.left = left;
        }

        if (right != null) {
            root.right = new TreeNode(-1);
            root.right.right = right;
        }

        // 递归执行
        dfsAddDummyNode(left);
        dfsAddDummyNode(right);
    }
}
