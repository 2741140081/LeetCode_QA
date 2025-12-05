package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_226 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 10:31
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_226 {

    /**
     * @Description:
     * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
     * @param: root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/05 10:32
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode invertTree(TreeNode root) {
        TreeNode result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 广度优先搜索, 添加一个优先队列
     * AC: 0ms/42.14MB
     * @param: root
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/05 10:33
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root) {
        if (root == null) {
            return root;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode currNode = queue.poll();
                if (currNode == null) {
                    continue;
                }
                // 不需要判断currNode.left == null, 因为queue 中的节点都是非空的
                TreeNode temp = currNode.left;
                currNode.left = currNode.right;
                currNode.right = temp;
                if (currNode.left != null) {
                    queue.add(currNode.left);
                }
                if (currNode.right != null) {
                    queue.add(currNode.right);
                }
            }
        }
        return root;
    }

}
