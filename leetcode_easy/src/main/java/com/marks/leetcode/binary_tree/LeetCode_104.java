package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 16:26
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_104 {
    private int ans = 0;

    /**
     * @Description:
     * 给定一个二叉树 root ，返回其最大深度。
     *
     * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/14 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int maxDepth(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * AC: 0ms/41.81MB
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/14 16:26
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        dfs(root, 0);
        return ans;
    }

    private void dfs(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        ans = Math.max(ans, depth);
        dfs(root.left, depth + 1);
        dfs(root.right, depth + 1);
    }
}
