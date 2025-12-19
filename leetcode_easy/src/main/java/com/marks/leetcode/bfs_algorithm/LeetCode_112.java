package com.marks.leetcode.bfs_algorithm;

import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_112 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/19 10:15
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_112 {

    /**
     * @Description:
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。
     * 判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 。
     * 如果存在，返回 true ；否则，返回 false 。
     *
     * 叶子节点 是指没有子节点的节点。
     * tips:
     * 树中节点的数目在范围 [0, 5000] 内
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     * @param: root
     * @param: targetSum
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/19 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        boolean result;
        result = method_01(root, targetSum);
        return result;
    }

    /**
     * @Description:
     * 1. 深度优先搜索, 需要一个内部类存储节点和路径和
     * 2. AC: 2ms/44.53MB
     * @param: root
     * @param: targetSum
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/19 10:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root, int targetSum) {
        if (root == null) {
            // 如果节点为空, 返回false
            return false;
        }
        // 创建一个队列
        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(root, root.val));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            TreeNode treeNode = node.node;
            int sum = node.sum;
            if (sum == targetSum && treeNode.left == null && treeNode.right == null) {
                // 路径和等于目标值, 并且节点是叶子节点
                return true;
            }
            if (treeNode.left != null) {
                queue.offer(new Node(treeNode.left, sum + treeNode.left.val));
            }
            if (treeNode.right != null) {
                queue.offer(new Node(treeNode.right, sum + treeNode.right.val));
            }
        }
        return false;
    }

    class Node {
        TreeNode node;
        int sum;
        public Node(TreeNode node, int sum) {
            this.node = node;
            this.sum = sum;
        }
    }

}
