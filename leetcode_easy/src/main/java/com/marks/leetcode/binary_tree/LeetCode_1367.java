package com.marks.leetcode.binary_tree;

import com.marks.utils.ListNode;
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
 * @date 2025/6/3 17:10
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1367 {
    /**
     * @Description:
     * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
     *
     * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
     *
     * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
     * @param head 
     * @param root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/6/3 17:11
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isSubPath(ListNode head, TreeNode root) {
        boolean result;
        result = method_01(head, root);
        return result;
    }

    /**
     * @Description:
     * 1. Queue<TreeNode> queue; 使用queue存储所有值为head.val
     *
     * AC: 10ms/44.11MB
     * @param head
     * @param root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/6/3 17:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(ListNode head, TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        dfs(root, queue, head.val);
        ListNode pre = head.next;
        while (!queue.isEmpty() && pre != null) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                if (currNode.left != null && currNode.left.val == pre.val) {
                    queue.add(currNode.left);
                }
                if (currNode.right != null && currNode.right.val == pre.val) {
                    queue.add(currNode.right);
                }
            }
            if (pre.next == null) {
                break;
            }
            pre = pre.next;
        }
        return !queue.isEmpty();
    }

    private void dfs(TreeNode root, Queue<TreeNode> queue, int target) {
        if (root == null) {
            return;
        }
        if (root.val == target) {
            queue.add(root);
        }
        dfs(root.left, queue, target);
        dfs(root.right, queue, target);
    }
}
