package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 15:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_700 {
    
    /**
     * @Description:
     * 给定二叉搜索树（BST）的根节点 root 和一个整数值 val。
     *
     * 你需要在 BST 中找到节点值等于 val 的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 null 。
     *
     * 二叉搜索树(Binary Search Tree, BST) 是一种基于节点的数据结构，其中每个节点最多有两个子节点（左子节点和右子节点），并且满足以下性质：
     *
     * 1. 二叉搜索树的定义‌
     * ‌左子树‌ 的所有节点的值都 ‌小于‌ 当前节点的值。
     * ‌右子树‌ 的所有节点的值都 ‌大于‌ 当前节点的值。
     * ‌左右子树‌ 也必须是二叉搜索树(递归定义)。
     * ‌没有重复节点‌（通常，但可以扩展支持重复值）。
     * @param root 
     * @param val
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/5/6 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode searchBST(TreeNode root, int val) {
        TreeNode result;
        result = method_01(root, val);
        return result;
    }

    /**
     * @Description:
     * 只需要进行判断就行
     * 1. root.val > val, 找 root.left
     * 2. root.val < val, 找 root.right
     * AC: 0ms/44.32MB
     * @param root 
     * @param val 
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/5/6 15:53
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root, int val) {
        TreeNode prev = root;
        while (prev != null) {
            if (prev.val == val) {
                return prev;
            }
            if (prev.val > val) {
                // 左子树值小于当前节点值
                prev = prev.left;
            } else {
                // 右子树的值大于当前节点值
                prev = prev.right;
            }
        }
        return null;
    }
}
