package com.marks.leetcode.binary_tree;

import com.marks.utils.TreeNode;

/**
 * <p>项目名称:  </p>
 * <p>文件名称:  </p>
 * <p>描述: [类型描述] </p>
 *
 * @author marks
 * @version v1.0
 * @date 2025/4/21 15:36
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class LeetCode_1080 {

    /**
     * @Description:
     * 给你二叉树的根节点 root 和一个整数 limit ，请你同时删除树中所有 不足节点 ，并返回最终二叉树的根节点。
     *
     * 假如通过节点 node 的每种可能的 “根-叶” 路径上值的总和全都小于给定的 limit，则该节点被称之为 不足节点 ，需要被删除。
     *
     * 叶子节点，就是没有子节点的节点。
     * @param root
     * @param limit
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/4/21 15:39
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    public TreeNode sufficientSubset(TreeNode root, int limit) {
        TreeNode result;
        result = method_01(root, limit);
        return result;
    }

    /**
     * @Description:
     * 1. 我们需要删除的是一种节点,
     *
     * 根据题意可知「不足节点」的定义为：通过节点 node 的每种可能的「根-叶」路径上值的总和全都小于给定的 limit，则该节点被称之为「不足节点」。
     * 按照上述定义可知：
     *
     * 2. 假设节点 node 为根的子树中所有的叶子节点均为「不足节点」，则可以推断出 node 一定也为「不足节点」，
     * 即经过该节点所有“根-叶” 路径的总和都小于 limit，此时该节点需要删除；
     *
     * 3. 假设节点 node 为根的子树中存在叶子节点不是「不足节点」，则可以推断出 node 一定也不是「不足节点」，
     * 因为此时一定存一条从根节点到叶子节点的路径和大于等于 limit，此时该节点需要保留。
     *
     * 查看官解后得出
     *
     * AC: 1ms/43.94MB
     * @param root
     * @param limit
     * @return com.marks.utils.TreeNode
     * @author marks
     * @CreateDate: 2025/4/21 15:36
     * @update: [序号][YYYY-MM-DD] [更改人姓名][变更描述]
     */
    private TreeNode method_01(TreeNode root, int limit) {
        boolean flag = dfsDeletedNode(root, 0, limit);
        // flag 为 true, 证明root 节点不是[不足节点], 否则就要删除 root 这个根节点, 返回null
        return flag ? root : null;
    }

    private boolean dfsDeletedNode(TreeNode root, int sum, int limit) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            // 叶子节点, 判断是否是[不足节点]
            return root.val + sum >= limit;
        }

        // 父节点
        boolean leftFlag = dfsDeletedNode(root.left, sum + root.val, limit);
        boolean rightFlag = dfsDeletedNode(root.right, sum + root.val, limit);

        if (!leftFlag) {
            // 左子树返回false, 说明左子树是[不足节点], 删除左子树
            root.left = null;
        }

        if (!rightFlag) {
            root.right = null;
        }

        // 返回当前节点是否是[不足节点], 左右子树全部为[不足节点]时, 当前节点才是[不足节点]
        return leftFlag || rightFlag;
    }
}
