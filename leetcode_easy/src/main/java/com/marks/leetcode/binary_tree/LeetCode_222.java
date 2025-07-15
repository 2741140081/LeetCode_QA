package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/7/8 16:46
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_222 {
    
    /**
     * @Description:
     * 给你一棵 完全二叉树 的根节点 root ，求出该树的节点个数。
     *
     * 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
     * 若最底层为第 h 层（从第 0 层开始），则该层包含 1~ 2^h 个节点。
     *
     * tips:
     * 树中节点的数目范围是[0, 5 * 10^4]
     * 0 <= Node.val <= 5 * 10^4
     * 题目数据保证输入的树是 完全二叉树
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/7/8 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int countNodes(TreeNode root) {
        int result;
        result = method_01(root);
        result = method_02(root);
        result = method_03(root);
        return result;
    }

    private int method_03(TreeNode root) {
        int count = dfsCount(root);
        return count;
    }

    private int dfsCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftLevel = getTreeLevel(root.left);
        int rightLevel = getTreeLevel(root.right);

        if (leftLevel == rightLevel) {
            // 左子树必定为满二叉树
            return (1 << leftLevel) + dfsCount(root.right);
        } else {
            // leftLevel > rightLevel ==> 右子树必定为满二叉树
            return dfsCount(root.left) + (1 << rightLevel);
        }
    }

    private int getTreeLevel(TreeNode node) {
        int height = 0;
        while (node != null) {
            height++;
            node = node.left;
        }
        return height;
    }

    /**
     * @Description:
     * 基于完全二叉树, 计算节点数
     *
     * 1. 基于完全二叉树的性质
     * 2. 对于一颗满二叉树, 其节点数是确定的, 假设高度是 h, 那么节点数: 2 ^ h - 1
     * 3. 对于上面这个性质, 要求父节点的左右子树的深度相同, 那么这颗子树就是一颗满二叉树, 只需要计算此时的高度 h1, (2 ^ h1) - 1
     * 4. 对不满足条件的二叉树根节点进行递归遍历, 其节点数: 1 + dfs(left) + dfs(right)
     * 5. 时间复杂度为 log(n)^2
     *
     * AC: 0ms/46.67MB
     *
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/7/8 17:43
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_02(TreeNode root) {
        int count = dfs(root);
        return count;
    }

    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = getTreeLeftDepth(root);
        int rightDepth = getTreeRightDepth(root);

        if (leftDepth == rightDepth) {
            return (1 << leftDepth) - 1; // 2^n - 1 的节点数量
        } else {
            return 1 + dfs(root.left) + dfs(root.right);
        }
    }

    private int getTreeRightDepth(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.right;
        }
        return height;
    }

    private int getTreeLeftDepth(TreeNode root) {
        int height = 0;
        while (root != null) {
            height++;
            root = root.left;
        }
        return height;
    }


    /**
     * @Description:
     * 先不去管他什么低于O(n) 的方法, 直接暴力求解
     * 1. 既然是完全二叉树, 那么直接使用广度优先遍历方式来遍历二叉树.
     *
     * AC: 4ms/45.88MB
     *
     * todo 优化时间复杂度
     * 需要完全理解完全二叉树的性质, 修改代码
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/7/8 16:47
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            count += size;
            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                if (currNode.left != null) {
                    queue.add(currNode.left);
                }

                if (currNode.right != null) {
                    queue.add(currNode.right);
                }
            }
        }

        return count;
    }


}
