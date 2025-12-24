package com.marks.leetcode.bfs_algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_117 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/24 10:16
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_117 {

    /**
     * @Description:
     * 给定一个二叉树： Node
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL 。
     * 初始状态下，所有 next 指针都被设置为 NULL 。
     * @param: root
     * @return com.marks.leetcode.bfs_algorithm.LeetCode_117.Node
     * @author marks
     * @CreateDate: 2025/12/24 10:17
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
     * 基于空间复杂度的优化, 降低空间复杂度O(N) => O(1)
     * @param: root
     * @return com.marks.leetcode.bfs_algorithm.LeetCode_117.Node
     * @author marks
     * @CreateDate: 2025/12/24 10:27
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Node last = null;
    private Node nextStart = null;
    private Node method_02(Node root) {
        if (root == null) {
            return root;
        }
        Node curr = root;
        while (curr != null) {
            last = null;
            nextStart = null;
            while (curr != null) {
                for(Node p = curr; p != null; p = p.next) {
                    if (p.left != null) {
                        handle(p.left);
                    }
                    if (p.right != null) {
                        handle(p.right);
                    }
                }
                curr = nextStart; // 移动到下一层
            }
        }
        return root;
    }

    private void handle(Node p) {
        if (last != null) {
            last.next = p;
        }
        if (nextStart == null) {
            // 这是新的层
            nextStart = p;
        }
        last = p; // 更新 last
    }

    /**
     * @Description:
     * 广度优先搜索, 并且每一层设置一个虚拟头节点 prev , 更新 prev.next 为当前节点, prev = prev.next
     * AC: 1ms/45.46MB
     * @param: root
     * @return com.marks.leetcode.bfs_algorithm.LeetCode_117.Node
     * @author marks
     * @CreateDate: 2025/12/24 10:16
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private Node method_01(Node root) {
        if (root == null) {
            return root;
        }
        // 创建队列
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            Node prev = new Node();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                prev.next = node;
                prev = prev.next;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
        }

        return root;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}
