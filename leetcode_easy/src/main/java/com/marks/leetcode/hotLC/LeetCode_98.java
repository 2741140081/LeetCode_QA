package com.marks.leetcode.hotLC;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称: LeetCode_QA </p>
 * <p>文件名称: LeetCode_98 </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/12/5 14:25
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_98 {

    /**
     * @Description:
     * 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。
     *
     * 有效 二叉搜索树定义如下：
     *
     * 节点的左子树只包含 严格小于 当前节点的数。
     * 节点的右子树只包含 严格大于 当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * @param: root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/05 15:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public boolean isValidBST(TreeNode root) {
        boolean result;
        result = method_01(root);
        return result;
    }

    /**
     * @Description:
     * 递归来解决, 判断左右子树是否是二叉搜索树
     * 1. 如果为null, return true;
     * AC: 0ms/44.31MB
     * @param: root
     * @return boolean
     * @author marks
     * @CreateDate: 2025/12/05 15:05
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private boolean method_01(TreeNode root) {
        return validBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validBST(TreeNode root, long minValue, long maxValue) {
        if (root == null) {
            return true;
        }
        if (root.val <= minValue || root.val >= maxValue) {
            return false;
        }
        return validBST(root.left, minValue, root.val) && validBST(root.right, root.val, maxValue);
    }

}
