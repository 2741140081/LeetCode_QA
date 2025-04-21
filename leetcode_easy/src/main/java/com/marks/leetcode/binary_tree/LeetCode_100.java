package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/21 11:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_100 {
    private boolean ans = true;
    /**
     * @Description:
     * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     *
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     *
     * @param p
     * @param q
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/21 11:20
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        boolean result;
        result = method_01(p, q);
        return result;
    }

    /**
     * @Description:
     * 1. dfs 判断对应节点的两个二叉树的值是否相同
     *
     * AC: 0ms/40.20MB
     * @param p
     * @param q
     * @return boolean
     * @author marks
     * @CreateDate: 2025/4/21 11:14
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode p, TreeNode q) {
        dfsIsSameVal(p, q);
        return ans;
    }

    private void dfsIsSameVal(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return;
        }

        if (p != null && q == null) {
            ans = false;
            return;
        }

        if (p == null && q != null) {
            ans = false;
            return;
        }

        // p 和 q 均不为 null

        if (p.val != q.val) {
            ans = false;
            return;
        }

        dfsIsSameVal(p.left, q.left);
        dfsIsSameVal(p.right, q.right);
    }
}
