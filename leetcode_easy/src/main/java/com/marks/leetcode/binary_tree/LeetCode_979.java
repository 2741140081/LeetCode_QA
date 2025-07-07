package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/7 16:28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_979 {

    private int move = 0;
    
    /**
     * @Description:
     * 给你一个有 n 个结点的二叉树的根结点 root ，其中树中每个结点 node 都对应有 node.val 枚硬币。整棵树上一共有 n 枚硬币。
     *
     * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。移动可以是从父结点到子结点，或者从子结点移动到父结点。
     *
     * 返回使每个结点上 只有 一枚硬币所需的 最少 移动次数。
     *
     * tips:
     * 树中节点的数目为 n
     * 1 <= n <= 100
     * 0 <= Node.val <= n
     * 所有 Node.val 的值之和是 n
     *
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/7/7 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int distributeCoins(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 节点数 n = sum(node.val)
     * 2. 如果某个节点值为0, 则该节点的硬币可能来自于左子树, 右子树, 以及父节点
     * 2.1 判断左右子树情况,
     *
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/7 16:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        dfs(root);
        return move;
    }

    private int dfs(TreeNode root) {
        int moveLeft = 0;
        int moveRight = 0;

        if (root == null) {
            return 0;
        }
        if (root.left != null) {
            moveLeft = dfs(root.left);
        }
        if (root.right != null) {
            moveRight = dfs(root.right);
        }

        move += Math.abs(moveLeft) + Math.abs(moveRight);
        return moveLeft + moveRight + root.val - 1;
    }
}
