package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/21 14:04
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_101 {
    private boolean ans;

    /**
     * @Description:
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     *
     * tips:
     * 树中节点数目在范围 [1, 1000] 内
     * -100 <= Node.val <= 100
     * @param root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/21 14:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isSymmetric(TreeNode root) {
        boolean result;
        result = method_01(root);
        result = method_02(root);
        return result;
    }

    /**
     * @Description:
     * 尝试使用迭代(循环遍历), 在二叉树中我认为相当于是BFS, 即使用优先队列, 给相对应的节点赋予一个相同的优先值,
     * 通过优先队列排序, 取出相同优先值的节点, 比较节点值是否相同
     *
     * AC: 3ms/41.31MB
     * @param root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/21 14:12
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_02(TreeNode root) {
        ans = true;
        PriorityQueue<queueNode> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.pValue));
        int index = 0;

        queue.add(new queueNode(index, root.left));
        queue.add(new queueNode(index, root.right));
        index++;

        while (!queue.isEmpty()) {
            // 取出优先值相同的节点, 不需要考虑到底是左子树还是右子树, 因为比较的是其子树的对称
            queueNode left = queue.poll();
            queueNode right = queue.poll();

            // 可以执行pValue的值的比较, 但是没有意义, 所以我直接省略

            TreeNode leftNode = left.curr;
            TreeNode rightNode = right.curr;

            if (leftNode == null && rightNode == null) {
                continue;
            }

            if ((leftNode != null && rightNode == null) || (leftNode == null && rightNode != null)) {
                ans = false;
                break;
            }

            if (leftNode.val != rightNode.val) {
                ans = false;
                break;
            }
            // 将其子树对称位置添加到优先队列
            queue.add(new queueNode(index, leftNode.left));
            queue.add(new queueNode(index, rightNode.right));
            index++;

            queue.add(new queueNode(index, leftNode.right));
            queue.add(new queueNode(index, rightNode.left));
            index++;

        }

        return ans;
    }

    class queueNode {
        private int pValue; // priority value
        private TreeNode curr;

        public queueNode(int pValue, TreeNode curr) {
            this.pValue = pValue;
            this.curr = curr;
        }
    }

    /**
     * @Description:
     * 使用递归解决, 即dfs
     * AC: 0ms/41.13MB
     * @param root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/21 14:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root) {
        dfsIsSymm(root.left, root.right);
        return ans;
    }

    private void dfsIsSymm(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return;
        }

        if ((left != null && right == null) || (left == null && right != null)) {
            ans = false;
            return;
        }

        if (left.val != right.val) {
            ans = false;
            return;
        }

        dfsIsSymm(left.left, right.right);
        dfsIsSymm(left.right, right.left);
    }
}
