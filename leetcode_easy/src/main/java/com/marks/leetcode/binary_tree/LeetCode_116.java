package com.marks.leetcode.binary_tree;

import com.marks.utils.Node;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/6/4 14:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_116 {

    
    /**
     * @Description:
     * 给定一个 完美二叉树 ，其所有叶子节点都在同一层，每个父节点都有两个子节点。二叉树定义如下：
     *
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     *
     * 初始状态下，所有 next 指针都被设置为 NULL。
     *
     * tips:
     * 树中节点的数量在 [0, 2^12 - 1] 范围内
     * -1000 <= node.val <= 1000
     * @param root
     * @return com.marks.utils.Node
     * @author marks
     * @CreateDate: 2025/6/4 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public Node connect(Node root) {
        Node result;
        result = method_01(root);
        result = method_02(root);
        return result;
    }

    /**
     * @Description:
     * 进阶:
     * 你只能使用常量级额外空间。
     * 使用递归解题也符合要求，本题中递归程序占用的栈空间不算做额外的空间复杂度。
     * @param root
     * @return com.marks.utils.Node
     * @author marks
     * @CreateDate: 2025/6/4 14:54
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Node method_02(Node root) {
        if (root == null) {
            return null;
        }
        // 从根节点开始
        Node leftmost = root;

        while (leftmost.left != null) {

            // 遍历这一层节点组织成的链表，为下一层的节点更新 next 指针
            Node head = leftmost;

            while (head != null) {

                // CONNECTION 1
                head.left.next = head.right;

                // CONNECTION 2
                if (head.next != null) {
                    head.right.next = head.next.left;
                }

                // 指针向后移动
                head = head.next;
            }

            // 去下一层的最左的节点
            leftmost = leftmost.left;
        }

        return root;
    }

    /**
     * @Description:
     * 简单BFS
     * AC: 2ms/43.38MB
     * @param root 
     * @return com.marks.utils.Node
     * @author marks
     * @CreateDate: 2025/6/4 14:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Node method_01(Node root) {
        if (root == null) {
            return null;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node pre = new Node(0);
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node currNode = queue.poll();
                pre.next = currNode;
                if (currNode.left != null) {
                    queue.add(currNode.left);
                }

                if (currNode.right != null) {
                    queue.add(currNode.right);
                }

                pre = pre.next;
            }
        }

        return root;
    }
}
