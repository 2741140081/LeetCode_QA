package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_124 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 17:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_124 {

    /**
     * @Description:
     * 二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。
     * 同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
     * 路径和 是路径中各节点值的总和。
     * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
     *
     * @param: root
     * @return int
     * @author marks
     * @CreateDate: 2025/12/08 17:51
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxPathSum(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    private int ans;

    /**
     * @Description:
     * 递归求解
     * AC: 1ms/45.69MB
     * @param: root
     * @return int
     * @author marks
     * @CreateDate: 2025/12/08 17:52
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        ans = Integer.MIN_VALUE;
        dfsTree(root);
        return ans;
    }

    private int dfsTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = Math.max(dfsTree(root.left), 0);
        int right = Math.max(dfsTree(root.right), 0);
        ans = Math.max(ans, left + right + root.val);
        return Math.max(left, right) + root.val;
    }

}
