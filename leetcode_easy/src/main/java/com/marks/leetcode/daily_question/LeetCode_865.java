package com.marks.leetcode.daily_question;

import com.marks.utils.TreeNode;

import java.util.*;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_865 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/9 14:20
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_865 {

    /**
     * @Description:
     * 给定一个根为 root 的二叉树，每个节点的深度是 该节点到根的最短距离 。
     * 返回包含原始树中所有 最深节点 的 最小子树 。
     * 如果一个节点在 整个树 的任意节点之间具有最大的深度，则该节点是 最深的 。
     * 一个节点的 子树 是该节点加上它的所有后代的集合。
     * @param: root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/09 14:21
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        TreeNode result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * AC: 2ms/42.88MB
     * @param: root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2026/01/09 14:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root) {
        if (root.left == null && root.right == null) {
            return root;
        }
        // 队列对二叉树进行 广度优先搜索
        Queue<TreeNode> queue = new LinkedList<>();
        // map 记录子节点的父节点
        Map<TreeNode, TreeNode> map = new HashMap<>();
        queue.offer(root);
        // preQueue 记录最后一层的节点
        Queue<TreeNode> preQueue = new LinkedList<>();
        while (!queue.isEmpty()) {
            int size = queue.size(); // 遍历整个一层
            preQueue = new LinkedList<>(queue);
            for (int i = 0; i < size; i++) {
                TreeNode parent = queue.poll();
                if (parent.left != null) {
                    map.put(parent.left, parent);
                    queue.offer(parent.left);
                }
                if (parent.right != null) {
                    map.put(parent.right, parent);
                    queue.offer(parent.right);
                }
            }
        }

        Set<TreeNode> set = new HashSet<>(); // 记录是否重复
        while (preQueue.size() > 1) {
            int size = preQueue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = preQueue.poll();
                TreeNode parent = map.get(node);
                if (parent != null && !set.contains(parent)) {
                    preQueue.offer(parent);
                    set.add(parent);
                }
            }
        }

        return preQueue.peek();
    }

}
