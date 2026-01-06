package com.marks.leetcode.daily_question;

import com.marks.utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_1161 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2026/1/6 10:23
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1161 {

    /**
     * @Description:
     * 给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
     * 返回总和 最大 的那一层的层号 x。如果有多层的总和一样大，返回其中 最小 的层号 x。
     * @param: root
     * @return int
     * @author marks
     * @CreateDate: 2026/01/06 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxLevelSum(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 1. 广度优先搜索, sum 记录当前层的总和, maxSum 记录最大值, ans 记录最大值所在的层号
     * AC: 8ms/48.21MB
     * @param: root
     * @return int
     * @author marks
     * @CreateDate: 2026/01/06 10:23
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        int ans = 1; // 默认值顶层的root
        int maxSum = root.val;
        int level = 1;
        // 队列存储节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int sum = 0; // 当前层的和
            level++;
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                    sum += node.left.val;
                }
                if (node.right != null) {
                    queue.offer(node.right);
                    sum += node.right.val;
                }
            }
            if (!queue.isEmpty() && sum > maxSum) {
                maxSum = sum;
                ans = level;
            }
        }
        return ans;
    }

}
