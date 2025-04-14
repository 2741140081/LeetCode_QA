package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>项目名称: 二叉树的前序遍历 </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/14 15:14
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_144 {
    private List<Integer> ans = new ArrayList<>();
    /**
     * @Description: [功能描述]
     * @param root
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/4/14 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 前序遍历方法: 先遍历根节点, 然后是左子树, 最后是右子树
     *
     * 深度优先遍历二叉树
     * AC: 0ms/40.83MB
     * @param root
     * @return java.util.List<java.lang.Integer>
     * @author marks
     * @CreateDate: 2025/4/14 15:15
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private List<Integer> method_01(TreeNode root) {
        dfs(root);
        return ans;
    }

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        ans.add(root.val);
        dfs(root.left); // 左子树
        dfs(root.right); // 右子树
    }
}
