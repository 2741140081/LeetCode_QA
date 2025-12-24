package com.marks.leetcode.bfs_algorithm;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_515 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/24 10:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_515 {

    /**
     * @Description:
     * 给定一棵二叉树的根节点 root ，请找出该二叉树中每一层的最大值。
     * @param: root
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/24 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * AC: 2ms/46.64MB
     * @param: root
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/12/24 10:42
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(TreeNode root) {
        // 构建队列
        Queue<TreeNode> queue = new LinkedList<>();
        // 结果集
        List<Integer> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                max = Math.max(max, node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            ans.add(max);
        }
        return ans;
    }
}
