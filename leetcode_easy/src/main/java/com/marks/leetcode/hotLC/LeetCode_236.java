package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_236 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/8 17:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_236 {

    /**
     * @Description:
     * 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
     * 最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，
     * 最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * @param: root
     * @param: p
     * @param: q
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/08 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode result;
        result = method_01(root, p, q);
        return result;
    }

    private TreeNode ans;
    /**
     * @Description:
     * AC: 7ms/45.98MB
     * @param: root
     * @param: p
     * @param: q
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/12/08 17:29
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root, TreeNode p, TreeNode q) {
        ans = null;
        dfs(root, p, q);
        return ans;
    }

    private int dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || ans != null) {
            return 0;
        }
        int curr = (root == p || root == q) ? 1 : 0;
        int left = dfs(root.left, p, q);
        int right = dfs(root.right, p, q);

        // 满足条件 curr left right 中存在两个true
        if (curr + left + right >= 2 && ans == null) {
            ans = root;
        }
        return curr + left + right;
    }

}
