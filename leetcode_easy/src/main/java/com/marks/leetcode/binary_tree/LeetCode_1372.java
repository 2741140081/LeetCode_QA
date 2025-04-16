package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/15 10:44
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1372 {
    private int ans = 0;
    
    /**
     * @Description:
     * 给你一棵以 root 为根的二叉树，二叉树中的交错路径定义如下：
     *
     * 选择二叉树中 任意 节点和一个方向（左或者右）。
     * 如果前进方向为右，那么移动到当前节点的的右子节点，否则移动到它的左子节点。
     * 改变前进方向：左变右或者右变左。
     * 重复第二步和第三步，直到你在树中无法继续移动。
     * 交错路径的长度定义为：访问过的节点数目 - 1（单个节点的路径长度为 0 ）。
     *
     * 请你返回给定树中最长 交错路径 的长度。
     * @param root
     * @return int
     * @author marks
     * @CreateDate: 2025/4/15 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public int longestZigZag(TreeNode root) {
        int result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * true is mean left, false is mean right
     * 0 is mean depth
     *
     * and another condition is you can start any node.
     *
     * AC: 6ms/53.50MB
     * @param root 
     * @return int
     * @author marks
     * @CreateDate: 2025/4/15 10:45
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private int method_01(TreeNode root) {
        dfs(root.left, 1, true);
        dfs(root.right, 1, false);
        return ans;
    }

    private void dfs(TreeNode root, int depth, boolean directory) {
        if (root == null) {
            return;
        }
        ans = Math.max(ans, depth);
        if (directory) {
            dfs(root.right, depth + 1, false);
            dfs(root.left, 1, true);
        } else {
            dfs(root.left, depth + 1, true);
            dfs(root.right, 1, false);
        }

        // start the curr node


    }
}
