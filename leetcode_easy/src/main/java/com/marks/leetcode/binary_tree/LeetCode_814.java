package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/5/6 14:42
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_814 {
    
    /**
     * @Description:
     * 给你二叉树的根结点 root ，此外树的每个结点的值要么是 0 ，要么是 1 。
     *
     * 返回移除了所有不包含 1 的子树的原二叉树。
     *
     * 节点 node 的子树为 node 本身加上所有 node 的后代。
     * @param root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/5/6 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode pruneTree(TreeNode root) {
        TreeNode result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     *
     * dfs 判断该节点是否需要删除
     * 1. 如果该节点是叶子节点, 并且 node.val = 0, 则删除
     * 2. 如果该节点是父节点, 那么判断当前节点的左右子树是否全部为0， 并且node.val 也是0
     *
     * 以上会存在问题, 即左右子树的后代节点可能存在为1的节点, 所以我们可以返回当前左右子树的和, 如果为0, 并且该节点值也是0，则删除该节点
     *
     * AC: 0ms/40.53MB
     * @param root 
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/5/6 14:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root) {
        int ans = dfsDelNode(root);
        return ans == 0 ? null : root;
    }

    private int dfsDelNode(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int sumLeft = dfsDelNode(root.left);
        int sumRight = dfsDelNode(root.right);
        if (sumLeft == 0) {
            root.left = null;
        }
        if (sumRight == 0) {
            root.right = null;
        }

        return sumLeft + sumRight + root.val;
    }
}
